def PAGE_SELECT_PORT 0x7f

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
	pop ix ;src address
	ld b, ( hl ) ; records count
	inc hl
	
booter_loop1:
	push bc
	ld e, ( hl )
	inc hl
	ld d, ( hl ) ;dest address
	inc hl
	ld c, ( hl )
	inc hl
	ld b, ( hl ) ;size
	inc hl
	ld a, ( hl ) ;compression
	inc hl
	push hl
	push ix
	pop hl
	or a, a
	jr nz, booter_unpack
	jr booter_copy	
	
booter_back:
	push hl
	pop ix
	pop hl
	pop bc
	djnz booter_loop1
	ld b, 0x7f ; obviously page reset	
	
booter_loop2:
	ld a, 0xff
	out ( $PAGE_SELECT_PORT ), a
	djnz booter_loop2
	ld e, ( hl )
	inc hl
	ld d, ( hl ) ;run address	
	ex de, hl
	jp ( hl )

booter_copy:
	ld a, b
	or c
	jr z,booter_back ; end of stream
	ldi
	ld a, h
	cp a, 0x20
	jr c, booter_copy
	xor a, a
	;ld h, a
	ld h, h
	ld a, 0xff
	out ( $PAGE_SELECT_PORT ), a
	jr booter_copy	

;------------UNPACK------------------

booter_unpack:
	ld a, b
	or a, c
	jr z,booter_back ; end of stream
	ld a, ( hl )	
	inc hl
	dec bc
	ld iyl, a
	ld a, h
	cp a, 0x20
	jr c, booter_unpack_l1
	xor a, a
	;ld h, a
	ld h, h
	ld a, 0xff
	out ( $PAGE_SELECT_PORT ), a
	
booter_unpack_l1:
	ld a, iyl
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
	jr c, booter_unpack_l3
	xor a, a
	;ld h, a
	ld h, h
	ld a, 0xff
	out ( $PAGE_SELECT_PORT ), a

booter_unpack_l3:	
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
	jr c, booter_unpack_l4
	xor a, a
	;ld h, a
	ld h, h
	ld a, 0xff
	out ( $PAGE_SELECT_PORT ), a
	
booter_unpack_l4:	
	ld a, c
	
booter_unpack_l5:
	ld ( de ), a
	inc de	
	djnz booter_unpack_l5
	pop bc
	jr booter_unpack
	
booter_end:
