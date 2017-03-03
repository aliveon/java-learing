package com.huanyu.doc.demo.pom.comm;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

/**
 * @author yangtao
 */
@XStreamAlias("product")
public class ProductElement {

  public static final String RANGE_MODE = "range";


  @XStreamAsAttribute
  private String mode = "range";
  @XStreamImplicit(itemFieldName = "condition")
  private List<ConditionElement> conditions;

  public String getMode() {
    return mode;
  }

  public void setMode(String mode) {
    this.mode = mode;
  }

  public List<ConditionElement> getConditions() {
    return conditions;
  }

  public void setConditions(List<ConditionElement> conditions) {
    this.conditions = conditions;
  }

}
