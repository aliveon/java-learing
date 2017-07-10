package com.huanyu.doc.utils;

import com.google.common.collect.ImmutableMap;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 二维码工具类
 *
 * @author yangtao
 */
public class QRCodeUtils {

  private static final Logger LOG = LoggerFactory.getLogger(QRCodeUtils.class);

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
    FileUtils.createDir(file);

    BitMatrix bitMatrix = new MultiFormatWriter()
      .encode(content, BarcodeFormat.QR_CODE, QRCODE_WIDTH, QRCODE_WIDTH, QRCODE_ENCODEHINTTYPE);
    MatrixToImageWriter.writeToPath(bitMatrix, QRCODE_FORMAT.substring(1), file.toPath());
    LOG.info("二维码内容:{},生成二维码文件地址:{}", content, file.getPath());
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
      fileDir.lastIndexOf(FileUtils.DOT) > fileDir.lastIndexOf(FileUtils.FILE_SEPARATOR))
      return;

    if (fileDir.length() - 1 != fileDir.lastIndexOf(FileUtils.FILE_SEPARATOR))
      fileDir = StringUtils.join(fileDir, File.separator);

    if (qrCodeContents.isEmpty() == false) {
      List<File> files = new ArrayList<File>();
      for (String key : qrCodeContents.keySet())
        files.add(generateQRCode(qrCodeContents.get(key), StringUtils.join(fileDir, key), StringUtils.EMPTY));
    }
  }

  /**
   * 批量生成二维码并压缩打包
   *
   * @param qrCodeContents 二维码内容键值对,键：用于命名二维码图片名称;值：用于生成二维码
   * @param zipFilePath    打包文件地址(包含文件路径和文件名称,不包含文件后缀名)
   * @return
   * @throws Exception
   */
  public static File batchGenerateQRCodesAndzip(Map<String, String> qrCodeContents,
                                                String zipFilePath) throws Exception {
    File zipFile = new File(StringUtils.join(zipFilePath, FileUtils.ZIP_SUFFIX));
    if (qrCodeContents.isEmpty() == false) {
      List<File> files = new ArrayList<File>();
      String dir = StringUtils.join(File.separator, "files", File.separator);
      if (zipFilePath.lastIndexOf(FileUtils.FILE_SEPARATOR) != -1)
        dir =
          StringUtils.join(zipFilePath.substring(0, zipFilePath.lastIndexOf(FileUtils.FILE_SEPARATOR)), dir);
      for (String key : qrCodeContents.keySet())
        files.add(generateQRCode(qrCodeContents.get(key), StringUtils.join(dir, key), StringUtils.EMPTY));
      FileUtils.zipFiles(zipFilePath, files.toArray(new File[files.size()]));
    }

    LOG.info(String.format("生成%s成功", zipFile));
    return zipFile;
  }

}
