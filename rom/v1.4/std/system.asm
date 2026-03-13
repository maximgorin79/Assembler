; Delay
; bc - delay
delay:
	push af
	push bc

 delay_start:
	ld a, b
	or c
	jr z, delay_exit
	nop
	dec bc
	jr delay_start

 delay_exit:
	pop bc
	pop af
	ret	

random:
	push af
	push hl
	ld a, r			; Load the A register with the refresh register
	ld l, a			; Copy register A into register L
	and a, 0x20		; This masking prevents the address we are forming from accessing RAM
	ld h, a			; Copy register A into register H	
	ld a,( hl )
	pop hl
	pop af
	ret

clear_memory:
	push bc
	push hl
	push de
	ld bc, 0xc000
	ld hl, 0xffff	
	ld d, h
	ld e, l
	dec de
	ld ( hl ), c
	lddr	
	pop de
	pop hl
	pop bc
	ret

set_48k_mode:
	push af
	push bc
	ld a, 0x30
	ld b, 0x7f
	ld c, 0xfd
	out ( c ), a
	pop bc
	pop af
	ret