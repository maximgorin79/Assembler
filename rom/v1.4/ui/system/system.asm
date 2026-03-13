; Delay
; bc - delay
system_delay:
	ld a, b
	or c
	jr z, system_delay_exit
	nop
	dec bc
	jr system_delay
system_delay_exit:
	ret	

system_random:
	ld a, r			; Load the A register with the refresh register
	ld l, a			; Copy register A into register L
	and a, 0x20		; This masking prevents the address we are forming from accessing RAM
	ld h, a			; Copy register A into register H	
	ld a,( hl )
	ret
