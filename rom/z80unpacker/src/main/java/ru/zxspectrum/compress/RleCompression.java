package ru.zxspectrum.compress;

import java.io.ByteArrayOutputStream;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PushbackInputStream;

/**
 * RleCompression.
 *
 * @author Maxim Gorin
 */
public final class RleCompression {
    public final static int COMPRESSED = 0x80;

    public final static int NORMAL = 0x0;

    private RleCompression() {
    }

    public static void deflate(final InputStream is, final OutputStream os) throws IOException {
      PushbackInputStream pis = new PushbackInputStream(is);
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      int sample, lastSample = 0;
      while((sample = pis.read()) != -1) {
        if ((baos.size() == 0) || (lastSample != sample)) {
          baos.write(sample);
          lastSample = sample;
        } else {
            flushNormal(baos, baos.size() - 1, os);
            baos.write(lastSample);
            baos.write(sample);
            lastSample = sample;
            while((sample = pis.read()) != -1) {
                if (sample == lastSample) {
                  baos.write(sample);
                } else {
                  pis.unread(sample);
                  break;
                }
            }
            flushCompressed(baos, os);
        }
      }
      flushNormal(baos, baos.size(), os);
    }

    public static void inflate(final InputStream is, final OutputStream os) throws IOException {
      int sample;
      while((sample = is.read()) != -1) {
        final int type = sample & COMPRESSED;
        final int count = sample & 127;
        if (type == COMPRESSED) {
          sample = is.read();
          if (sample == -1) {
            throw new EOFException();
          }
          for (int i = 0; i < count; i++) {
            os.write(sample);
          }
        } else {
            for (int i = 0; i < count; i++) {
              sample = is.read();
              if (sample == -1) {
                throw new EOFException();
              }
              os.write(sample);
            }
        }
      }
    }

  private static void flushNormal(ByteArrayOutputStream baos, int size, OutputStream os) throws IOException {
    if (size > 0 && baos.size() >= size) {
      byte []data = baos.toByteArray();
      int i = 0;
      while(size > 0) {
        int count = size > 127 ? 127 : size;
        os.write(NORMAL | count);
        for (int j = 0; j < count; j++, i++) {
          os.write(data[i]);
        }
        size -= count;
      }
    }
    baos.reset();
  }

  private static void flushCompressed(ByteArrayOutputStream baos, OutputStream os) throws IOException {
      if (baos.size() > 1) {
        byte []data = baos.toByteArray();
        int sample = data[0];
        int size = data.length;
        while(size > 0) {
          int count = (size > 127 ? 127 : size);
          os.write(COMPRESSED | count);
          os.write(sample);
          size -= count;
        }
      }
      baos.reset();
  }
/*
  //Inflate test
  public static void main(String []args) throws IOException {
      if (args.length != 2) {
        System.out.println("No arguments");
        return;
      }
      try (FileInputStream fis = new FileInputStream(args[0]); FileOutputStream fos = new FileOutputStream(args[1])) {
        inflate(fis, fos);
      }
  }
  */


  //Deflate test
  public static void main(String []args) throws IOException {
    if (args.length != 2) {
      System.out.println("No arguments");
      return;
    }
    try (FileInputStream fis = new FileInputStream(args[0]); FileOutputStream fos = new FileOutputStream(args[1])) {
      deflate(fis, fos);
    }
  }
}
