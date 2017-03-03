package com.huanyu.doc.interview.array;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 门店会员码
 *
 * @author yangtao
 */
@XmlRootElement
public class StoreQRCode {

  private String storeId;
  private String content;
  private String pictureUrl;

  /**
   * 门店Id
   *
   * @return
   */
  @XmlElement(name = "store_id")
  public String getStoreId() {
    return storeId;
  }

  public void setStoreId(String storeId) {
    this.storeId = storeId;
  }

  /**
   * 二维码内容
   *
   * @return
   */
  @XmlElement(name = "content")
  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  /**
   * 二维码图片链接
   *
   * @return
   */
  @XmlElement(name = "picture_url")
  public String getPictureUrl() {
    return pictureUrl;
  }

  public void setPictureUrl(String pictureUrl) {
    this.pictureUrl = pictureUrl;
  }

}
