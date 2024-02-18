Features:
1. Supports all z80 instructions including undocumented ones.

2. Supported commands:
.include, include  "<filename>" - include the source (example: include "gfx.asm")
.def, def, .define. define <identifier> <expr> - define number (example: define WIDTH 256)
.db, db, .defb, defb <expr0>, <expr1>...<exprN> - defines bytes (1 byte), string, character (example: db 1, 2, 3, "Hello world!")
.dw, dw, .defw, defw <word expr0>,<word expr1>...<word exprN> - defines bytes (1 byte), words (2 bytes)
, characters (example: defw 255, 65534, fffh)
.ddw, ddw, .dd, dd, .defdw, defdw  <expr0>, <expr1>...<exprN> - defines bytes (1 byte), words (2 bytes)
, dwords (4 bytes), characters (example: dd 0x123456h, 100000)
.end, end - force compiler to stop (example: end)
.saveWav, saveWav <text1>...<textN> - saves compiled data into wave format (for zxspectrum and microsha, example: saveWav "out.wav")
.saveTap, saveTap <text1>...<textN> - saves compiled data into <tap> format (for zxspectrum, example: savetap "out.tap")
.saveTzx, saveTzx <text1>...<textN> - saves compiled data into <tzx> format (for zxspectrum, example: saveTzx "out.tzx")
.saveRkm, saveRkm <text1>...<textN> - saves compiled data into <rkm> format (for microsha, example: saverkm "out.rkm")
label: .equ, equ  <address> - defines address for a label (example: lab1: equ 123)
defres, resource <path1>...<pathN> - inserts binary data from a file into compiled file (example: .resource "/home/user/data.txt")
img, image <path1>...<pathN> - inserts a image data from a file into the compiled file,  (example: img "icon.ong")
if it is nesessary, converts to monochrome format
echo, print, message <text1>...<text1> - prints messages in console (example: .echo "Hello world!\n")
println <text1>..<text2> - the same as previous, but it prints a new message with a new line (example: println "Hello world!")

3. Number formats:

0[0..7]  - octal number (C-style) (example: 0234)
[0..7][gG] - ocatl number (Old-style) (example: 123G)
0x[0..F] - hexadecimal (C-style) (example: 0xFF)
#[0..F]  - hexadecimal (example: #1c)
$[0..F]  - hexadecimal (ZX-style) (example: $0C)
[0..F][Hh]  - hexdecimal (Old-style) (example: 12h)
0b[0..1] - binary (Java-style) (example: 0B11110000)
[0..1][Bb]  - binary (Old-style) (example: 11101b)
[0..9] - decimal (example: 12345)

4. Variable:

$var - compiler evaluates this variable