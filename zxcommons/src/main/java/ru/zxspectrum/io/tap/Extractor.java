package ru.zxspectrum.io.tap;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * Extractor.
 *
 * @author Maxim Gorin
 */
public final class Extractor {
  private Extractor() {

  }
  public static void main(final String []args) throws IOException {
    if (args.length < 2) {
      System.out.println("Use first argument as a file name, the sound for a directory");
      return;
    }
    List<Block> blockList;
    final TapData data =  new TapData();
    int i = 0;
    try (FileInputStream fis = new FileInputStream(args[0])) {
      data.read(fis);
      blockList = data.getBlockList();
    }
    for (Block block : blockList) {
      if (block.getFlag() == Flag.Data) {
        if (block instanceof HeaderBlock) {
          HeaderBlock hb = (HeaderBlock) block;
          if (hb.getBytesParams() != null) {
            System.out.println("START ADDRESS: " + hb.getBytesParams().getStartAddress());
          }
        }
        FileOutputStream fos = new FileOutputStream(args[1] + File.separator + "b" + i++ + ".bin");
        block.write(fos);
        fos.close();
      }
    }
  }
}
