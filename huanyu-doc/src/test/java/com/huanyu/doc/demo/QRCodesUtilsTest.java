package com.huanyu.doc.demo;

import java.io.File;
import java.util.Arrays;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.ImmutableMap;

/**
 * 二维码工具类测试
 *
 * @author yangtao
 */
public class QRCodesUtilsTest {
  private static final Logger logger = LoggerFactory.getLogger(QRCodesUtilsTest.class);

  @Test
  public void generateQRCode() throws Exception {
    String text =
      "https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxfdda9bc3e0f52b04&redirect_uri" +
        "=http%3A%2F%2Fhuanyu.com%2Fweixinmember%2Fview%2Ftestbn01%2Fpay&response_type=code&scope" +
        "=snsapi_base&state=dcbe72cc29914f8cadda1fe22805f921#wechat_redirect";
    QRCodeUtils.generateQRCode(text, "qrcode");
    QRCodeUtils.generateQRCode(text, "/qrcode/store/testbn01/qrcode2", ".jpg");
  }

  @Test
  public void zipFiles() throws Exception {
    String text = "http://139.129.92.1/weixinmember/test/testbn01/jsapi";
    File file = QRCodeUtils.generateQRCode(text, "qrcode3");
    File file2 = QRCodeUtils.generateQRCode(text, "/qrcode/store/testbn01/qrcode4");
    File[] files = Arrays.asList(file, file2).toArray(new File[2]);
    QRCodeUtils.zipFiles("qrcodes.zip", files);
    QRCodeUtils.zipFiles("/qrcode/zip/testbn01/qrcodes2.zip", files);
  }

  @Test
  public void batchGenerateQRCodesAndzip() throws Exception {
    String text = "http://139.129.92.1/weixinmember/test/testbn01/jsapi";
    Map<String, String> contents = ImmutableMap.<String, String>builder()//
      .put("qrcode5", text)//
      .put("qrcode6", text)//
      .build();

    QRCodeUtils.batchGenerateQRCodesAndzip(contents, "/qrcode/zip/testbn01/qrcodes");
  }

  @Test
  public void substepHandleBatchGenerateQRCodesAndzip() throws Exception {
    String text =
      "https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxfdda9bc3e0f52b04&redirect_uri" +
        "=http%3A%2F%2Fhuanyu.com%2Fweixinmember%2Fview%2Ftestbn01%2Fpay&response_type=code&scope" +
        "=snsapi_base&state=dcbe72cc29914f8cadda1fe22805f921#wechat_redirect";
    Map<String, String> contents = ImmutableMap.<String, String>builder()//
      .put("qrcode7", text)//
      .put("qrcode8", text)//
      .build();

    String dir = "/qrcode/zip/testbn01/";
    QRCodeUtils.batchGenerateQRCodes(contents, dir);
    QRCodeUtils.zipAllFilesInDir(StringUtils.join(dir, "qrcodes"), dir);
  }

}
