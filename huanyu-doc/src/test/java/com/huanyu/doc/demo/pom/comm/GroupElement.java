package com.huanyu.doc.demo.pom.comm;

import java.math.BigDecimal;
import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

/**
 * @author yangtao
 */
@XStreamAlias("group")
public class GroupElement {

  @XStreamAsAttribute
  private BigDecimal quantity;
  @XStreamImplicit(itemFieldName = "entity")
  private List<EntityElement> entity;


  /**
   * 组数量
   *
   * @return
   */
  public BigDecimal getQuantity() {
    return quantity;
  }

  public void setQuantity(BigDecimal quantity) {
    this.quantity = quantity;
  }

  public List<EntityElement> getEntity() {
    return entity;
  }

  public void setEntity(List<EntityElement> entity) {
    this.entity = entity;
  }

}
