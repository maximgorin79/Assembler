def LOADER_ADDR 0x5b00

def PAGE_SELECT_PORT 0x7f

def SCREEN_ADDR	0x4000

org $LOADER_ADDR

; a - current number
; hl - data src
loader_start:
	ld sp, loader_end + 32
	or a, a
	jr z, loader_l1
	ld b, a
	
 loader_loop1: ; set current page	
	ld a, 0xff
	out ( $PAGE_SELECT_PORT ), a
	djnz loader_loop1
	
 loader_l1:
	ld b, 3 ; number of pages
	
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
	ld hl, 0xffff
	jp $SCREEN_ADDR	

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
	ld ixl, a
	call loader_read
	dec bc
	ld ixh, a
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
	ld a, ixl
	call loader_write
	ld a, ixh
	
 loader_unpack_l1:
	call loader_write
	jr loader_unpack

loader_read:
	ld a, ( hl )
	inc hl
	ld iyl, a	
	ld a, h
	cp a, 0x20 ; hl >= 16384
	jr  loader_read_l1
	;jr nz, loader_read_l1
	ld a, 0xff
	out ( $PAGE_SELECT_PORT ), a ; switch page 
	ld h, 0

 loader_read_l1:
	ld a, iyl
	ret

loader_write:
	ld iyl, a	
	ld a, d	
	cp a, $LOADER_ADDR >> 8
	jr z, loader_no_write
	cp a, $SCREEN_ADDR >> 8
	jr z, loader_no_write
	ld a, iyl
	ld ( de ), a
	inc de
	ret
	
 loader_no_write:
	ld a, iyl
	inc de	
	ret	

loader_end:
