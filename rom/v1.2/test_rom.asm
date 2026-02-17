def PROG_ADDR	0000h

def PROC_ADDR	0x4000

def TEMPLATE_SIZE	59

rom_start:
org $PROG_ADDR
	di
	xor a, a
	out ( $_BORDER_PORT ), a
	; set 48k mode
	ld a, 0x30
	ld b, 0x7f
	ld c, 0xfd
	out ( c ), a
	ld a, 0x3f
	ld i, a
	;-------CLEAR MEMORY-------
	xor a, a
	ld bc, 0xc000
	ld hl, 0xffff	
	_ld de, hl
	ld ( hl ), a
	dec de	
	lddr		
	; -------START-------
	ld hl, rom_loader_start
	ld de, $PROC_ADDR
	ld bc, rom_loader_end - rom_loader_start
	ldir
	ld hl, rom_data_addr	
	ld a, 0
	jp $PROC_ADDR

rom_loader_start:
	resource "res/loader.bin"
	
rom_loader_end:

rom_data_addr:
	resource "res/panama.bin"
	
rom_end:
	alloc 16384 - (rom_end - rom_start)	
