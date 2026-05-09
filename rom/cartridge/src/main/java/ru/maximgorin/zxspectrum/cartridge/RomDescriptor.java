package ru.maximgorin.zxspectrum.cartridge;

import java.io.IOException;
import java.io.OutputStream;

/**
 * RomDescriptor.
 *
 * @author Maxim Gorin
 */
public class RomDescriptor {
  private int page;

  private int address;

  public RomDescriptor(int page, int address) {
    this.page = page;
    this.address = address;
  }

  public int getPage() {
    return page;
  }

  public void setPage(int page) {
    this.page = page;
  }

  public int getAddress() {
    return address;
  }

  public void setAddress(int address) {
    this.address = address;
  }

  public void store(OutputStream os) throws IOException {
    os.write(page);
    os.write(address & 0xff);
    os.write((address >>> 8) & 0xff);
  }
}
