def AY_REG_SELECT_PORT	0xfffd

def AY_REG_VALUE_PORT	0xbffd

	ld hl, ay_reg_last
	push hl
	ld d, ( hl )
	inc hl
	inc hl		
	xor a, a
	ld e, a
	ld b, 16
	
loop:	
	push bc
	ld a, e
	cp a, d
	jr z, skip_put_reg
	ld bc, $AY_REG_SELECT_PORT
	out ( c ), a	
	ld a, ( hl )
	inc hl
	ld bc, $AY_REG_VALUE_PORT
	out ( c ), a	
	
skip_put_reg:	
	inc e
	pop bc
	djnz loop
	pop hl
	ld a, ( hl ) ; register number
	inc hl
	ld bc, $AY_REG_SELECT_PORT
	out ( c ), a	
	ld a, ( hl ); register value
	ld bc, $AY_REG_VALUE_PORT
	out ( c ), a

include "init.asm"

ay_reg_last:
	defb 0, 0 ; reg number, reg value
	
ay_reg_values:	
	defb 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0
