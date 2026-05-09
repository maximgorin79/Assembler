package ru.maximgorin.zxspectrum.cartridge;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import ru.zxspectrum.format.Z80;

/**
 * Cartridge.
 *
 * @author Maxim Gorin
 */
public class RomMaker {

  public static void main(String ... args) throws IOException {
    String settingsPath = "settings.props";
    if (args.length != 0) {
      if (args.length == 1 && "-h".equals(args[0])) {
        System.out.println("Usage: Catridge -h shows help\n-s <path to settings file>");
        return;
      }
      if (args.length == 2 && "-s".equals(args[0])) {
        settingsPath = args[1];
      }
    }
    start(settingsPath);
  }

  private static void start(final String settingsPath) throws IOException {
    final SettingsLoader loader = new SettingsLoader();
    final Settings settings = loader.load(new File(settingsPath));
    final File tmpFile = File.createTempFile("rom", "bin");
    try (FileOutputStream fos = new FileOutputStream(tmpFile)) {
      final byte []menu = Files.readAllBytes(Path.of(settings.getMenuPath()));
      fos.write(menu);
      int address = menu.length; // descriptors
      final List<byte []> romList = loadRoms(settings.getImagePaths(), settings.getTemplatePath());
      address += 3 * romList.size(); // header
      final List<RomDescriptor> rdList = createRomDescriptors(romList, address, settings.getPageSize());
      for (RomDescriptor rd : rdList) {
        System.out.println("Save descriptor [page=" + rd.getPage() + ", address=" + rd.getAddress() + "]");
        rd.store(fos);
      }
      for (byte []data : romList) {
        System.out.println("Save rom, size=" + data.length);
        fos.write(data);
      }
    }
    int index = 0;
    byte []buf = new byte[settings.getRomSize()];
    int fullSize = 0;
    final File outputDir = new File(settings.getOutputPath());
    if (!outputDir.exists()) {
      outputDir.mkdirs();
    } else {
      for (File file : outputDir.listFiles()) {
        file.delete();
      }
    }
    try (FileInputStream fis = new FileInputStream(tmpFile)) {
      int readBytes;
      while((readBytes = fis.read(buf)) != -1) {
        final File f = new File(outputDir, settings
            .getPrefix() + index++ + ".bin");
        System.out.println("Save data: " + readBytes);
        try (FileOutputStream fos = new FileOutputStream(f)) {
          fos.write(buf);
        }
        Arrays.fill(buf, (byte)0xff);
        fullSize += readBytes;
      }
    }
    int lastPage = (fullSize / settings.getPageSize());
    if (lastPage >= 15) {
      System.out.println("No enough space on a cartridge. Greater 14 pages, must be not greater 15!!!");
    } else {
      System.out.println("Last page is " + lastPage + ", free size = " + (settings.getPageSize() -
          (fullSize % settings.getPageSize())));
    }
  }

  private static List<RomDescriptor> createRomDescriptors(List<byte[]> romList, int address,
      int pageSize) {
    final List<RomDescriptor> resultList = new LinkedList<>();
    for (byte[] buf : romList) {
      int page = address / pageSize;
      int offset = address % pageSize;
      resultList.add(new RomDescriptor(page, offset));
      address += buf.length;
    }
    return resultList;
  }

  private static List<byte[]> loadRoms(final List<String> imagePaths, String templateName) throws IOException {
    int index = 0;
    final List<byte []> resultList = new LinkedList<>();
    for (String imagePath : imagePaths) {
      final File convertedFile = File.createTempFile("image" + index++, "z80");
      Z80.main(imagePath, convertedFile.getAbsolutePath(), "-t", templateName);
      final byte []buf = Files.readAllBytes(convertedFile.toPath());
      resultList.add(buf);
    }
    return resultList;
  }
}
