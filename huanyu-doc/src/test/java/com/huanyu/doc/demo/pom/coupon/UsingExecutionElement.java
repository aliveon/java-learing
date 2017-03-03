package com.huanyu.doc.demo.pom.coupon;

import com.huanyu.doc.demo.pom.comm.XmlElement;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

/**
 * 使用约束
 *
 * @author yangtao
 */
public class UsingExecutionElement extends XmlElement {

  @XStreamAsAttribute
  private Integer count;
  @XStreamAsAttribute
  private String function;

  /**
   * 次数
   *
   * @return
   */
  public Integer getCount() {
    return count;
  }

  public void setCount(Integer count) {
    this.count = count;
  }

  public String getFunction() {
    return function;
  }

  public void setFunction(String function) {
    this.function = function;
  }

}
