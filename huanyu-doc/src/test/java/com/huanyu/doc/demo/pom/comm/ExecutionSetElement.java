package com.huanyu.doc.demo.pom.comm;

import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

/**
 * 执行条件集合
 *
 * @author yangtao
 */
public class ExecutionSetElement extends XmlElement {

  /**
   * 自动模式
   */
  public static final String AUTO_MODE = "auto";
  /**
   * 手动模式
   */
  public static final String MANUAL_MODE = "manual";

  @XStreamAsAttribute
  private String inputMode;

  /**
   * 输入模式。取值：
   * auto - 自动
   * manual - 手动
   *
   * @return
   */
  public String getInputMode() {
    return inputMode;
  }

  public void setInputMode(String inputMode) {
    this.inputMode = inputMode;
  }

}
