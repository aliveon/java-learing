package com.huanyu.doc.demo.pom.comm;

import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

/**
 * 规则提醒元素
 *
 * @author yangtao
 */
public class ReminderElement extends XmlElement {

  @XStreamAsAttribute
  private Integer index;
  @XStreamAsAttribute
  private String text;

  /**
   * 序号，提醒规则标识
   *
   * @return
   */
  public Integer getIndex() {
    return index;
  }

  public void setIndex(Integer index) {
    this.index = index;
  }

  /**
   * 提醒文本
   *
   * @return
   */
  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

}
