package com.huanyu.doc.demo.pom.comm;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

/**
 * @author yangtao
 */
@XStreamAlias("condition")
public class ConditionElement {

  public static final String PRODUCT_FIELD = "product";
  public static final String BRAND_FIELD = "brand";
  public static final String CATEGORY_FIELD = "category";


  public static final String equals_operator = "equals";
  public static final String not_equals_operator = "not_equals";

  @XStreamAsAttribute
  private String field;
  @XStreamAsAttribute
  private String operator;
  private OperandElement operand;


  public String getField() {
    return field;
  }

  public void setField(String field) {
    this.field = field;
  }

  public String getOperator() {
    return operator;
  }

  public void setOperator(String operator) {
    this.operator = operator;
  }

  public OperandElement getOperand() {
    return operand;
  }

  public void setOperand(OperandElement operand) {
    this.operand = operand;
  }
  
}
