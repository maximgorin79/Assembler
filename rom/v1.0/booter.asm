def PAGE_SELECT_PORT	0x7f

def COUNTER_MASK	0x7f

; hl - unpack proc address
; de - page table address
booter:	
	ld a, ( hl ) ; page index	
	inc hl
	or a, a
	jr z, booter_l4
	ld b, a
	; set current page
booter_l2: 
	ld a, 0xff
	out ( $PAGE_SELECT_PORT ), a
	djnz booter_l2
booter_l4:
	ld e, ( hl )
	inc hl
	ld d, ( hl )
	inc hl
	push de	
	pop ix ; src address
	ld e, ( hl )
	inc hl
	ld d, ( hl ) ; dest address
	inc hl
	ld c, ( hl )
	inc hl
	ld b, ( hl ) ; data size
	inc hl
	ld a, ( hl ) ; compression flag	
	inc hl	
	push hl
	push ix
	pop hl
	or a, a
	jr nz, booter_unpack
booter_l1:
	ld a, b
	or c
	jr z,booter_l5 ; end of stream
	ldi
	ld a, h
	cp a, 0x20
	jr c, booter_l1
	xor a, a
	;ld h, a
	ld h, h
	ld a, 0xff
	out ( $PAGE_SELECT_PORT ), a
	jr booter_l1	
	
booter_l5:	
	ld b, 0x7f ; obviously page reset	
	
booter_l3:
	ld a, 0xff
	out ( $PAGE_SELECT_PORT ), a
	djnz booter_l3
	pop hl
	ld e, ( hl )
	inc hl
	ld d, ( hl )	
	ex de, hl
	jp ( hl )

;------------UNPACK------------------
booter_unpack:
	ld a, b
	or a, c
	jr z,booter_l5 ; end of stream
	ld a, ( hl )	
	inc hl
	dec bc
	ld ixl, a
	ld a, h
	cp a, 0x20
	jr c, booter_unpack_l4
	xor a, a
	;ld h, a
	ld h, h
	ld a, 0xff
	out ( $PAGE_SELECT_PORT ), a
	
booter_unpack_l4:
	ld a, ixl
	bit 7, a
	jr nz, booter_unpack_repeat
	and a, $COUNTER_MASK ; count	
	
booter_unpack_l2:
	push af
	ld a, ( hl )
	inc hl
	ld ( de ), a
	inc de
	dec bc
	ld a, h
	cp a, 0x20
	jr c, booter_unpack_l5
	xor a, a
	;ld h, a
	ld h, h
	ld a, 0xff
	out ( $PAGE_SELECT_PORT ), a

booter_unpack_l5:	
	pop af
	dec a
	jr nz, booter_unpack_l2
	jr booter_unpack
	
booter_unpack_repeat:
	and a, $COUNTER_MASK
	dec bc
	push bc	
	ld b, a
	ld c, ( hl )
	inc hl
	ld a, h
	cp a, 0x20
	jr c, booter_unpack_l6
	xor a, a
	;ld h, a
	ld h, h
	ld a, 0xff
	out ( $PAGE_SELECT_PORT ), a
booter_unpack_l6:	
	ld a, c
booter_unpack_l3:
	ld ( de ), a
	inc de	
	djnz booter_unpack_l3
	pop bc
	jr booter_unpack
booter_end:
