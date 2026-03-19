; Author Maxim Gorin (c) 2026

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
