package ru.maximgorin.zxspectrum.cartridge;

import java.util.List;

/**
 * Settings.
 *
 * @author Maxim Gorin
 */
public class Settings {
  private String menuPath;

  private String outputPath;

  private List<String> imagePaths;

  private int romSize;

  private int pageSize;

  private String prefix;

  private String templatePath;

  public int getPageSize() {
    return pageSize;
  }

  public void setPageSize(int pageSize) {
    this.pageSize = pageSize;
  }

  public String getMenuPath() {
    return menuPath;
  }

  public void setMenuPath(String menuPath) {
    this.menuPath = menuPath;
  }

  public String getOutputPath() {
    return outputPath;
  }

  public void setOutputPath(String outputPath) {
    this.outputPath = outputPath;
  }

  public List<String> getImagePaths() {
    return imagePaths;
  }

  public void setImagePaths(List<String> imagePaths) {
    this.imagePaths = imagePaths;
  }

  public int getRomSize() {
    return romSize;
  }

  public void setRomSize(int romSize) {
    this.romSize = romSize;
  }

  public String getPrefix() {
    return prefix;
  }

  public void setPrefix(String prefix) {
    this.prefix = prefix;
  }

  public String getTemplatePath() {
    return templatePath;
  }

  public void setTemplatePath(String templatePath) {
    this.templatePath = templatePath;
  }
}
