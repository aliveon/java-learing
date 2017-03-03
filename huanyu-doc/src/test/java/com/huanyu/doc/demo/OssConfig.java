package com.huanyu.doc.demo;

/**
 * @author lsz
 */
public class OssConfig {

  public static final String NAME = "OSS";

  private String accessAddress;
  private String accessKeyId;
  private String accessKeySecret;
  private String bucket;
  private String namespace;

  public OssConfig() {
    super();
  }

  public String getAccessAddress() {
    return accessAddress;
  }

  public void setAccessAddress(String accessAddress) {
    this.accessAddress = accessAddress;
  }

  public String getAccessKeyId() {
    return accessKeyId;
  }

  public void setAccessKeyId(String accessKeyId) {
    this.accessKeyId = accessKeyId;
  }

  public String getAccessKeySecret() {
    return accessKeySecret;
  }

  public void setAccessKeySecret(String accessKeySecret) {
    this.accessKeySecret = accessKeySecret;
  }

  public String getBucket() {
    return bucket;
  }

  public void setBucket(String bucket) {
    this.bucket = bucket;
  }

  public String getNamespace() {
    return namespace;
  }

  public void setNamespace(String namespace) {
    this.namespace = namespace;
  }

}
