package ru.maximgorin.zxspectrum.cartridge;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

/**
 * SettingsLoader.
 *
 * @author Maxim Gorin
 */
public class SettingsLoader extends Properties {

    public Settings load (final File f) throws IOException {
      if (f == null) {
        throw new FileNotFoundException();
      }
      try (FileInputStream fis = new FileInputStream(f)) {
        super.load(fis);
      }
      Settings settings = new Settings();
      settings.setMenuPath(super.getProperty("menu_path"));
      settings.setOutputPath(super.getProperty("output_path", "output"));
      settings.setRomSize(Integer.parseInt(super.getProperty("rom_size", "32768")));
      settings.setPageSize(Integer.parseInt(super.getProperty("page_size", "8912")));
      settings.setPrefix(super.getProperty("prefix", "rom"));
      settings.setTemplatePath(super.getProperty("template_path"));
      List<String> imagePathsList = new LinkedList<>();
      for (int i = 0; i < 64; i++) {
        String imagePath = super.getProperty("image_path" + i);
        if (imagePath == null) {
          break;
        }
        imagePathsList.add(imagePath);
      }
      settings.setImagePaths(imagePathsList);
      return settings;
    }
}
