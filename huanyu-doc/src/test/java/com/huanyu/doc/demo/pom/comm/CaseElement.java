package com.huanyu.doc.demo.pom.comm;

import java.math.BigDecimal;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

/**
 * 分支
 *
 * @author yangtao
 */
@XStreamAlias("case")
public class CaseElement {

  @XStreamAsAttribute
  private BigDecimal value;
  private XmlElement element;

  /**
   * 取值下限，表示大于等于
   *
   * @return
   */
  public BigDecimal getValue() {
    return value;
  }

  public void setValue(BigDecimal value) {
    this.value = value;
  }

  public XmlElement getElement() {
    return element;
  }

  public void setElement(XmlElement element) {
    this.element = element;
  }

}
