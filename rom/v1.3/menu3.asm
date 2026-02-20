; v1.1

def PROG_ADDR	0x0000

def LOADER_START_ADDR	0x5b00

org $PROG_ADDR
	
	di	
	ld sp, 0xff56
	jp main
	

include "menu3.hasm"

main:
	xor a, a
	out ( $_BORDER_PORT), a
	ld a, 0x30
	ld b, 0x7f
	ld c, 0xfd
	out ( c ), a	
	ld a,0x3f
	ld i, a
	; clear memory
	ld bc, 0xc000
	ld hl, 0xffff	
	ld d,h
	ld e,l
	dec de
	xor a, a
	ld ( hl ), a	
	;lddr	
	ld hl,bk_screen_addr
	push hl
	call start_menu
	pop hl		
	jp loading_menu	

loading_menu:	
	ld b, $_BLACK	
	push hl
	call graph_cls
	pop hl
	ld d, 11
	ld e, 11
	ld iy, loading_addr
	ld ix, font_raw_addr
	ld b, $_YELLOW | $FLASHING
	ld c, $NORMAL
	push hl
	call graph_print
	pop hl
	call graph_swap_screen	
	ld hl, bk_attr_addr
	call graph_swap_screen_attr
	; ----------------------------------------
	ld hl, loader_exec_begin
	ld de, $LOADER_START_ADDR
	ld bc, loader_exec_end - loader_exec_begin
	ldir
	ld hl, data_header_table
	ld a, ( menu_selected_addr )
	ld c, a	
	rlca
	add a, c ; mul a, 3
	ld b, 0
	ld c, a
	add hl, bc
	ld a, ( hl ) ; page number
	inc hl
	ld e, ( hl ) 
	inc hl
	ld d, ( hl ) ; address of data
	ex de, hl
	jp $LOADER_START_ADDR


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
	ld d, 2
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
	inc d
	inc a	
	djnz draw_options_l1 
	ret
	
title_addr:
	defb "BEST COLLECTION 02", 0
	
options_addr:
	defb "1.Venom SB   ", 0
	
	defb "2.Dark fusion", 0

	defb "3.Bruce Lee  ", 0
	
	
author_addr:
	defb "Created by Maxx in 2026",0

loading_addr:
	defb "Loading...", 0

font_raw_addr:
	resource "res/godofwar.fnt"

loader_exec_begin:
	resource "res/embedded/loader2.bin"
	
data_header_table:
; format
; page0
; address0
; ...
; pageN
; addressN

loader_exec_end:
