package com.huanyu.doc.demo;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author yangtao
 */
@XmlRootElement
public class ActionResult {

  private boolean success;
  private String message;

  public boolean isSuccess() {
    return success;
  }

  public void setSuccess(boolean success) {
    this.success = success;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

}
