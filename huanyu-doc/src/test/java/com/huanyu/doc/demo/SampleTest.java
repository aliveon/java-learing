package com.huanyu.doc.demo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.ImmutableMap;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.huanyu.doc.interview.array.StoreQRCode;

/**
 * @author yangtao
 */
public class SampleTest {

  private static final Logger logger = LoggerFactory.getLogger(SampleTest.class);

  private static final String SQL_LIKE_PARAM = "%%%s%%";
  private static final String MULTI_VARIABLE =
    "multi places variable to replace:%1$s|%2$s|%1$s|%1$s|%2$s|%1$s";

  private static final String QRCODE_FORMAT = "png";
  private static final int QRCODE_WIDTH = 600;
  private static final String QRCODE_CHARSET = "utf-8";
  private static final String DOT = ".";
  private static final Map<EncodeHintType, String> QRCODE_ENCODEHINTTYPE =
    ImmutableMap.<EncodeHintType, String>builder()//
      .put(EncodeHintType.CHARACTER_SET, QRCODE_CHARSET)//
      .build();

  @Test
  public void test() {
    Object[] o = new Object[3];
    o[0] = "1";
    o[1] = new Date();
    o[2] = BigDecimal.ONE;

    // logger.info(JSON.toJSONString(o));
    // logger.info(o.length);

    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    logger.info(format.format(new Date()));

    logger.info(String.format(SQL_LIKE_PARAM, "haha"));
    logger.info(String.format(MULTI_VARIABLE, "first", "second"));
  }

  @Test
  public void testa() {
    logger.info(qpcToStr(BigDecimal.valueOf(1.0000)));
  }

  public static String qpcToStr(BigDecimal qpc) {
    assert (qpc != null);
    String qpcStr = qpc.toString();
    if (qpcStr.indexOf('.') > 0) {
      int n = qpcStr.length() - 1;
      while (qpcStr.charAt(n) == '0')
        n--;
      if (qpcStr.charAt(n) == '.')
        n--;
      qpcStr = qpcStr.substring(0, n + 1);
    }
    return "1*" + qpcStr;
  }

  /**
   * 二维码生成测试
   *
   * @throws Exception
   */
  @Test
  public void generateQRCode() throws Exception {
    String text = "http://www.baidu.com";
    File file = generateQRCode(text, "qrcode");
    File file2 = generateQRCode(text, "/qrcode/store/testbn01/qrcode2");
  }

  private File generateQRCode(String content, String filePath) throws Exception {
    if (StringUtils.isBlank(content) || StringUtils.isBlank(filePath))
      throw new RuntimeException(StringUtils.isBlank(content) ? "待生成二维码的内容为空" : "待生成二维码文件名为空");

    File file = new File(StringUtils.join(filePath, DOT, QRCODE_FORMAT));
    createDir(file);

    BitMatrix bitMatrix = new MultiFormatWriter()
      .encode(content, BarcodeFormat.QR_CODE, QRCODE_WIDTH, QRCODE_WIDTH, QRCODE_ENCODEHINTTYPE);
    MatrixToImageWriter.writeToPath(bitMatrix, QRCODE_FORMAT, file.toPath());
    return file;
  }

  private static void createDir(File file) throws Exception {
    //判断目标文件所在的目录是否存在
    if (file.getParentFile() != null) {
      if (!file.getParentFile().exists()) {
        if (!file.getParentFile().getParentFile().exists()) {
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

  @Test
  public void zipFiles() throws Exception {
    String text = "http://www.baidu.com";
    File file = generateQRCode(text, "qrcode");
    File file2 = generateQRCode(text, "/qrcode/store/testbn01/qrcode2");
    File[] files = Arrays.asList(file, file2).toArray(new File[2]);
    zipFiles("qrcodes.zip", "", files);
    zipFiles("qrcodes2.zip", "/qrcode/zip/testbn01/", files);
  }

  private File zipFiles(String zipName, String zipFilePath, File[] files) throws Exception {
    byte[] buffer = new byte[1024];
    ZipOutputStream out = null;
    File zipFile = new File(StringUtils.join(zipFilePath, zipName));
    try {
      if (StringUtils.isNotBlank(zipFilePath))
        createDir(zipFile);

      out = new ZipOutputStream(new FileOutputStream(StringUtils.join(zipFilePath, zipName)));
      for (int i = 0; i < files.length; i++) {
        FileInputStream fis = new FileInputStream(files[i]);
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
      if (out != null)
        out.close();
    }
    logger.info(String.format("生成%s成功", zipFile));
    return zipFile;
  }

  private File batchGenerateQRCodesAndzip(Map<String, String> qrCodeContents, String zipName,
    String zipFilePath) throws Exception {
    File zipFile = new File(StringUtils.join(zipFilePath, zipName));
    if (qrCodeContents.isEmpty() == false) {
      List<File> files = new ArrayList<File>();
      for (String key : qrCodeContents.keySet())
        files.add(generateQRCode(qrCodeContents.get(key), key));
      zipFiles(zipName, zipFilePath, files.toArray(new File[files.size()]));
    }

    logger.info(String.format("生成%s成功", zipFile));
    return zipFile;
  }

  @Test
  public void MathCeil() {
    logger.info("50123,after ceil:{}", Math.ceil(50123 / 100.0));
    logger.info("300,after ceil:{}", Math.ceil(300 / 100.0));
    logger.info("300,after ceil:{}", Math.ceil(301 / 100.0));

    for (int i = 0; i <= Math.ceil(120 / 100); i++) {
      logger.info("loop:" + i);
    }
  }

  private List<String> left(List<String> source, QueryStoreQRCodeResponse response) {
    List<String> contains = new ArrayList<String>();
    for (StoreQRCode storeQRCode : response.getQrCodes()) {
      contains.add(storeQRCode.getStoreId());
    }

    if (contains.isEmpty() == false)
      source.removeAll(contains);
    return source;
  }

  @Test
  public void listRemove() {
    List<String> source = new ArrayList<String>();
    source.add("123");
    source.add("456");
    source.add("789");
    source.add("147");
    source.add("258");
    source.add("369");

    List<StoreQRCode> qrCodes = new ArrayList<StoreQRCode>();
    StoreQRCode code1 = new StoreQRCode();
    code1.setStoreId("123");
    qrCodes.add(code1);

    StoreQRCode code2 = new StoreQRCode();
    code2.setStoreId("147");
    qrCodes.add(code2);

    StoreQRCode code3 = new StoreQRCode();
    code3.setStoreId("456");
    qrCodes.add(code3);

    QueryStoreQRCodeResponse response = new QueryStoreQRCodeResponse(qrCodes);

    List<String> target = left(source, response);

    logger.info("left:{}", JSON.toJSONString(target, true));
  }

  private <T extends Enum<T>> T cvtEnumType(Class<T> enumType, String value) {
    try {
      T result = Enum.valueOf(enumType, value);
      return result;
    } catch (Exception e) {
      logger.error("", e);
      return null;
    }
  }

  @Test
  public void judgeUrlPatternExclude() {
    //    String regex = "^*/checkService/check.hd$|(\\?sensitive\\=true|false)$";
    String regex = "^*/checkService/check.hd$|(\\.*sensitive\\=true|false)$";
    Pattern pattern = Pattern.compile(regex);

    String requestUri = "http:/localhost:8080/huanyu-web/checkService/check.html";
    logger.info("{} -> {}", requestUri, pattern.matcher(requestUri).find());

    String requestUri2 = "http:/localhost:8080/huanyu-web/checkService/check.html?sensitive=true";
    logger.info("{} -> {}", requestUri2, pattern.matcher(requestUri2).find());

    String requestUri3 = "http:/localhost:8080/huanyu-web/checkService/check.html?abc=true";
    logger.info("{} -> {}", requestUri3, pattern.matcher(requestUri3).find());

    String requestUri4 = "http:/localhost:8080/huanyu-web/checkService/check.html?sensitive=abc";
    logger.info("{} -> {}", requestUri4, pattern.matcher(requestUri4).find());

  }

}
