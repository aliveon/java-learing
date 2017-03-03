package com.huanyu.doc.demo;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.aliyun.oss.model.GetObjectRequest;
import com.aliyun.oss.model.PutObjectResult;
import com.huanyu.common.utils.GenerateUUIDFactory;
import com.huanyu.doc.demo.AliyunOSSClient;

/**
 * @author yangtao
 */
public class UploadFileUtil {

  private static Logger logger = LoggerFactory.getLogger(UploadFileUtil.class);

  private static final String UPLOAD_PATH = "/temp/upload/";
  private static final String DOWNLOAD_PATH = "/temp/download/";

  private static final String LOOP_UPLOAD_PATH = "/temp/upload_loop/";
  private static final String RESULT_URL = "RESULT_URL";

  @Test
  public void uploadFile() throws Exception {
    String fileName = "test.txt";
    logger.info(uploadFile(fileName, UPLOAD_PATH, DOWNLOAD_PATH));
  }

  private String uploadFile(String fileName, String path, String downloadPath) {
    String ossKey = GenerateUUIDFactory.getInstance().generateUUID() + "-" + fileName;
    PutObjectResult res = AliyunOSSClient.getOSSClient()
      .putObject(AliyunOSSClient.OSS_BUCKET, ossKey, new File(path + fileName));

    if (StringUtils.isNotBlank(downloadPath))
      AliyunOSSClient.getOSSClient()
        .getObject(new GetObjectRequest(AliyunOSSClient.OSS_BUCKET, ossKey),
          new File(downloadPath + fileName));

    String result = RESULT_URL + ossKey;
    return result;
  }

  @Test
  public void uploadFiles() throws Exception {
    File file = new File(LOOP_UPLOAD_PATH);
    File[] tempList = file.listFiles();

    logger.info("{}", JSON.toJSONString(tempList, true));

    long success = 0L;
    long fail = 0L;
    long total = 0L;
    List<String> fileNames = new ArrayList<String>();


    for (File f : tempList) {
      if (f.isFile()) {
        total++;
        try {
          logger.info(f.getName());
          logger.info(uploadFile(f.getName(), LOOP_UPLOAD_PATH, DOWNLOAD_PATH));
          success++;
        } catch (Exception e) {
          logger.error("", e);
          fileNames.add(f.getName());
          fail++;
        }
      }
    }

    logger.info("上传结果：目录下共 {} 个文件和文件夹,需要上传的文件有 {} 个, 上传成功 {} 个文件,上传失败 {} 个文件,上传失败的文件名:",
      new Object[]{tempList.length, total, success, fail,
        fileNames.isEmpty() ? "无" : JSON.toJSONString(fileNames)});

  }

  @Test
  public void test() {
    logger.info("length:{},lastIndexOf(\"/\"):{}", LOOP_UPLOAD_PATH.length(),
      LOOP_UPLOAD_PATH.lastIndexOf("/"));
    Assert.assertTrue(LOOP_UPLOAD_PATH.length() - 1 == LOOP_UPLOAD_PATH.lastIndexOf("/"));
  }

}
