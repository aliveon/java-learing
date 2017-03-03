package com.huanyu.doc.demo.pom.comm;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

/**
 * 组合商品条件
 *
 * @author yangtao
 */
public class MultiProductElement extends XmlElement {

  /**
   * "MultiProduct" - 组合商品
   */
  public static final String FUNCTION_DEFAULT = "MultiProduct";
  /**
   * "MultiProduct#And" - 组合商品（并且）
   */
  public static final String FUNCTION_AND = "MultiProduct#And";
  /**
   * "MultiProduct#Or" - 组合商品（或者）
   */
  public static final String FUNCTION_OR = "MultiProduct#Or";


  @XStreamAsAttribute
  private String function;
  @XStreamImplicit(itemFieldName = "group")
  private List<GroupElement> group;
  private ExcludedElement excluded;

  /**
   * 功能。取值：
   * "MultiProduct" - 组合商品, {@link FUNCTION_DEFAULT}
   * "MultiProduct#And" - 组合商品（并且）, {@link FUNCTION_AND}
   * "MultiProduct#Or" - 组合商品（或者）, {@link FUNCTION_OR} .
   *
   * @return
   */
  public String getFunction() {
    return function;
  }

  public void setFunction(String function) {
    this.function = function;
  }

  public List<GroupElement> getGroup() {
    return group;
  }

  public void setGroup(List<GroupElement> group) {
    this.group = group;
  }

  public ExcludedElement getExcluded() {
    return excluded;
  }

  public void setExcluded(ExcludedElement excluded) {
    this.excluded = excluded;
  }

}
