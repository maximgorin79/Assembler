package ru.zxspectrum.format;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PushbackInputStream;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

/**
 * z80.
 *
 * @author Maxim Gorin
 */

/*
 Offset  Length  Description
        ---------------------------
        0       1       A register
        1       1       F register
        2       2       BC register pair (LSB, i.e. C, first)
        4       2       HL register pair
        6       2       Program counter
        8       2       Stack pointer
        10      1       Interrupt register
        11      1       Refresh register (Bit 7 is not significant!)
        12      1       Bit 0  : Bit 7 of the R-register
                        Bit 1-3: Border colour
                        Bit 4  : 1=Basic SamRom switched in
                        Bit 5  : 1=Block of data is compressed
                        Bit 6-7: No meaning
        13      2       DE register pair
        15      2       BC' register pair
        17      2       DE' register pair
        19      2       HL' register pair
        21      1       A' register
        22      1       F' register
        23      2       IY register (Again LSB first)
        25      2       IX register
        27      1       Interrupt flipflop, 0=DI, otherwise EI
        28      1       IFF2 (not particularly important...)
        29      1       Bit 0-1: Interrupt mode (0, 1 or 2)
                        Bit 2  : 1=Issue 2 emulation
                        Bit 3  : 1=Double interrupt frequency
                        Bit 4-5: 1=High video synchronisation
                                 3=Low video synchronisation
                                 0,2=Normal
                        Bit 6-7: 0=Cursor/Protek/AGF joystick
                                 1=Kempston joystick
                                 2=Sinclair 2 Left joystick (or user
                                   defined, for version 3 .z80 files)
                                 3=Sinclair 2 Right joystick
 */
 // Version 1
public class Z80 {
  private final static byte [] EOS = new byte[]{(byte) 0xED, (byte) 0xED, 0x00};
  private int af;

  private int bc;

  private int hl;

  private int pc;

  private int sp;

  private int i;

  private int r;

  private int flag1;

  private int de;

  private int _bc;

  private int _de;

  private int _hl;

  private int _af;

  private int ix;

  private int iy;

  private int iff;

  private int iff2;

  private int flag2;

  private byte version = 1;

  public int getAf() {
    return af;
  }

  public int getBc() {
    return bc;
  }

  public int getHl() {
    return hl;
  }

  public int getPc() {
    return pc;
  }

  public int getSp() {
    return sp;
  }

  public int getI() {
    return i;
  }

  public int getR() {
    return r;
  }

  public int getFlag1() {
    return flag1;
  }

  public int getDe() {
    return de;
  }

  public int get_bc() {
    return _bc;
  }

  public int get_de() {
    return _de;
  }

  public int get_hl() {
    return _hl;
  }

  public int get_af() {
    return _af;
  }

  public int getIx() {
    return ix;
  }

  public int getIy() {
    return iy;
  }

  public int getIflag() {
    return iff;
  }

  public int getIff2() {
    return iff2;
  }

  public int getFlag2() {
    return flag2;
  }
  public byte getVersion() {
    return version;
  }

  public int getBorderColor() {
    return borderColor;
  }

  public byte[] getData() {
    return data;
  }

  private int borderColor;

  private int lastAyRegisterOut;

  private byte [] ayRegisters;

  private byte [] data;

  private final TreeMap<Integer, byte []> pagesMap = new TreeMap<>();

  public byte[] getAyRegisters() {
    return ayRegisters;
  }

  public void setAyRegisters(byte[] regs) {
    this.ayRegisters = regs;
  }

  public int getLastAyRegisterOut() {
    return lastAyRegisterOut;
  }

  public void setLastAyRegisterOut(int reg) {
    this.lastAyRegisterOut = reg;
  }

  private int readByte(final InputStream is) throws IOException {
    int value = is.read();
    if (value == -1) {
      throw new EOFException();
    }
    return value;
  }

  private int readWord(final InputStream is) throws IOException {
    return readByte(is) | (readByte(is) << 8);
  }

  private void writeWord(final OutputStream os, int val) throws IOException {
    os.write(val & 0xff);
    os.write((val >> 8) & 0xff);
  }

  private int readWordBigEndian(final InputStream is) throws IOException {
    return (readByte(is) << 8) | readByte(is);
  }

  private byte [] readBytes(final InputStream is, int len) throws  IOException {
    byte [] buf = new byte[len];
    int readBytes = is.read(buf);
    if (readBytes != len) {
      throw new EOFException();
    }
    return buf;
  }

  public void load(final InputStream is) throws IOException {
    pagesMap.clear();
    af = readWordBigEndian(is);
    bc = readWord(is);
    hl = readWord(is);
    pc = readWord(is);
    sp = readWord(is);
    i = readByte(is);
    r = readByte(is);
    flag1 = readByte(is);
    de = readWord(is);
    _bc = readWord(is);
    _de = readWord(is);
    _hl = readWord(is);
    _af = readWordBigEndian(is);
    iy = readWord(is);
    ix = readWord(is);
    iff = readByte(is);
    iff2 = readByte(is);
    flag2 = readByte(is);
    borderColor = ((flag1 >>> 1) & 0x07);
    r |= (flag1 << 7) & 0x80;
    if (pc == 0) {
      loadDataV23(is);
    } else {
      loadDataV1(is);
    }
    analysePrnBuffer();
  }

  private void analysePrnBuffer() {
    int sum = 0;
    for (int i = 0x1b00; i < 0x1c00; i++) {
      sum += data[i];
    }
    if (sum != 0) {
      System.out.println("PRN buffer is busy");
    }
  }

  private void loadDataV1(InputStream is) throws IOException {
   boolean compression = ((flag1 >>> 5) & 0x01) != 0 ? true : false;
   if (compression) {
     data = unpack1(is);
   }
   else {
     data = is.readAllBytes();
   }
   byte []buf = new byte[16384];
   ByteArrayInputStream baos = new ByteArrayInputStream(data);
   baos.read(buf);
   byte []compressed = compressZ80(buf);
   pagesMap.put(8, compressed);
    baos.read(buf);
    compressed = compressZ80(buf);
    pagesMap.put(4, compressed);
    baos.read(buf);
    compressed = compressZ80(buf);
    pagesMap.put(5, compressed);
  }

  private static byte [] compressZ80( byte []data) throws IOException {
    return compressZ80(new ByteArrayInputStream(data));
  }
  private static byte [] compressZ80(InputStream is) throws IOException {
    PushbackInputStream uncompressed = new PushbackInputStream(is, 2);
    ByteArrayOutputStream compressed = new ByteArrayOutputStream();
    int b;
    while ((b = uncompressed.read()) != -1) {
      int next = uncompressed.read();
      if (b == next) {
        int count = 2;
        while (((next = uncompressed.read()) == b) && count < 255) {
          count++;
        }
        if (next != -1) {
          uncompressed.unread(next);
        }
        if (b == 0xed || (count > 4)) {
          compressed.write(0xed);
          compressed.write(0xed);
          compressed.write(count);
          compressed.write(b);
        } else {
          for (int i = 0; i < count; i++) {
            compressed.write(b);
          }
        }
      } else {
        if (next != -1) {
          uncompressed.unread(next);
        }
        compressed.write(b);
      }
    }
    return compressed.toByteArray();
  }

  private byte [] unpack1(final InputStream is) throws IOException {
    ByteArrayOutputStream uncompressed = new ByteArrayOutputStream();
    PushbackInputStream pis = new PushbackInputStream(is, 4);
    byte [] buffer = new byte[4];
    byte [] endMarker = new byte []{0, (byte)0xed, (byte)0xed, 0};
    int readBytes;
    while ((readBytes = pis.read(buffer)) != -1) {
      if ((readBytes == 4) && Arrays.equals(buffer, endMarker)) {
          break;
      }
      pis.unread(buffer, 0, readBytes);
      int b = pis.read();
      if (b == 0xed) {
        int next = pis.read();
        if (next == 0xed) {
           int count = pis.read();
           int val = pis.read();
           for (int i = 0; i < count; i++) {
             uncompressed.write(val);
           }
        } else {
          uncompressed.write(b);
          uncompressed.write(next);
        }
      } else {
        uncompressed.write(b);
      }
    }
    return uncompressed.toByteArray();
  }

  private void loadDataV23(final InputStream is) throws IOException {
    int headerLen = readWord(is);
    if (headerLen == 23) {
      version = 2;
    } else if (headerLen == 54 || headerLen == 55) {
      version = 3;
    } else {
      version = -1;
    }
    pc = readWord(is);
    headerLen -= 2;
    final int hwMode = readByte(is);//not used
    headerLen--;
    final int samRamState = readByte(is);//not used
    headerLen--;
    final int ifcIpaged = readByte(is);//not used
    headerLen--;
    final int state = readByte(is);
    headerLen--;
    if ((state & 0b100) != 0) { // ay used
      System.out.println("Used AY-8910");
      lastAyRegisterOut = readByte(is);
      headerLen--;
      ayRegisters = new byte[16];
      is.read(ayRegisters);
      headerLen -= ayRegisters.length;
    }

    //skip remains
    for (int i = 0; i < headerLen; i++) {
      is.read();//skip
    }
    PushbackInputStream pis = new PushbackInputStream(is);
    ByteArrayOutputStream pageStream = new ByteArrayOutputStream();
    ByteArrayOutputStream unpacked = new ByteArrayOutputStream();
    int b;
    while((b = pis.read()) != -1) {
      pis.unread(b);
      int blockLen = readWord(pis);
      System.out.println("Loading page size=" + blockLen);
      int page = readByte(pis);
      System.out.println("Loading page=" + page);
      byte [] buf = readBytes(pis, blockLen);
      pageStream.write(buf);
      pagesMap.put(Integer.valueOf(page), pageStream.toByteArray());
      pageStream.reset();
    }
    for (Map.Entry<Integer, byte []> entry : pagesMap.entrySet()) {
      pis = new PushbackInputStream(new ByteArrayInputStream(entry.getValue()));
      int page = entry.getKey();
      byte [] blockData;
      int blockLen = entry.getValue().length;
      if (blockLen == 0xffff) {
         blockData = readBytes(pis, 16384);//no compression
      } else {
        blockData = unpack23(pis, blockLen);
      }
      System.out.println("Unpacked block for page=" + page + ", size=" + blockData.length);
      unpacked.write(blockData);
    }
    data = unpacked.toByteArray();
  }


  private byte [] unpack23(final InputStream is, int size) throws IOException {
    int b;
    ByteArrayOutputStream uncompressed = new ByteArrayOutputStream();
    while (size > 0) {
      b = readByte(is);
      size--;
      if (b == 0xED) {
        int next = readByte(is);
        size--;
        if (next == 0xED) {
          int count = readByte(is);
          size--;
          int value = readByte(is);
          size--;
          for (int i = 0; i < count; i++) {
            uncompressed.write(value);
          }
        } else {
            uncompressed.write(b);
            uncompressed.write(next);
          }
        } else {
          uncompressed.write(b);
        }
      }
    return uncompressed.toByteArray();
  }

  public void saveData(final OutputStream os) throws IOException {
    os.write(data);
  }

  @Override
  public String toString() {
    String s = String.format("AF=%04Xh,\nBC=%04X,\nHL=%04Xh,\nPC=%04Xh,\nSP=%04Xh,\nI=%02X,\nR=%02Xh,\n"
            + "DE=%04Xh,\nBC'=%04Xh,\nDE'=%04Xh,\nHL'=%04Xh,\nAF'=%04Xh\nIX=%04Xh,\nIY=%04Xh\n"
            + "data size=%04d\n"
            + "Border color = %02Xh,\nver=%d",
        af & 0xffff, bc & 0xffff, hl & 0xffff, pc & 0xffff, sp & 0xffff, i & 0xff, r & 0xff
        , de & 0xffff, _bc & 0xffff, _de & 0xffff, _hl & 0xffff, _af & 0xffff, ix & 0xffff, iy & 0xffff,
         data == null ? 0 : data.length, borderColor,
        version);
    return s;
  }

  public void export(final OutputStream os, String templateName) throws IOException {

    for (Map.Entry<Integer, byte []> entry : pagesMap.entrySet()) {
      int page = entry.getKey().intValue();
      {
        System.out.println("Export: page=" + page);
        os.write(page);
        byte[] buf = entry.getValue();
        writeWord(os, buf.length);
        System.out.println("Export: data len=" + String.format("%02x", buf.length));
        os.write(buf);
      }
    }
    List<Integer> argList = new LinkedList<>();
    int im = flag2 & 0x03;
    switch (im) {
      case 0:
              im = 0x46;
              System.out.println("im 0");
              break;
      case 1:
              im = 0x56;
              System.out.println("im 1");
              break;
      case 2:
              im = 0x5e;
              System.out.println("im 2");
              break;
    }
    if (iff == 0) {
      iff = 0xf3;
      System.out.println("di");
    } else {
      iff = 0xfb;
      System.out.println("ei");
    }
    int borderColor = (flag1 >>> 1) & 0x03;
    if (ayRegisters != null) {
      System.out.println("AY regs=" + Arrays.toString(ayRegisters));
    }
    System.out.println("Border color=" + borderColor);
    System.out.println("IY=" + iy);
    System.out.println("HL=" + hl);
    System.out.println("SP=" + sp);
    System.out.println("PC=" + pc);
    System.out.println("BC'=" + _bc);
    System.out.println("DE'=" + _de);
    System.out.println("HL'=" + _hl);
    System.out.println("AF'=" + _af);
    System.out.println("BC=" + bc);
    System.out.println("DE=" + de);
    System.out.println("ix=" + ix);
    System.out.println("i=" + i);
    System.out.println("r=" + r);
    System.out.println("af=" + af);
    argList.add(borderColor);
    argList.add(_hl);
    argList.add(_de);
    argList.add(_bc);
    argList.add(_af);
    argList.add(ix);
    argList.add(iy);
    argList.add(bc);
    argList.add(i);
    argList.add(hl);
    argList.add(r);
    argList.add(af);
    argList.add(de);
    argList.add(sp);
    argList.add(iff);
    argList.add(im);
    argList.add(pc);
    if (ayRegisters != null) {
      argList.add(lastAyRegisterOut & 0xff);
      argList.add(ayRegisters[lastAyRegisterOut] & 0xff);
      for (int i = 0; i < ayRegisters.length; i++) {
        if (i != lastAyRegisterOut) {
          argList.add(Integer.valueOf(ayRegisters[i] & 0xff));
        }
      }
    }
    if (templateName != null) {
      byte[] template = generateTemplate(templateName, argList);
      os.write(template);
    }
    os.flush();
  }

  private byte [] generateTemplate(final String templateName, final List<Integer> params) throws IOException {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    try (PushbackInputStream pis = new PushbackInputStream(new FileInputStream(templateName))) {
      int b;
      Iterator<Integer> iter = params.iterator();
      while ((b = pis.read()) != -1) {
        if (b == 0) {
              int value = 0;
              if (iter.hasNext()) {
                value = iter.next();
              }
              int next = pis.read();
              if (next == 0) {
                 writeWord(baos, value);
              } else {
                pis.unread(next);
                baos.write(value);
              }
        } else {
          baos.write(b);
        }
      }
      return baos.toByteArray();
    }
  }

  private void saveScreen() throws IOException {
    for (Entry<Integer, byte []> entry : pagesMap.entrySet()) {
      if (entry.getKey() == 8) {
        try (FileOutputStream fos = new FileOutputStream("shot.dat")){
            fos.write(unpack23(new ByteArrayInputStream(entry.getValue()), entry.getValue().length), 0, 6912);
        }
      }
    }
  }

  public static final void main(String ... args) throws IOException {
    if (args.length < 2) {
      System.out.println("Usage: Z80 <in.z80> <out.bin>");
      System.out.println("Generate template: Z80 <in.z80> <out.bin> -t <template name>");
      System.out.println("Make shot template: Z80 <in.z80> <out.bin> -s");
      return;
    }
    boolean saveShot = false;
    String templateName = null;
    for (int i = 0; i < args.length; i++) {
      String arg = args[i];
      if ("-t".equalsIgnoreCase(arg)) {
        if (++i < args.length) {
          templateName  = args[i];
          continue;
        } else {
          System.out.println("Template file required after key -t");
          return;
        }
      }
      if ("-s".equalsIgnoreCase(arg)) {
        saveShot = true;
      }
    }
    try (FileInputStream fis = new FileInputStream(args[0]); FileOutputStream fos = new FileOutputStream(args[1])) {
      Z80 format = new Z80();
      format.load(fis);
      System.out.println(format);
      format.export(fos, templateName);
      if (saveShot) {
        format.saveScreen();
      }
    }
  }
}
