; Show GUI elemenst like window, menu items
; The general rule: passed arguments in procedures will not restore after execution 
; and A register as well

include "win.hasm"

include "../text/string.asm"

item_top:
	equ 0x5b00 + 13

items_current_row_in_column:
	equ 0x5b00 + 14

items_current_row:
	equ 0x5b00 + 15
	
items_window_size:
	equ 0x5b00 + 16

items_offset:
	equ 0x5b00 + 17

item_selected:
	equ 0x5b00 + 18

item_prev_selected:
	equ 0x5b00 + 19

items_repaint:
	equ 0x5b00 + 20
	
items_count:
	equ 0x5b00 + 21

; bc - height, width
_calc_center:
	ld a, $DESKTOP_WIDTH
	sub a, c
	rrca
	and a, 0b01111111
	ld e, a
	ld a, $DESKTOP_HEIGHT
	sub a, b
	rrca
	and a, 0b01111111
	ld d, a
	ret


; de - y, x
; hl - screen addr
; returns address of drawing
; it works correctly when l == 0
_calc_point_addr:	
 	add hl, de	
	ret

; de - y, x
; hl - screen addr
; returns address of drawing
; it works correctly when l == 0
_calc_attr_addr:	
	ld l, e
	ld a, d
	rrca
	rrca
	rrca
	ld e, a
	and a, 0b00000011
	ld d, a
	ld a, e
	and a, 0b11100000
	ld e, a
 	add hl, de	
	ret

; de - y, x
; bc - height, width
; hl - screen addr 
; a - color
_draw_bk_bar:
	ex af, af'
	ld  a, b
	or a, a
	ret z
	ld a, c
	or a, a
	ret z
	ld a, h
	add a, 24
	ld h, a ; calculate attr address
	call _calc_attr_addr		
	dec c	
	ex af, af'
	ld e, a
	ld d, 0

 _draw_bk_bar_l1:
	push bc
	push de     
	ld ( hl ), e	
	xor a, a
	or a, c
	jr z, _draw_bk_bar_skip
	ld b, d
	_ld de, hl
	inc de	
	push hl
	ldir
	pop hl

 _draw_bk_bar_skip:
	ld de, 32
	add hl, de	
	pop de
	pop bc
	djnz _draw_bk_bar_l1
	ret

; hl - screen address
; ix - font address
; a - symbol code

_draw_symbol:
	push bc
	push de
	sub a, ' '
	rlca
	rlca
	rlca
	ld c, a
	and a, 0b00000111
	ld b, a
	ld a ,c
	and a, 0b11111000
	ld c, a
	ld d, ixh
	ld e, ixl
	ex de, hl
	add hl, bc
	ex de, hl
	ld ixh, d
	ld ixl, e
	ld de, 32
	ld a, ( ix + 0)
	ld ( hl ), a
	add hl, de
	ld a, ( ix + 1)
	ld ( hl ), a
	add hl, de
	ld a, ( ix + 2)
	ld ( hl ), a
	add hl, de
	ld a, ( ix + 3)
	ld ( hl ), a
	add hl, de
	ld a, ( ix + 4)
	ld ( hl ), a
	add hl, de
	ld a, ( ix + 5)
	ld ( hl ), a
	add hl, de
	ld a, ( ix + 6)
	ld ( hl ), a
	add hl, de
	ld a, ( ix + 7)
	ld ( hl ), a
	pop de
	pop bc
	ret

; hl - screen address
; ix - font address
; iy - string with 0 as terminal symbol
_draw_string:
	ld a, ( iy + 0)
	or a
	ret z	
	push hl
	push ix
	call _draw_symbol
	pop ix
	pop hl
	inc hl
	inc iy
	jr _draw_string 	


; hl - screen address
; de - y, x
; bc - height, width
; ix - font address
; iy - title or null
_draw_frame:
	push hl ;store hl for a title
	call _calc_point_addr
	dec c
	dec c
	dec b
	dec b	
	ld a, '{'
	push hl
	push ix
	call _draw_symbol
	pop ix
	pop hl
	inc hl
	ld a, c
	or a, a
	jr z, _draw_frame_skip_hor1
	push bc
	ld b, c
	
 _draw_frame_l2:
	push hl
	push ix
	ld a, '~'
	call _draw_symbol
	pop ix
	pop hl
	inc hl
	djnz _draw_frame_l2
	pop bc
	
 _draw_frame_skip_hor1:
 	push hl
 	push ix
	ld a, '|'
	call _draw_symbol
	pop ix
	pop hl
	inc h
	; vertical
	ld a, b
	or a, a
	jr z, _draw_frame_skip_ver1	
	push bc	
	
_draw_frame_l4:	
	push hl
	push ix
	ld a, 127
	call _draw_symbol
	pop ix
	pop hl		
	inc h
	djnz _draw_frame_l4
	pop bc
	
 _draw_frame_skip_ver1:	
	push hl
	push ix
	ld a, 96
	call _draw_symbol
	pop ix
	pop hl
	dec hl
	ld a, c
	or a, a
	jr z, _draw_frame_skip_hor2
	push bc
	ld b, c
	
 _draw_frame_l5:
	push hl
	push ix
	ld a, '~'
	call _draw_symbol
	pop ix
	pop hl
	dec hl
	djnz _draw_frame_l5
	pop bc

 _draw_frame_skip_hor2:
 	push hl
 	push ix
	ld a, 94
	call _draw_symbol
	pop ix
	pop hl
	dec h
	; vertical
	ld a, b
	or a, a
	jr z, _draw_frame_skip_ver2
	push bc	
	
_draw_frame_l6:	
	push hl
	push ix
	ld a, 127
	call _draw_symbol
	pop ix
	pop hl		
	dec h
	djnz _draw_frame_l6
	pop bc

 _draw_frame_skip_ver2: 	
 	pop hl
 	push iy
 	call strlen 	
 	pop iy	
 	or a, a
 	jr z, _draw_frame_exit
 	ld b, a
 	ld a, c
 	sub a, b
 	rrca
 	and a, 0b01111111
 	add a, e 	
 	ld e, a
 	inc e
 	call _calc_point_addr 	
 	call _draw_string

 _draw_frame_exit: 	
	ret

; de - y, x
; hl - screen address
; bc - height, width 
_draw_shadow:
	ex af, af'
	ld a, b
	or a, a
	jr z,  _draw_shadow_exit
	ld a, c
	or a, a
	jr z,  _draw_shadow_exit
	ld a, h
	add a, 24
	ld h, a ; calculate attr address
	push de
	push hl
	push bc
	inc e
	ld a, d
	add a, b	
	ld d, a
	ld a, e
	cp a, 32
	jr nc, _draw_shadow_skip
	call _calc_attr_addr
	ld b, c
	ld a, ( shadow_color )
	
 _draw_shadow_l2:	
	ld ( hl ), a
	inc hl	
	djnz _draw_shadow_l2
	
 _draw_shadow_skip:	
	pop bc 
	pop hl
	pop de
	inc d
	ld a, e
	add a, c	
	ld e, a
	ld a, d
	cp a, 24
	jr nc, _draw_shadow_exit 
	call _calc_attr_addr
	ld de, 32
	ld a, ( shadow_color )

 _draw_shadow_l1:
	ld ( hl ), a
	add hl, de
	djnz _draw_shadow_l1

 _draw_shadow_exit:
	ex af, af'
	ret

shadow_color:
	defb $_BLACK | $_BRIGHT

; de - y, x
; hl - screen address
; bc - height, width
; ix - font
_draw_separator:
	ld a, b
	cp a, 2
	ret c
	ld a, c
	cp a, 3
	ret c
	ld a, c
	rrca
	and 0b01111111
	add a, e
	ld e, a	
	dec b
	dec b
	inc d
	call _calc_point_addr

 _draw_separator_l1:
	push hl
	push ix
	ld a, 125
	call _draw_symbol
	pop ix
	pop hl
	inc h
	djnz _draw_separator_l1
	ld a, '#'
	call _draw_symbol
	ret

; de - y, x
; hl - screen address
; bc - height, width
; ix - font
_draw_alert:	
	ld a, c
	cp a, 2 ; width less 2
	ret c
	ld a, b
	cp a, 2
	ret c
	ld a, e
	cp a, 32
	ret nc
	ld a, d
	cp a, 24
	ret nc
	push ix
	push iy
	ld iy, 0
	push hl
	push de
	push bc	
	call _draw_frame	
	pop bc
	pop de
	pop hl
	pop iy
	pop ix	
	push hl
	push de
	push bc
	ld a, ( alert_color )
	call _draw_bk_bar	
	pop bc
	pop de
	pop hl
	
	push hl
	push de
	inc e
	inc d	
	push bc
	ld b, c
	dec b
	dec b
	ld a, ( alert_msg_color )
	ld c, a
	call _draw_item
	pop bc
	pop de
	pop hl	
	
	push hl
	push de
	push bc	
	call _draw_shadow
	pop bc
	pop de	
	pop hl		
	ret

alert_color:
	defb $_BK_RED | $_BLACK ;bk color
	
alert_msg_color:
	defb $_BK_RED | $_WHITE ;message color

_draw_main_window:
	ex af, af'
	ld a, c
	cp a, 2 ; width less 2
	ret c
	ld a, b
	cp a, 3
	ret c
	ld a, e
	cp a, 32
	ret nc
	ld a, d
	cp a, 24
	ret nc
	ex af, af'
	; draw window frame
	push hl
	push de
	push bc
	dec b	
	call _draw_frame	
	pop bc
	pop de
	pop hl
	; draw menu separator
	push hl
	push de
	push bc
	dec b
	push ix
	call _draw_separator
	pop ix
	pop bc
	pop de
	pop hl
	; draw window background
	push hl
	push de
	push bc
	ld a, ( main_win_color )
	call _draw_bk_bar	
	pop bc
	pop de
	pop hl
	; draw cli
	push hl
	push de
	push bc	
	ld a, d	
	add a, b	
	dec a
	ld d, a
	inc e	
	ld a, ( cli_color )
	ld c, a
	ld iy, cli_string
	ld b, 5
	call _draw_item
	pop bc
	pop de
	pop hl
	; draw shadow
	push hl
	push de
	push bc	
	call _draw_shadow
	pop bc
	pop de	
	pop hl
	ret
	
main_win_color:
	defb $_BK_BLUE | $_GREEN ;bk color

cli_color:
	defb $_BK_BLUE | $_WHITE ;cli color

cli_string:
	defb "ROM:>"


; hl - screen address
; de - y, x
; ix - font address
; iy - string data
; c - color
; b - symbol count
_draw_item:
	push de
	push bc
	push hl
	call _calc_point_addr
	
_draw_item_l1:	
	ld a, ( iy + 0)
	push hl
	push ix
	call _draw_symbol
	pop ix
	pop hl
	inc hl
	inc iy
	djnz _draw_item_l1
	pop hl	
	pop bc
	pop de	
	ld a, c
	ld c, b
	ld b, 1
	call _draw_bk_bar
	ret

_init_ui:
	xor a, a
	ld ( item_top ), a
	ld ( items_current_row_in_column ), a
	ld ( items_current_row ), a
	ld ( items_window_size ), a
	ld ( items_offset ), a
	ld ( item_selected ), a
	ld ( item_prev_selected ), a	
	ld ( items_count ), a	
	ld a, 1
	ld ( items_repaint ), a
	
	
	ret


; Draw two columns of items with selection
; hl - screen address
; de - y, x
; ix - font address
; iy - items string
; c - item length
; b - column size

_draw_items:
	ld a, ( items_count )
	or a, a
	ret z
	ld a, ( items_offset )
	or a, a
	jr z, _draw_items_nocalc
	ld a, c
	exx
	_ld de, iy
	ex de, hl
	ld d, 0
	ld e, a
	ld a, ( items_offset )
	ld b, a
	
 _draw_items_mul:
 	add hl, de
 	djnz _draw_items_mul
 	ex de, hl
 	_ld iy, de
 	exx
	
 _draw_items_nocalc:
	ld a, d
	ld ( item_top ), a	
	ld a, b
	rlca	
	ld ( items_window_size ), a
	xor a, a
	ld ( items_current_row_in_column ), a	
	ld a, ( items_offset )
	ld ( items_current_row), a
	ld b, a
	ld a, ( items_count )
	sub a, b
	ld b, a
	ld a, ( items_window_size )
	cp a, b
	jr nc, _draw_items_l1
	ld b, a
	
 _draw_items_l1:	
	push hl
	push de	
	push iy	
	push bc
	push ix
	ld b, c
	ld a, ( items_current_row )	
	ld c, a	
	ld a, ( items_repaint)
 	or a, a
 	jr nz, _draw_items_repaint_normal 	
	ld a, ( item_selected )
	cp a, c
	jr nz, _draw_items_normal
	ld a, ( item_selected_color )
	jr _draw_items_draw
	
 _draw_items_normal:
	ld a, ( item_prev_selected )
	cp a, c
	jr nz, _draw_items_nodraw 
	ld a, ( item_color )	
	jr _draw_items_draw
	
 _draw_items_repaint_normal:
	ld a, ( item_selected )
	cp a, c
	jr nz, _draw_items_no_selected 
	ld a, ( item_selected_color )	
	jr _draw_items_draw
	
 _draw_items_no_selected:	 	
 	ld a, ( item_color )	
	
 _draw_items_draw:	
 	ld c, a
	call _draw_item
	
 _draw_items_nodraw:
	pop ix
	pop bc		
	pop iy
	_ld de, iy	
	ld h, 0
	ld l, c
	ex de, hl
	add hl, de
	ex de, hl
	_ld iy, de	
	pop de	
	inc d
	ld hl, items_current_row
	inc ( hl )
	ld hl, items_current_row_in_column	
	inc ( hl )
	ld a, ( items_window_size )
	rrca
	cp a, ( hl )
	pop hl
	jr nz, _draw_items_l2
	xor a, a
	ld ( items_current_row_in_column ), a
	ld a, ( item_top )
	ld d, a
	ld a, e
	add a, c
	inc a
	ld e, a
	
 _draw_items_l2:
	djnz _draw_items_l1
	xor a, a
	ld ( items_repaint), a
	ret

_draw_all_items:
	ld a, 1
	ld ( items_repaint ), a
	call _draw_items
	ret

item_color:
	defb $_BK_BLUE | $_WHITE

item_selected_color:
	defb $_BK_GREEN


_items_navigate_up:	
	ld a, ( item_selected )
	or a, a
	ret z
	ld ( item_prev_selected ), a
	dec a
	push hl
	ld hl, items_offset
	cp a, ( hl )
	jr nc, _items_navigate_up_l1
	dec ( hl )
	ex af, af'
	ld a, 1
	ld ( items_repaint), a
	ex af, af'
	
 _items_navigate_up_l1:	
	ld ( item_selected ), a	
	pop hl
	ret

; b - column size
_items_navigate_down:
	ld a, b
	rlca	
	push hl
	ld hl, items_offset
	add a, ( hl )
	ld b, a
	ld a, ( items_count)
	cp a, b
	jr nc, _items_navigate_down_l0
	ld b, a
	
 _items_navigate_down_l0:	
	ld a, ( item_selected )
	ld ( item_prev_selected ), a
	inc a
	cp a, b
	jr c, _items_navigate_down_l1	
	ld b, a
	ld a, ( items_count )
	cp a, b	
	jr z, _items_navigate_down_l2
	inc ( hl )
	ld a, 1
	ld ( items_repaint), a
	ld a, b
 _items_navigate_down_l1:	
 	ld ( item_selected ), a 	
	
 _items_navigate_down_l2:
	pop hl
	ret
	
; b - column size
_items_navigate_left:	
	push hl
	ld hl, item_selected
	ld a, ( hl )
	ld ( item_prev_selected ), a
	sub a, b
	jr c, _items_navigate_left_l1
	ld b, a
	ld a, ( items_offset )
	cp a, b
	jr nc, _items_navigate_left_l1
	ld a, b
	ld ( hl ), a
	pop hl
	ret
 _items_navigate_left_l1:
 	xor a, a 	
 	ld ( hl ), a	
 	ld hl, items_offset
 	ld a, ( hl )
 	or a, a
 	jr z, _items_navigate_left_exit
 	xor a, a
 	ld ( hl ), a
 	ld a, 1
 	ld ( items_repaint), a
 	
 _items_navigate_left_exit:
	pop hl
	ret
	
; b - column size
_items_navigate_right:
	push hl
	ld hl, item_selected
	ld a, ( hl )
	ld ( item_prev_selected ), a
	add a, b
	ex af, af'
	ld a, b
	rlca
	ld b, a
	ld a, ( items_count )
	cp a, b
	jr nc, _items_navigate_right_l0
	ld b, a
	
 _items_navigate_right_l0:
	ld a, ( items_offset )
	add a, b
	ld b, a
	ex af, af'
	cp a, b
	jr nc, _items_navigate_right_l1
	ld ( hl ), a
	pop hl
	ret
	
 _items_navigate_right_l1:
 	ld a, ( items_count )
 	dec a
 	ld ( hl ), a
 	ld a, b
 	ld hl, items_offset
 	sub a, ( hl )
 	ld b, a
 	ld a, ( items_count )
 	sub b
 	cp a, ( hl )
 	jr z, _items_navigate_right_exit
 	ld ( hl ), a
 	ld a, 1
 	ld ( items_repaint ), a
 	
 _items_navigate_right_exit: 	
 	pop hl 
	ret

; hl - screen address
; a - color
_clear_desktop:
	ex af, af'
	_ld de, hl
	inc de
	ld bc, $_SCREEN_SIZE - 1
	xor a, a
	ld ( hl ), a
	ldir
	inc hl
	inc de
	ex af, af'	
	ld ( hl ), a
	ld bc, $_ATTR_SIZE - 1
	ldir
	ret