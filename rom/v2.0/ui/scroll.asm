; ix - font address
; a - code of symbol
__copy_symbol_data:
	push hl
	push de
	push bc
	_ld de, ix
	ex de, hl
	rlca
	rlca
	rlca
	ld e, a
	and a, 0b00000111
	ld d, a
	ld a, e
	and a, 0b11111000
	ld e, a
	add hl, de
	ld de, scroll_first_symbol
	ld bc, 8
	ldir
	pop bc
	pop de
	pop hl
	ret
	
; ix - font address
; iy - text text with 0 as terminal symbol
_scroll_init:
	xor a, a
	ld ( scroll_symbol_index ), a
	ld ( scroll_symbol_index  + 1 ), a
	ld a, 8
	ld ( scroll_scroll_count ) , a
	ld a, ( iy + 0 )
	sub a, ' '
	call __copy_symbol_data
	ret

; hl - address of line
; de - address of first symbol
; ix - font address
; iy - text with 0 as terminal symbol
; b - line count
_scroll_line:
	ld a, ( hl )
	rlca
	rlca
	and a, 0b11111100
	ld ( hl ), a
	inc hl
	dec b

 _scroll_line_l1:
	ld a, ( hl )
	rlca
	rlca
	ld c, a
	and a, 0b11111100
	ld ( hl ), a
	dec hl
	ld a, c
	and a, 0b00000011
	or a, ( hl )
	ld ( hl ), a
	inc hl
	inc hl
	djnz _scroll_line_l1
	dec hl
	ld a, ( de )
	rlca
	rlca
	ld c, a
	and a, 0b11111100
	ld ( de ), a
	ld a, c
	and a, 0b00000011	
	or a, ( hl )
	ld ( hl ), a
	ret

; hl - address of line
; ix - font address
; iy - text with 0 as terminal symbol
; b - line count
_scroll_all_lines:
	push de
	push hl
	ld de, scroll_first_symbol
	ld c, b
	ld b, 8
	
 _scroll_all_lines_l1:
	push bc
	push hl
	ld b, c	
	call _scroll_line
	pop hl
	ld bc, 32
	add hl, bc
	pop bc
	inc de
	djnz _scroll_all_lines_l1

	ld a, ( scroll_scroll_count )
	sub a, 2
	ld ( scroll_scroll_count ), a
	jr nz, _scroll_all_lines_exit
	ld a, 8
	ld ( scroll_scroll_count ), a
	ld de, ( scroll_symbol_index )
	inc de
	ld ( scroll_symbol_index ), de
	ex de, hl
	_ld de, iy
	add hl, de
	ld a, ( hl )
	or a, a
	jr nz, _scroll_all_lines_next
	ld de, 0
	ld ( scroll_symbol_index ), de
	ld a, ( iy + 0)

 _scroll_all_lines_next:
	sub a, ' '
	call __copy_symbol_data

 _scroll_all_lines_exit:
	pop de
	pop bc	
	ret

scroll_first_symbol:
	defb 1, 0, 1, 0, 1, 0, 1, 0

scroll_symbol_index:
	defw 0

scroll_scroll_count:
	defb 0
