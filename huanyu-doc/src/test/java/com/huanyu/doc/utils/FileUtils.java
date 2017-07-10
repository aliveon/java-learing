package com.huanyu.doc.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *  文件操作工具类
 *
 * @author yangtao
 */
public class FileUtils {
  private static final Logger LOG = LoggerFactory.getLogger(FileUtils.class);

  // zip 压缩包文件后缀
  public static final String ZIP_SUFFIX = ".zip";
  // 文件夹分隔符
  public static final String FILE_SEPARATOR = "/";
  // 文件名
  public static final String DOT = ".";

  /**
   * 创建目录
   *
   * @param file 文件
   * @throws Exception
   */
  public static void createDir(File file) throws Exception {
    if (file == null)
      return;

    //判断目标文件所在的目录是否存在
    if (file.getParentFile() != null && !file.getParentFile().exists()) {
      // 父目录的父目录文件夹是否存在
      if (file.getParentFile().getParentFile() != null &&
        !file.getParentFile().getParentFile().exists()) {
        // 迭代创建父目录文件夹
        createDir(file.getParentFile());
      }
      LOG.info("创建文件夹{}", new Object[]{file.getParentFile()});
      if (!file.getParentFile().mkdirs()) {
        LOG.info("创建文件夹:{} 失败", new Object[]{file.getParentFile()});
        throw new RuntimeException("创建文件夹:" + file.getParentFile() + " 失败");
      }
    }
  }


  /**
   * 打包某个目录下的所有文件
   *
   * @param zipFilePath 打包下载路径
   * @param fileDir     待打包文件目录(文件夹)
   * @throws Exception
   */
  public static void zipAllFilesInDir(String zipFilePath, String fileDir) throws Exception {
    if (StringUtils.isBlank(zipFilePath) || StringUtils.isBlank(fileDir) ||
      // 非目录结构
      fileDir.lastIndexOf(DOT) > fileDir.lastIndexOf(FILE_SEPARATOR))
      return;

    if (fileDir.length() - 1 != fileDir.lastIndexOf(FILE_SEPARATOR))
      fileDir = StringUtils.join(fileDir, File.separator);

    File file = new File(fileDir);
    File[] files = file.listFiles();
    zipFiles(zipFilePath, files);
  }

  /**
   * 打包文件
   *
   * @param zipFilePath 打包文件地址(包含文件路径和文件名称,不包含文件后缀名)
   * @param files       待打包文件(文件地址：包含文件路径、文件名称和文件后缀)
   * @throws Exception
   */
  public static void zipFiles(String zipFilePath, File[] files) throws Exception {
    byte[] buffer = new byte[1024];
    ZipOutputStream out = null;
    FileInputStream fis = null;
    try {
      File file = new File(StringUtils.join(zipFilePath, ZIP_SUFFIX));
      // 创建文件夹
      FileUtils.createDir(file);
      out = new ZipOutputStream(new FileOutputStream(StringUtils.join(zipFilePath, ZIP_SUFFIX)));
      for (int i = 0; i < files.length; i++) {
        if (files[i].isFile() == false)
          continue;
        fis = new FileInputStream(files[i]);
        out.putNextEntry(new ZipEntry(files[i].getName()));
        int len;
        while ((len = fis.read(buffer)) > 0) {
          out.write(buffer, 0, len);
        }
        out.closeEntry();
        fis.close();
      }
    } catch (Exception e) {
      LOG.error("", e);
      throw e;
    } finally {
      if (fis != null)
        fis.close();
      if (out != null)
        out.close();
    }
    LOG.info(String.format("zip打包成功,zip文件地址:%s", StringUtils.join(zipFilePath, ZIP_SUFFIX)));
  }


}
