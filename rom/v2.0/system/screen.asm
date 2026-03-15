; Swap back screen into real screen without attribute area
; hl - address of back screen
screen_swap:
	push de
	push bc
	push af
	push ix
	push hl
	ld de, $_SCREEN_ADDR
	ld bc, 8 * 8 * 32
	push hl
	push bc
	call screen_swap_block
	pop bc
	pop hl	
	add hl, bc
	push hl
	push bc
	call screen_swap_block
	pop bc
	pop hl	
	add hl, bc
	call screen_swap_block
	pop hl
	ld a, 0x18
	add a, h
	ld h, a
	call screen_swap_attr
	pop ix
	pop af
	pop bc
	pop de	
	ret

; Swap block
; Uses: hl, de, bc, ixh, a	
screen_swap_block:	
	ld b, 8
	ld a, 256 - 32
screen_swap_block_1:		
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
	pop hl	
	ld c, 32
	add hl, bc
	ld b, ixh
	djnz screen_swap_block_1	
	ret

; hl - address of back attribute screen
; Uses: hl, de, bc
screen_swap_attr:
	ld de, $_ATTR_ADDR
	ld bc, $_ATTR_SIZE
	ldir
	ret

screen_attr_cls:
	push bc
	push af
	ld a, 0x18
	add a, h
	ld h, a
	_ld de, hl
	inc de	
	ld ( hl ), c
	ld bc, $_ATTR_SIZE - 1
	ldir
	pop af
	pop bc
	ret
