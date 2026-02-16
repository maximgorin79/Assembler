def LOADER_ADDR 0x5000

org $LOADER_ADDR

def PAGE_SELECT_PORT 0x7f

def TEMPLATE_SIZE	59

; a - current number
; hl - data src
loader_start:
	ld sp, loader_end + $TEMPLATE_SIZE + 64
	or a, a
	jr z, loader_l1
	ld b, a
	
 loader_loop1: ; set current page	
	ld a, 0xff
	out ( $PAGE_SELECT_PORT ), a
	djnz loader_loop1
	
 loader_l1:
	ld b, 3
	
 loader_main_loop:
	push bc
	call loader_read ;page number		
	cp a, 4
	jr nz, loader_l2
	ld de, 0x8000
	jr loader_l3
	
 loader_l2:
	cp a, 5
	jr nz, loader_l4
	ld de, 0xc000
	jr loader_l3

 loader_l4:
	cp a, 8
	jr nz, reboot
	ld de, 0x4000	
	
 loader_l3:
	call loader_read
	ld c, a
	call loader_read
	ld b, a ; block size	
	and a, c	
	cp a, 0xff	
	jr z, loader_copy ; jump if bc == 0xffff
	jr loader_unpack
	
 loader_l5:
	pop bc
	djnz loader_main_loop
	call loader_page_rst
	jp loader_run

loader_page_rst:
	ld b, 0x7f
	
 loader_page_rst_loop:	
	ld a, 0xff
	out ( $PAGE_SELECT_PORT ), a
	djnz loader_page_rst_loop
	ret
	
reboot:
	call loader_page_rst
	rst 0h
	
loader_copy:
	ld a, b
	or c
	jr z, loader_l5
	call loader_read
	dec bc
	call loader_write
	jr loader_copy

loader_unpack:
	ld a, b
	or c
	jr z, loader_l5
	call loader_read
	dec bc
	cp a, 0xed
	jr nz, loader_unpack_l1
	ld ( loader_store + 1 ), a
	call loader_read
	dec bc
	ld ( loader_store + 2 ), a
	cp a, 0xed	
	jr nz, loader_unpack_l2
	call loader_read
	dec bc
	push bc
	ld b, a ; repeat number
	call loader_read ; value
	
 loader_unpack_l3:	
	call loader_write
	djnz loader_unpack_l3
	pop bc	
	dec bc
	jr loader_unpack
	
 loader_unpack_l2:	
	ld a, ( loader_store + 1)
	call loader_write
	ld a, ( loader_store + 2)
	
 loader_unpack_l1:
	call loader_write
	jr loader_unpack

loader_read:
	ld a, ( hl )
	inc hl
	ld ( loader_store ), a	
	ld a, h
	cp a, 0x40 ; hl >= 16384
	jr nz, loader_read_l1
	xor a, a
	ld h, a
	;ld h, h
	ld a, 0xff
	out ( $PAGE_SELECT_PORT ), a

 loader_read_l1:
	ld a, ( loader_store )
	ret

loader_write:
	ld ( loader_store ), a	
	ld a, d	
	cp a, $LOADER_ADDR >> 8
	jr z, loader_no_write
	cp a, 0x01 | ($LOADER_ADDR >> 8)
	jr z, loader_no_write
	ld a, ( loader_store )
	ld ( de ), a
	inc de
	ret
	
 loader_no_write:
	ld a, ( loader_store )
	inc de	
	ret
	
loader_store:
	defb 0, 0, 0
	
loader_run:
	ld de, loader_end
	ld bc, $TEMPLATE_SIZE	
	
loader_run_l1:
	ld a, b
	or c
	jr z, loader_run_exit_loop
	call loader_read
	ld ( de ), a
	inc de
	dec bc
	jr loader_run_l1
	
loader_run_exit_loop:	
	call loader_page_rst
	
loader_end:
