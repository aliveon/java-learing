package com.huanyu.doc.demo.pom.comm;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

/**
 * @author yangtao
 */
@XStreamAlias("operand")
public class OperandElement {

  @XStreamImplicit(itemFieldName = "entity")
  private List<EntityElement> entitys;

  public List<EntityElement> getEntitys() {
    return entitys;
  }

  public void setEntitys(List<EntityElement> entitys) {
    this.entitys = entitys;
  }

}
