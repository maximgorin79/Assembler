<html>
 <body>
  <h1>Assembler for Z80 CPU</h1>  	
  <h1>Features:</h1>
	<a href="#sup-cmd-id">Supported commands</a><br>
	<a href="#num-fmt-id">Number formats</a><br>
	<a href="#vars-id">Variables</a><br>
	<a href="#synt-inst-id">Synthetic instructions</a><br>
	<a href="#embd-vars-id">Embedded variables</a><br>

	<h3>1. Supports all z80 instructions including undocumented ones.</h3>
	<h3 id="sup-cmd-id">2. Supported commands:</h3>
	<b>.include</b>, <b>include</b>  <i>"filename"</i> - include the source (example: include "gfx.asm")<br>
	<b>.def</b>, <b>def</b>, <b>.define</b> <b>define </b> <i>identifier</i> or <i>expr</i> - define number (example: define WIDTH 256)<br>
	<b>.db</b>, <b>db</b>, <b>.defb</b>, <b>defb</b> <i>expr0, expr1...exprN</i> - defines bytes (1 byte), string, character (example: db 1, 2, 3, "Hello world!")<br>
	<b>.dw</b>, <b>dw</b>, <b>.defw</b>, <b>defw</b> <i>word expr0, word expr1...word exprN</i> - defines bytes (1 byte), words (2 bytes), characters (example: defw 255, 65534, fffh)<br>
	<b>.ddw</b>, <b>ddw</b>, <b>.dd</b>, <b>dd</b>, <b>.defdw</b>, <b>defdw</b>  <i>expr0, expr1...exprN</i> - defines bytes (1 byte), words (2 bytes)
	, <b>dwords</b> (4 bytes), characters (example: dd 0x123456h, 100000)<br>
	<b>.end</b>, <b>end</b> - force compiler to stop (example: end)<br>
	<b>.saveWav</b>, <b>saveWav</b> <i>"filename1"..."filenameN"</i> - saves compiled data into wave format (for zxspectrum and microsha, example: saveWav "out.wav")<br>
	<b>.saveTap</b>, <b>saveTap</b> <i>"filename1"..."filenameN"</i> - saves compiled data into TAP format (for zxspectrum, example: savetap "out.tap")<br>
	<b>.saveTzx</b>, <b>saveTzx</b> <i>"filename1"..."filenameN"</i> - saves compiled data into TZX format (for zxspectrum, example: saveTzx "out.tzx")<br>
	<b>.saveRkm</b>, <b>saveRkm</b> <i>"filename1"..."filenameN"</i> - saves compiled data into RKM format (for microsha, example: saverkm "out.rkm")<br>
	label: <b>.equ</b>, <b>equ</b>  <i>address</i> - defines address for a label (example: lab1: equ 123)<br>
	<b>defres</b>, <b>resource</b> <i>"path1"..."pathN"</i> - inserts binary data from a file into compiled file (example: .resource "/home/user/data.txt")<br>
	<b>img</b>, <b>image</b> <i>"path1"..."pathN"</i> - inserts a image data from a file into the compiled file,  (example: img "icon.ong")
	if it is nesessary, converts to monochrome format<br>
	<b>echo</b>, <b>print</b>, message <i>"text1"..."textN"</i> - prints messages in console (example: .echo "Hello world!\n")<br>
	<b>println</b> <i>"text1"..."textN"</i> - the same as previous, but it prints a new message with a new line (example: println "Hello world!")<br>
	<h3 id="num-fmt-id">3. Number formats:</h3>
	<b>0[0..7]</b>  - octal number (C-style) (example: 0234)<br>
	<b>[0..7][gG]</b> - octal number (Old-style) (example: 123G)<br>
	<b>0x[0..F]</b> - hexadecimal (C-style) (example: 0xFF)<br>
	<b>#[0..F]</b> - hexadecimal (example: #1c)<br>
	<b>$[0..F]</b> - hexadecimal (ZX-style) (example: $0C)<br>
	<b>[0..F][Hh]</b>  - hexdecimal (Old-style) (example: 12h)<br>
	<b>0b[0..1]</b> - binary (Java-style) (example: 0B11110000)<br>
	<b>[0..1][Bb]</b>  - binary (Old-style) (example: 11101b)<br>
	<b>[0..9]</b> - decimal (example: 12345)<br>
	<h3 id="vars-id">4. Variables:</h3>
	<b>$var</b> - compiler evaluates the variable<br>
	<h3 id="synt-inst-id">5. Synthetic instructions:</h3>
	<h4>LD instructions:</h4>
	<i>_LD DE,HL</i><br>
	<i>_LD DE,BC</i><br>
	<i>_LD HL,DE</i><br>
	<i>_LD HL,BC</i><br>
	<i>_LD BC,HL</i><br>
	<i>_LD BC,DE</i><br>
	<h4>MUL instructions:</h4>
	<i>_MUL 2</i><br>
	<i>_MUL A,2</i><br>
	<i>_MUL 4</i><br>
	<i>_MUL A,4</i><br>
	<i>_MUL 8</i><br>
	<i>_MUL A,8</i><br>
	<i>_DIV 2</i><br>
	<i>_DIV A,2</i><br>
	<i>_DIV 4</i><br>
	<i>_DIV A,4</i><br>
	<i>_DIV 8</i><br>
	<i>_DIV A,8</i><br>
	<h4>EX instructions:</h4>
	<i>_EX HL,DE</i><br>
	<i>_EX HL,BC</i><br>
	<i>_EX DE,BC</i><br>
	<h4>SHIFT instructions:</h4>
	<i>_RR [A][B][C][D][E][H][L],1</i><br>
	<i>_RR [A][B][C][D][E][H][L],2</i><br>
	<i>_RR [A][B][C][D][E][H][L],3</i><br>
	<i>_RR [A][B][C][D][E][H][L],4</i><br>
	<i>_RR [A][B][C][D][E][H][L],5</i><br>
	<i>_RR [A][B][C][D][E][H][L],6</i><br>
	<i>_RR [A][B][C][D][E][H][L],7</i><br>	
	<i>_RL [A][B][C][D][E][H][L],1</i><br>
	<i>_RL [A][B][C][D][E][H][L],2</i><br>
	<i>_RL [A][B][C][D][E][H][L],3</i><br>
	<i>_RL [A][B][C][D][E][H][L],4</i><br>
	<i>_RL [A][B][C][D][E][H][L],5</i><br>
	<i>_RL [A][B][C][D][E][H][L],6</i><br>
	<i>_RL [A][B][C][D][E][H][L],7</i><br>
	<h3 id="embd-vars-id">6. Embedded variables:</h3>
	<b>_SCREEN_WIDTH</b> - Speccy screen width in pixels<br>
	<b>_SCREEN_HEIGHT</b> - Speccy screen height in pixels<br>
	<b>_SCREEN_ADDR</b> - the address of first screen data byte<br>
	<b>_SCREEN_SIZE</b> - the size of screen data in bytes without attributes buffer<br>
	<b>_ATTR_ADDR</b> - the address of first screen attribute byte<br>
	<b>_ATTR_SIZE</b> - the size of attribute data in bytes<br>
	<b>_BORDER_PORT</b><br>
	<b>_BEEPER_PORT</b><br>
	<b>_KEYBOARD_PORT</b><br>
	<b>_TAPE_PORT</b><br>
	<b>_KEMPSTON_PORT</b><br>
	<b>_PRINTER_PORT</b><br>
	<b>_CONFIG_128K_PORT</b><br>
	<b>_BDI_PORT</b><br>
 </body>
</html>