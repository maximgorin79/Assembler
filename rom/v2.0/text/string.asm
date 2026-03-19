; Author Maxim Gorin (c) 2026

; Returns length of string in a register
; iy - string, with 0 as terminal symbol
strlen:
	push bc
	ld a, iyh
	or a, iyl
	ld c, 0
	jr z, strlen_exit	

 strlen_l1:
	ld a, ( iy + 0)
	or a, a
	jr z, strlen_exit
	inc c
	inc iy
	jr strlen_l1
	
 strlen_exit:
 	ld a, c
 	pop bc
	ret
