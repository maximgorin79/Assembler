	.org 16384
	ld sp,end
	ld hl,32768
	ld bc,16384
	call check_mem
	ret
check_mem:
	ld a,255
	ld d,a
	push af
	push hl
	push bc
	call fill
	pop bc
	pop hl
	pop af
	ld d,a
	push af
	push hl
	push bc
	call check
	and a
	jr nz, print_error
	pop bc
	pop hl
	pop af
	
		
fill:
	ld a,d
	ld (hl),a
	inc hl
	dec bc
	ld a,b
	xor c
	jr nz,fill
	ret
print_error:
check:	
	ld a,(hl)
	cp d
	jr nz,check_error
	inc hl
	dec bc
	ld a,b
	xor c
	jr nz,check
	ld a,0
	ret
check_error:
	ld a,255
	ret
end:
