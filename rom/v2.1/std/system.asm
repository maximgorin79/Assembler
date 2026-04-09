; Delay
; bc - delay in cycles
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

random:	
	ld a, r
	rla
	xor l
	rlca
	xor h	
	rlca
	xor d
	rlca
	xor e
	rlca
	xor b
	rlca
	xor c
	ld r, a
	ret
	
