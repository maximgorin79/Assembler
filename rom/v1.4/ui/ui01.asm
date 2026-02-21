; Key pressing delay
def	KB_DELAY 0x2fff

; Selected flag for text
def	SELECTED 0xff

; Normal flag for text (not selected)
def	NORMAL 0x00

include "system/system.asm"

include "system/keyboard.asm"

include "draw/graph.asm"


; Address area to store loader data
menu_selected_addr:
	equ bk_attr_addr + $_ATTR_SIZE

loading_menu:
	ld hl, bk_screen_addr
	ld b, $_BLACK	
	push hl
	call graph_cls
	pop hl
	ld d, 11
	ld e, 11
	ld iy, loading_addr
	ld ix, font_raw_addr
	ld b, $_YELLOW | $_FLASHING
	ld c, $NORMAL
	push hl
	call graph_print
	pop hl
	call graph_swap_screen	
	ld hl, bk_attr_addr
	call graph_swap_screen_attr
	ret


draw_background:	
	ld b, $_BLACK
	call graph_cls	
	ret
	
start_menu:
	xor a, a	
	call graph_set_border
	ld ( menu_selected_addr ), a	
	ld hl, bk_screen_addr
	push hl
	call draw_background
	pop hl
	call graph_swap_screen	
	ld hl, bk_attr_addr
	call graph_swap_screen_attr
	; draw title	
	ld d, $TITLE_TOP
	ld e, $TITLE_LEFT
	ld iy, title_addr
	ld ix, font_raw_addr
	ld hl, bk_screen_addr
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
	push hl
	call graph_print
	pop hl	
	call graph_swap_screen	
	ld hl, bk_attr_addr
	call graph_swap_screen_attr	
	
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
	push ix
	push af
	push de
	call graph_print	
	pop de
	ld a, d
	add a, $LINE_INTERVAL
	ld d, a
	pop af
	pop ix	
	pop hl	
	pop bc	
	inc a	
	djnz draw_options_l1 
	ret
