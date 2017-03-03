package com.huanyu.doc.demo.pom.comm;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * @author yangtao
 */
@XStreamAlias("excluded")
public class ExcludedElement {


  private EntityElement entity;

  public EntityElement getEntity() {
    return entity;
  }

  public void setEntity(EntityElement entity) {
    this.entity = entity;
  }

}
