package com.huanyu.doc.demo.pom.comm;

import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

/**
 * 可选执行条件
 *
 * @author yangtao
 */
public class ExecutionOptionElement extends XmlElement {

  @XStreamAsAttribute
  private Integer evaluation = 70;

  /**
   * 价值评估。取值为0-100之间的整数，默认为70。数值越大表示价值越高。
   *
   * @return
   */
  public Integer getEvaluation() {
    return evaluation;
  }

  public void setEvaluation(Integer evaluation) {
    this.evaluation = evaluation;
  }

}
