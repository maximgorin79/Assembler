; Loader for 128k mode for 128k-1Mb ROM-Disk
; in a, ( 0xfb) - to enter in ROM-Disk mode

def LOADER_ADDR 0x5b00

def PAGE_SELECT_PORT 0xfb

def SWITCH_OFF_PORT	0x7b

def TEMPLATE_SIZE	122

def ZX128_PAGE_SELECT_PORT	0x7ffd

	org $LOADER_ADDR

; a - current number
; hl - data src
loader_start:
	
	ld sp, loader_end + 18
	or a, a
	jr z, loader_l1	
	out ( $PAGE_SELECT_PORT ), a ; select a page
	ld ( loader_page_addr ), a
	
 loader_l1:
	ld b, 8 ; number of pages for 128k
	
 loader_main_loop:
	push bc
	call loader_read ;page number			

	ld de, 0x8000
	cp a, 4	
	jr z, loader_l3	

 	ld d, 0x40 	
	cp a, 8	
	jr z, loader_l3

	ld d, 0xc0
	
 loader_l3:
 	sub a, 3
	ld bc, $ZX128_PAGE_SELECT_PORT
	out ( c ), a
	
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
	jp loader_exec
	
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
	cp a, 0x40 ; h == 16384
	;jr  loader_read_l1
	jr nz, loader_read_l1
	ld a, ( loader_page_addr )
	inc a
	ld ( loader_page_addr ), a
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
	ld a, iyl
	ld ( de ), a
	inc de
	ret
	
 loader_no_write:
	ld a, iyl
	inc de	
	ret

_ldir:
	ld a, b
	or c
	ret z
	ldi
	ld a, h
	cp a, 0x40 ; h == 16384
	jr nz, _ldir
	;jr _ldir
	ld a, ( loader_page_addr )
	inc a
	ld ( loader_page_addr ), a
	out ( $PAGE_SELECT_PORT ), a ; switch page 	
	ld h, 0
	jr _ldir

loader_page_rst:
	in a, ( $SWITCH_OFF_PORT )
	ret

loader_exec:	
	ld de, $LOADER_ADDR
	ld bc, $TEMPLATE_SIZE
	call _ldir	
	call loader_page_rst	
	jp $LOADER_ADDR
	
loader_page_addr:
	defb 0

loader_end:
