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
	bit 1, a ; key 'e9'
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
	call draw_loading
	ret

init:	
	xor a, a	
	ld ( item_offset_addr ), a
	ld ( item_selected_addr ), a
	ld ( prev_item_selected_addr ), a
	ld a, $_WHITE
	out ( 0xfe ), a	
	ld hl, bk_screen_addr
	ld a, $_BK_WHITE | $_BLACK
	call _clear_desktop
	ret

draw_loading:
	ld hl, bk_screen_addr
	ld a, $_BK_WHITE | $_WHITE
	call _clear_desktop	
	ld hl, bk_screen_addr
	ld iy, loading_addr
	call strlen
	inc a
	inc a
	ld c, a
	ld b, 3
	call _calc_center
	ld a, $_BK_RED | $_WHITE | $_FLASHING
	ld ( alert_msg_color ), a
	ld iy, loading_addr
	ld ix, font_addr	
	call _draw_alert
	ld hl, bk_screen_addr
	call screen_swap
lll: nop
	jr lll
	ret
	
scroll_text:
	defb "Hi guys!!! Glad to present you a new ROM which"
	defb " has 1 Mb size, where you can find all your favorite"
	defb " games from childhood on the old respected zx speccy!"
	defb " Loader written by Maxx in 2026.                      ", 0

include "system/console.asm"

include "ui/win.asm"

include "system/screen.asm"

include "ui/scroll.asm"

loading_addr:
	defb "Loading...", 0

title_addr:
	defb "Best of CRackOWN", 0

font_addr:
	resource "res/font.ch8"

loader_addr:
	resource "res/embedded/loader48k1.bin"
	
items_addr:
	include "bc01items.asm"
