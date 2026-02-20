; set border color
; a - color
graph_set_border:
	out ($_BORDER_PORT),a
	ret
	
graph_set_border_end:	

; clear screen by color in b register
; hl - address of back screen
graph_cls:		
	ld ( hl ), 0
	_ld de,hl
	inc de
	ld bc, $_SCREEN_SIZE + $_ATTR_SIZE - 1 	
	ldir
	ret

; print text on the back screen
; hl - address of back screen
; ix - font address
; iy - string address
; d - y coordinate, e - x coordinate
; c - selected(0xff) or not (0x00)
; b - color/bk
graph_print:
	push bc
	push hl
	push de
	push iy	
	add hl, de
	
graph_print_l1:	
	ld a, (iy + 0)
	inc iy
	or a, a
	jr z, graph_print_l2
	push ix	
	sub a, ' '		
	push hl
	push bc	
	push ix
	pop hl	
	rlca
	rlca
	rlca
	ld c, a
	and a, 0b00000111
	ld b, a
	ld a, c
	and a, 0b11111000
	ld c, a
	add hl, bc
	push hl
	pop ix
	pop bc
	pop hl
	push hl
	ld de, 32
	ld a, ( ix + 0 )
	xor a, c
	ld ( hl ), a
	add hl, de
	ld a, ( ix + 1 )
	xor a, c
	ld ( hl ), a
	add hl, de
	ld a, ( ix + 2 )
	xor a, c
	ld ( hl ), a
	add hl, de
	ld a, ( ix + 3 )
	xor a, c
	ld ( hl ), a
	add hl, de
	ld a, ( ix + 4 )
	xor a, c
	ld ( hl ), a
	add hl, de
	ld a, ( ix + 5 )
	xor a, c
	ld ( hl ), a
	add hl, de
	ld a, ( ix + 6 )
	xor a, c
	ld ( hl ), a
	add hl, de
	ld a, ( ix + 7 )
	xor a, c
	ld ( hl ), a
	pop hl
	inc hl
	pop ix
	jr graph_print_l1
	
graph_print_l2:
	pop iy
	pop de
	pop hl	
	ld bc, $_SCREEN_SIZE
	add hl, bc	
	ld a, d
	ld d, 0
	rla
	rlca
	rlca
	rlca
	rlca	
	ld c, a
	and a, 0b00011111
	ld b, a
	ld a, c
	and a, 0b11100000
	ld c, a
	add hl, bc	
	add hl, de
	pop bc
	
graph_print_l4:	
	ld a, ( iy + 0 )
	inc iy
	or a, a	
	jr z, graph_print_l3		
	ld ( hl ), b
	inc hl
	jr graph_print_l4
	
graph_print_l3:
	ret

; print title on the back screen
; hl - address of back screen
; ix - font address
; iy - string address
; d - y coordinate, e - x coordinate
; b - color/bk
graph_print_title:	
	push hl
	push de
	push iy	
	add hl, de
	
graph_printx2_l1:
	ld a, ( iy + 0 )
	inc iy
	or a, a
	jr z, graph_printx2_l2
	push ix	
	sub a, ' '		
	push hl	
	push ix
	pop hl	
	rlca
	rlca
	rlca
	ld c, a
	and a, 0b00000111
	ld b, a
	ld a, c
	and a, 0b11111000
	ld c, a
	add hl, bc
	push hl
	pop ix	
	pop hl
	push hl
	ld de, 64
	ld a, ( ix + 0 )	
	ld ( hl ), a		
	add hl, de
	ld a, ( ix + 1 )	
	ld ( hl ), a		
	add hl, de
	ld a, ( ix + 2 )	
	ld ( hl ), a		
	add hl, de
	ld a, ( ix + 3 )	
	ld ( hl ), a		
	add hl, de
	ld a, ( ix + 4 )	
	ld ( hl ), a		
	add hl, de
	ld a, ( ix + 5 )	
	ld ( hl ), a	
	add hl, de
	ld a, ( ix + 6 )	
	ld ( hl ), a	
	add hl, de
	ld a, ( ix + 7 )	
	ld ( hl ), a	
	pop hl
	inc hl	
	pop ix
	jr graph_printx2_l1
	
graph_printx2_l2:
	pop iy
	pop de
	pop hl	
	ld bc, $_SCREEN_SIZE
	add hl, bc	
	ld a, d
	ld d, 0
	rlca
	rlca
	rlca
	rlca
	rlca	
	ld c, a
	and a, 0b00011111
	ld b, a
	ld a, c
	and a, 0b11100000
	ld c, a
	add hl, bc	
	add hl, de	
	ld de, 32
	
graph_printx2_l4:
	ld a, (iy + 0)
	inc iy
	or a, a	
	jr z, graph_printx2_l3	
	ld b, $_BRIGHT | $_YELLOW
	ld ( hl ), b	
	push hl
	add hl, de
	ld b, $_BRIGHT | $_RED
	ld ( hl ), b	
	pop hl
	inc hl
	jr graph_printx2_l4
	
graph_printx2_l3:
	ret	
	
; Swap back screen into real screen without attribute area
; hl - address of back scren
graph_swap_screen:
	ld de, $_SCREEN_ADDR
	ld bc, 8 * 8 * 32
	push hl
	push bc
	call graph_swap_screen_block
	pop bc
	pop hl	
	add hl, bc
	push hl
	push bc
	call graph_swap_screen_block
	pop bc
	pop hl	
	add hl, bc	
	call graph_swap_screen_block		
	ret

; Swap block
; Uses: hl, de, bc, ixh, a	
graph_swap_screen_block:	
	ld b, 8
	ld a, 256 - 32
graph_swap_screen_block_1:		
	ld ixh, b ;keep b in ixh
	push hl	
	;--		
	ld b, 0
	ld c, 32		
	ldir	
	ld c, a
	add hl, bc
	ld c, 32		
	ldir
	ld c, a
	add hl, bc
	ld c, 32		
	ldir
	ld c, a
	add hl, bc
	ld c, 32		
	ldir
	ld c, a
	add hl, bc
	ld c, 32
	ldir
	ld c, a
	add hl, bc
	ld c, 32	
	ldir
	ld c, a
	add hl, bc
	ld c, 32	
	ldir
	ld c, a
	add hl, bc
	ld c, 32
	ldir	
	;--
	pop hl	
	ld c, 32
	add hl, bc
	ld b, ixh
	djnz graph_swap_screen_block_1
	ret

; hl - address of back attribute screen
; Uses: hl, de, bc
graph_swap_screen_attr:
	ld de, $_ATTR_ADDR
	ld bc, $_ATTR_SIZE
	ldir
	ret

; hl - address of back screen
; e - x, d - y
; b - color
; uses: ixl, a, c, iy
;set_pixel:
;	exx	
;	ld a, d
;	rla
;	rla
;	rla
;	rla
;	rla
;	ld c, a
;	and a, 0b00011111
;	ld b, a
;	ld a, c
;	and a, 0b11100000
;	ld c, a	
;	add hl, bc
;	ld a, e
;	rra
;	rra
;	rra
;	and a, 0b00011111
;	ld b, 0
;	ld c, a
;	add hl, bc
;	ld e, a
;	and a, 0b00000111
;	ld ( set_pixel_offset + 2), a
;	ld iy, set_pixel_array	
	
;set_pixel_offset:	
;	ld a, ( iy + 0)
;	ld ( hl ) , a
;	exx
;	; attributes
;	ld ixl, b
;	ld bc, 6144
;	add hl, bc
;	ld d, a
;	rla
;	rla
;	ld c, a
;	and a, 0b00011111
;	ld b, a
;	ld a, c
;	and a, 0b11100000
;	ld c, a	
;	add hl, bc
;	ld b, 0
;	ld c, c
;	add hl, bc
;	ld a, ixl
;	ld ( hl ), a 
;	ret
;	
;set_pixel_array:
;	defb 128, 64, 32, 16, 8, 4, 2, 1
	
	
