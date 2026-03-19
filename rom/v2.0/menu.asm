; Author Maxim Gorin (c) 2026

include "system/keyboard.hasm"

include "consts.hasm"

def START_ADDRESS	0X66

	org 0
	
	jp $START_ADDRESS

	alloc 0x66 - 3
		
	org $START_ADDRESS
	
	di
	ld sp, 0xffff
	call init
	ld hl, bk_screen_addr
	ld d, 0
	ld e, 0
	ld b, 23
	ld c, 31
	ld ix, font_addr
	ld iy, title_text		
	call _draw_main_window

	call _init_ui
	ld a, 39
	ld ( items_count ), a
	ld ix, font_addr
	ld iy, scroll_text
	call _scroll_init	

loop:
	; Scroller
	ld hl, bk_screen_addr
	ld d, 22
	ld e, 6
	add hl, de
	ld iy, scroll_text
	ld ix, font_addr
	ld b, 26
	call _scroll_all_lines
	
	; Items
	ld hl, bk_screen_addr
	ld d, $ITEM_TOP
	ld e, $ITEM_LEFT
	ld c, $ITEM_LENGTH
	ld b, $ITEM_COLUMN_SIZE
	ld ix, font_addr
	ld iy, items_addr
	call _draw_items
	ld hl, bk_screen_addr
	call screen_swap
	ld bc, $KB_PORT | ($R_67890 << 8)
	in a, ( c )
	bit 2, a ; key '8'
	jr z, pressed_8
	bit 1, a ; key '9'
	jr z, pressed_9
	bit 4, a ; key '6'
	jr z, pressed_6
	bit 3, a ; key '7'
	jr z, pressed_7
	bit 0, a ; key '0'
	jr z, pressed_fire
	ld bc, $KB_PORT | ($R_HJKLENT << 8)
	in a, ( c )
	bit 0, a ; key 'Enter'
	jr z, pressed_fire
	jr loop	
	
 pressed_8: 
 	ld b, $ITEM_COLUMN_SIZE
	call _items_navigate_down
	jr loop
	
 pressed_9: 	
 	call _items_navigate_up
	jr loop
	ret

pressed_6:
 	ld b, $ITEM_COLUMN_SIZE
 	call _items_navigate_left
	jr loop

pressed_7:	
	ld b, $ITEM_COLUMN_SIZE
 	call _items_navigate_right
	jr loop

pressed_fire:
	ld hl, bk_screen_addr
	ld a, $_BK_WHITE | $_WHITE
	call _clear_desktop	
	ld hl, bk_screen_addr
	ld iy, loading_text
	call strlen
	inc a
	inc a
	ld c, a
	ld b, 3
	call _calc_center
	ld a, $_BK_RED | $_WHITE | $_FLASHING
	ld ( alert_msg_color ), a
	ld iy, loading_text
	ld ix, font_addr	
	call _draw_alert
	ld hl, bk_screen_addr
	call screen_swap
	jp execute

init:
	xor a, a	
	ld ( item_offset_addr ), a
	ld ( item_selected_addr ), a
	ld ( prev_item_selected_addr ), a
	ld a, $_WHITE
	out ( $_BORDER_PORT ), a	
	ld hl, bk_screen_addr
	ld a, $_BK_WHITE | $_WHITE
	call _clear_desktop
	ret	

execute:
	ld hl, content_decription
	ld a, ( item_selected )
	rlca
	rlca
	ld e, a
	and a, 0b00000011
	ld d, a
	ld a, e
	and a, 0b11111100
	ld e, a
	add hl, de
	ld a, ( hl ) ; loader type
	rlca
	inc hl
	push hl
	ld hl, loader_table
	ld e, a
	ld d, 0
	add hl, de
	ld e, ( hl )
	inc hl
	ld d, ( hl )	
	_ld ix, de ;address of loader
	pop hl
	ld a, ( hl ) ; page number
	inc hl
	ld e, ( hl )
	inc hl
	ld d, ( hl )
	ex de, hl ; hl - address of content
	jp ( ix )

include "system/console.asm"

include "ui/win.asm"

include "system/screen.asm"

include "ui/scroll.asm"

scroll_text:
	include "text.asm"

title_text:
	include "title.asm"

loading_text:
	defb "Loading...\0"	

font_addr:
	resource "res/font.ch8"
	
items_addr:
	include "bc01items.asm"

loader48k1:
	resource "res/embedded/loader48k1.bin"
	
loader48k2:
	resource "res/embedded/loader48k2.bin"	

loader128k1:
	resource "res/embedded/loader48k1.bin"
	
loader128k2:
	resource "res/embedded/loader48k2.bin"		
	

loader_table:
	defw loader48k1 ;loader 48k
	
	defw	loader48k2 ;screen loader 48k
	
	defw loader48k1 ;loader 128k	
	
	defw loader48k2 ;screen loader 128k

content_decription:
; DATA HEADER:
;00 1b - loader type (48k1, 48k2, 128k1, 128k2)
;01 1b - page number
;02 2b - start address of content
