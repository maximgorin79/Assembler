; v1.0

def ORG_ADDR	40000

org $ORG_ADDR

jp main

include "main.hasm"

set_48k_mode:
	ld a, 0x30
	ld b, 0x7f
	ld c, 0xfd
	out ( c ), a
	ret
	
main:	
	di
	ld sp, 65000
	call set_48k_mode ; set 48k mode
	ld a, $_BLACK
	ld hl,bk_screen_addr	
	push hl
	call graph_set_border	
	pop hl	
	push hl
	call start_menu
	pop hl		
	call loading_menu
	ei
	ret

loading_menu:	
	ld b, $_BLACK	
	push hl
	call graph_cls
	pop hl
	ld d, 11
	ld e, 11
	ld iy, loading_addr
	ld ix, font_raw_addr
	ld b, $_YELLOW
	ld c, $NORMAL
	push hl
	call graph_print
	pop hl
	call graph_swap_screen	
	ld hl, bk_attr_addr
	call graph_swap_screen_attr
	call prepare_booter	
	call $_SCREEN_ADDR
	ret
	
prepare_booter:	
; copy unpack proc
	ld de, $_SCREEN_ADDR	
	; copy booter proc
	ld hl, booter		
	ld bc, booter_end - booter
	ldir	
	push de ;table address	
	ld a, (menu_selected_addr)
	ld c, data_info_body_end - data_info_body_start
	ld hl, data_info
	or a, a
	
prepare_booter_l1:	
	jr z, prepare_booter_l2
	add hl, bc
	dec a
	jr prepare_booter_l1
	
prepare_booter_l2:
	ldir		
	pop hl ; unpack proc dest address	
	ret		

draw_background:
	ld b, $_BLACK	
	call graph_cls
	ret
	
start_menu:	
	xor a, a
	ld (menu_selected_addr), a	
	push hl
	call draw_background
	pop hl
	; draw title
	ld d, 3
	ld e, 7
	ld iy, title_addr
	ld ix, font_raw_addr
	push ix
	push hl
	call graph_print_title
	pop hl
	pop ix
	; Draw author	
	ld d, 22
	ld e, 5
	ld c, $NORMAL
	ld b, $_MAGENTA	
	ld iy, author_addr	
	call graph_print
	
key_listener_start:	
	ld hl,bk_screen_addr
	ld ix, font_raw_addr
	push hl
	call draw_options	
	pop hl
	call graph_swap_screen	
	ld hl, bk_attr_addr
	call graph_swap_screen_attr
	
key_listener_l1:
	ld hl, menu_selected_addr
	ld bc, $KB_PORT | ($R_12345 << 8)
	in a, ( c )
	bit 0, a ; key '1'
	jr z, key_pressed_1
	bit 1, a ; key '2'
	jr z, key_pressed_2
	bit 2, a ; key '3'
	jr z, key_pressed_3
	bit 3, a ; key '4'
	jr z, key_pressed_4	
	ld bc, $KB_PORT | ($R_67890 << 8)
	in a, ( c )
	bit 1, a ; key '9'
	jr z, key_pressed_9
	bit 2, a ; key '8'
	jr z, key_pressed_8
	bit 0, a ; key '0'
	jr z, key_pressed_OK
	ld bc, $KB_PORT | ($R_HJKLENT << 8)
	in a, ( c )
	bit 0, a ; key 'enter'
	jr z, key_pressed_OK
	
	ld bc , $KB_DELAY
	call system_delay
	jr key_listener_l1	
	
key_pressed_9:	
	ld a, ( hl )
	or a, a
	jr z, key_pressed_9_1
	dec ( hl )
	jr key_listener_start
	
key_pressed_9_1:
key_pressed_4:
	ld a, $NUM_OPTIONS - 1
	jr key_return
	
key_pressed_8:	
	ld a, ( hl )
	cp a, $NUM_OPTIONS - 1
	jr z, key_pressed_8_1
	inc ( hl )	
	jr key_listener_start
	
key_pressed_8_1:
key_pressed_1:
	xor a, a
	jr key_return
	
key_pressed_2:
	ld a, 1
	jr key_return
	
key_pressed_3:
	ld a, 2
	jr key_return	

key_pressed_OK:	
	ret

key_return:
	ld ( hl ), a
	jr key_listener_start	
	
; hl - screen address
; ix - font address
draw_options:
	;-- Menu
	ld b, $NUM_OPTIONS
	ld d, $MENU_TOP
	ld e, $MENU_LEFT
	ld iy, options_addr
	xor a, a
	
draw_options_l1:
	push bc	
	push hl
	push hl
	ld hl, menu_selected_addr
	cp a, ( hl )	
	jr nz, draw_options_l2
	ld c,$SELECTED
	ld b, $_YELLOW
	jr draw_options_l3
	
draw_options_l2:
	ld c,$NORMAL
	ld b, $_WHITE
	
draw_options_l3:
	pop hl
	push de
	push ix
	push af
	call graph_print	
	pop af
	pop ix
	pop de	
	pop hl	
	pop bc
	inc d
	inc d	
	inc a	
	djnz draw_options_l1 
	ret
	
title_addr:
	defb "BEST COLLECTION N1", 0
	
options_addr:
	defb "1.Starquake", 0
	
	defb "2.Venom SB ", 0

	defb "3.Tujad    ", 0

	defb "4.Commando ", 0
	
author_addr:
	defb "Created by Maxx in 2026",0

loading_addr:
	defb "Loading...", 0

font_raw_addr:
	resource "res/godofwar.fnt"	

; ------------- Tables ------------------

; data description	

data_info:

; data 0

data_info_body_start:

	defb 0 ;page index

	defw	$ORG_ADDR + 1779 ;src address in page
	
	defw 26000 ;dest address

	defw	1203 ; data size

	defb 1 ;compression

	defw 26000 ;call address	
	
data_info_body_end:

; data 1
;-----------------------------

	defb 0 ;page index 

	defw	$ORG_ADDR + 2982 ;src address in page
	
	defw 27000 ;dest address

	defw	1227 ; data size

	defb 0 ;compression

	defw 27000 ;call address

; data 2
;-----------------------------

	defb 0 ;page index 

	defw	$ORG_ADDR + 4209 ;src address in page
	
	defw 28000 ;dest address

	defw	1227 ; data size

	defb 0 ;compression

	defw 28000 ;call address

; data 3
;-----------------------------

	defb 0 ;page index 

	defw	$ORG_ADDR + 5436 ;src address in page
	
	defw 29000 ;dest address

	defw	1227 ; data size
	
	defb 0 ;compression

	defw 29000 ;call address

game0:
	resource "res/test0.rle"

game1:
	resource "res/test1.bin"

game2:
	resource "res/test2.bin"

game3:
	resource "res/test3.bin"
