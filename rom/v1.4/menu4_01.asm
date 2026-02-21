; v1.1

def PROG_ADDR	0xc000

def LOADER_START_ADDR	0x5b00

; Back screen address
bk_screen_addr:
	equ 0x8000

; Back screen buffer to draw primitives	
bk_attr_addr:
	equ bk_screen_addr + $_SCREEN_SIZE

org $PROG_ADDR

main:
	di	
	ld sp, 0xff56	
	ld a, 0x30
	ld b, 0x7f
	ld c, 0xfd
	out ( c ), a	
	ld a,0x3f
	ld i, a
	; clear memory
	ld bc, 0xc000
	ld hl, 0xffff	
	ld d,h
	ld e,l
	dec de
	xor a, a
	ld ( hl ), a
	;lddr	
	call start_menu	
	call loading_menu	
	; ----------------------------------------
	ld hl, loader_exec_begin
	ld de, $LOADER_START_ADDR
	ld bc, loader_exec_end - loader_exec_begin
	ldir
	ld hl, data_header_table
	ld a, ( menu_selected_addr )
	ld c, a	
	rlca
	add a, c ; mul a, 3
	ld b, 0
	ld c, a
	add hl, bc
	ld a, ( hl ) ; page number
	inc hl
	ld e, ( hl ) 
	inc hl
	ld d, ( hl ) ; address of data
	ex de, hl
	jp $LOADER_START_ADDR

include "ui/ui4_01.hasm"

include "ui/ui01.asm"

font_raw_addr:
	resource "res/godofwar.fnt"
	
title_addr:
	defb "BEST COLLECTION 01", 0
	
options_addr:
	defb "1.Nether Earth ", 0
	
	defb "2.Jack Nipper 2", 0	

	defb "3.Panama Joe  1", 0

	defb "4.Cyclone      ", 0
	
author_addr:
	defb "Created by Maxx in 2026",0

loading_addr:
	defb "Loading...", 0

loader_exec_begin:
	resource "res/embedded/loader2.bin"
	
data_header_table:
; format
; page0
; address0
; ...
; pageN
; addressN

loader_exec_end:
