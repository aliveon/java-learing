package com.huanyu.doc.demo;

import java.io.File;
import java.io.InputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.CannedAccessControlList;
import com.aliyun.oss.model.PutObjectResult;
import com.aliyun.oss.model.SetBucketAclRequest;

/**
 * @author yangtao
 */
@Component
public class AliyunOSSClient implements InitializingBean {

  private final Logger logger = LoggerFactory.getLogger(this.getClass());
  private static OSSClient client;

  private static final String OSS_ADDRESS = "OSS_ADDRESS";
  private static final String OSS_KEY_ID = "OSS_KEY_ID";
  private static final String OSS_KEY_SECRET = "OSS_KEY_SECRET";
  public static final String OSS_BUCKET = "OSS_BUCKET";

  public void afterPropertiesSet() throws Exception {

    client = new OSSClient(OSS_ADDRESS, OSS_KEY_ID, OSS_KEY_SECRET);
    client.setBucketAcl(new SetBucketAclRequest(OSS_BUCKET, CannedAccessControlList.PublicRead));

    // 如果默认数据存储空间不存在则新建
    if (client.doesBucketExist(OSS_BUCKET) == false) {
      client.createBucket(OSS_BUCKET);
    }
  }


  public static OSSClient getOSSClient() {
    return new OSSClient(OSS_ADDRESS, OSS_KEY_ID, OSS_KEY_SECRET);
  }

  public PutObjectResult uploadFile(String key, InputStream input) throws Exception {
    try {
      validateKey(key);
      return client.putObject(OSS_BUCKET, key, input);

    } catch (OSSException e) {
      throw new Exception(e);
    }
  }

  public PutObjectResult uploadFile(String key, File file) throws Exception {
    try {
      validateKey(key);
      return client.putObject(OSS_BUCKET, key, file);

    } catch (OSSException e) {
      throw new Exception(e);
    }
  }

  private void validateKey(String key) throws Exception {
    if (key.getBytes().length == 0 || key.getBytes().length > 1023) {
      throw new Exception("key长度必须在1~1023之间");

    } else if (key.startsWith("/") || key.startsWith("\\")) {
      throw new Exception("key不能以\"/\"或者\"\\\"开头");

    }
  }

}
