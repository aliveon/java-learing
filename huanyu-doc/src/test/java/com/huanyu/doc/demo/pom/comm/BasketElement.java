package com.huanyu.doc.demo.pom.comm;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

/**
 * 购物篮条件
 *
 * @author yangtao
 */
public class BasketElement extends XmlElement {

  @XStreamAsAttribute
  private boolean overall;
  @XStreamImplicit(itemFieldName = "product")
  private List<ProductElement> products;
  @XStreamImplicit(itemFieldName = "element")
  private List<XmlElement> childrends;

  /**
   * 是否全场
   *
   * @return
   */
  public boolean isOverall() {
    return overall;
  }

  public void setOverall(boolean overall) {
    this.overall = overall;
  }

  public List<ProductElement> getProducts() {
    return products;
  }

  public void setProducts(List<ProductElement> products) {
    this.products = products;
  }

  @Override
  public List<XmlElement> getChildrends() {
    return childrends;
  }

  @Override
  public void setChildrends(List<XmlElement> childrends) {
    this.childrends = childrends;
  }

}
