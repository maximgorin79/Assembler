	ld a, 0 ; border color
	out ( 0xfe ), a
	ld hl, 0 ; alt hl
	ld de, 0 ; alt de
	ld bc, 0 ; ald bc
	exx
	ld de, 0 ; alt af
	push de
	pop af
	ex af, af'
	ld ix, 0 ; ix	
	ld iy, 0 ; iy
	ld bc, 0 ; bc	
	ld a, 0
	ld i, a ; i		
	ld hl, 0 ; hl	
	ld a, 0 ; r
	ld r, a	
	ld de, 0 ; af
	push de
	pop af
	ld de, 0 ; de	
	ld sp, 0 ; sp
	defb 0 ; di or ei
	defb 0xed, 0 ; im 0-2
	jp 0	; pc
