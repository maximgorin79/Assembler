;system_rand:
	;ld hl,(RandSeed)
	;ld a,r
	;ld d,a
	;ld e,(hl)
	;add hl,de
	;add a,l
	;xor h
	;ld (RandSeed),hl
	;ret

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