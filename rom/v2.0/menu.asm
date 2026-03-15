include "system/keyboard.hasm"

include "consts.hasm"

	
	org 49152
	di		
	ld sp, 0xffff
	call init
	ld hl, bk_screen_addr
	ld d, 0
	ld e, 0
	ld b, 23
	ld c, 31
	ld ix, font_addr
	ld iy, title_addr
	ld a, $_BK_BLUE | $_GREEN ;bk color
	;ld a, $_BK_RED | $_WHITE ;bk color
	call _draw_main_window

	call _init_items
	ld a, 36
	ld ( items_count ), a

loop:
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

 wait: 	
	ld bc, $KB_PORT | ($R_67890 << 8)
	in a, ( c )
	bit 2, a ; key '8'
	jr z, pressed_8
	bit 1, a ; key 'e9'
	jr z, pressed_9
	bit 4, a ; key '6'
	jr z, pressed_6
	bit 3, a ; key '7'
	jr z, pressed_7
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
	ret	

init:	
	xor a, a	
	ld ( item_offset_addr ), a
	ld ( item_selected_addr ), a
	ld ( prev_item_selected_addr ), a
	ld a, $_WHITE
	out ( 0xfe ), a
	ld hl, bk_attr_addr
	_ld de, hl
	inc de
	ld bc, 768
	ld a, $_BK_WHITE | $_BLACK
	ld ( hl ), a
	ldir
	ret

include "system/console.asm"

include "ui/win.asm"

include "system/screen.asm"

title_addr:
	defb "Best of CRackOWN", 0

font_addr:
	resource "res/font.ch8"

loader_addr:
	resource "res/embedded/loader48k1.bin"
	
items_addr:
	include "bc01items.asm"
