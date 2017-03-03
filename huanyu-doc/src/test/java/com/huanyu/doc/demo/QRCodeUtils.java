package com.huanyu.doc.demo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.ImmutableMap;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;

/**
 * 二维码工具类
 *
 * @author yangtao
 */
public class QRCodeUtils {

  private static final Logger logger = LoggerFactory.getLogger(QRCodeUtils.class);

  // 二维码图片格式
  public static final String QRCODE_FORMAT = ".png";
  // 二维码图片宽度
  private static final int QRCODE_WIDTH = 600;
  // 二维码字符格式
  public static final String QRCODE_CHARSET = "UTF-8";
  // 二维码hints
  private static final Map<EncodeHintType, String> QRCODE_ENCODEHINTTYPE =
    ImmutableMap.<EncodeHintType, String>builder()//
      .put(EncodeHintType.CHARACTER_SET, QRCODE_CHARSET)//
      .build();
  // zip 压缩包文件后缀
  public static final String ZIP_SUFFIX = ".zip";
  // 文件夹分隔符
  public static final String FILE_SEPARATOR = "/";
  // 文件名
  public static final String DOT = ".";

  /**
   * 生成二维码
   *
   * @param content  待生成二维码的内容
   * @param filePath 二维码图片的文件地址(包含文件路径和文件名称,不包含文件后缀名,默认以{@link QRCODE_FORMAT}为后缀名)
   * @return
   * @throws Exception
   */
  public static File generateQRCode(String content, String filePath) throws Exception {
    return generateQRCode(content, filePath, QRCODE_FORMAT);
  }

  /**
   * 生成二维码
   *
   * @param content  待生成二维码的内容
   * @param filePath 二维码图片的文件地址(包含文件路径和文件名称)
   * @param suffix   二维码图片后缀名,默认后缀名 {@link QRCODE_FORMAT}
   * @return
   * @throws Exception
   */
  public static File generateQRCode(String content, String filePath, String suffix)
    throws Exception {
    if (StringUtils.isBlank(content) || StringUtils.isBlank(filePath))
      throw new RuntimeException(StringUtils.isBlank(content) ? "待生成二维码的内容为空" : "待生成二维码文件地址为空");

    File file =
      new File(StringUtils.join(filePath, StringUtils.isNotBlank(suffix) ? suffix : QRCODE_FORMAT));
    // 创建文件夹
    createDir(file);

    BitMatrix bitMatrix = new MultiFormatWriter()
      .encode(content, BarcodeFormat.QR_CODE, QRCODE_WIDTH, QRCODE_WIDTH, QRCODE_ENCODEHINTTYPE);
    MatrixToImageWriter.writeToPath(bitMatrix, QRCODE_FORMAT.substring(1), file.toPath());
    logger.info("二维码内容:{},生成二维码文件地址:{}", content, file.getPath());
    return file;
  }

  /**
   * 在{@param fileDir}目录下批量生成{@param qrCodeContents}列表对应的二维码列表
   *
   * @param qrCodeContents 待生成二维码内容
   * @param fileDir        二维码生成存放目录
   * @throws Exception
   */
  public static void batchGenerateQRCodes(Map<String, String> qrCodeContents, String fileDir)
    throws Exception {
    if (StringUtils.isBlank(fileDir) ||
      // 非目录结构
      fileDir.lastIndexOf(DOT) > fileDir.lastIndexOf(FILE_SEPARATOR))
      return;

    if (fileDir.length() - 1 != fileDir.lastIndexOf(FILE_SEPARATOR))
      fileDir = StringUtils.join(fileDir, File.separator);

    if (qrCodeContents.isEmpty() == false) {
      List<File> files = new ArrayList<File>();
      for (String key : qrCodeContents.keySet())
        files.add(generateQRCode(qrCodeContents.get(key), StringUtils.join(fileDir, key), ""));
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
      createDir(file);
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
      logger.error("", e);
      throw e;
    } finally {
      if (fis != null)
        fis.close();
      if (out != null)
        out.close();
    }
    logger.info(String.format("zip打包成功,zip文件地址:%s", StringUtils.join(zipFilePath, ZIP_SUFFIX)));
  }

  /**
   * 批量生成二维码并压缩打包
   *
   * @param qrCodeContents 二维码内容价值对,键：用于命名二维码图片名称;值：用于生成二维码
   * @param zipFilePath    打包文件地址(包含文件路径和文件名称,不包含文件后缀名)
   * @return
   * @throws Exception
   */
  public static File batchGenerateQRCodesAndzip(Map<String, String> qrCodeContents,
    String zipFilePath) throws Exception {
    File zipFile = new File(StringUtils.join(zipFilePath, ZIP_SUFFIX));
    if (qrCodeContents.isEmpty() == false) {
      List<File> files = new ArrayList<File>();
      String dir = StringUtils.join(File.separator, "files", File.separator);
      if (zipFilePath.lastIndexOf(FILE_SEPARATOR) != -1)
        dir =
          StringUtils.join(zipFilePath.substring(0, zipFilePath.lastIndexOf(FILE_SEPARATOR)), dir);
      for (String key : qrCodeContents.keySet())
        files.add(generateQRCode(qrCodeContents.get(key), StringUtils.join(dir, key), ""));
      zipFiles(zipFilePath, files.toArray(new File[files.size()]));
    }

    logger.info(String.format("生成%s成功", zipFile));
    return zipFile;
  }

  /**
   * 创建目录
   *
   * @param file 文件
   * @throws Exception
   */
  private static void createDir(File file) throws Exception {
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
      logger.info("创建文件夹{}", new Object[]{file.getParentFile()});
      if (!file.getParentFile().mkdirs()) {
        logger.info("创建文件夹:{} 失败", new Object[]{file.getParentFile()});
        throw new RuntimeException("创建文件夹:" + file.getParentFile() + " 失败");
      }
    }
  }

}
