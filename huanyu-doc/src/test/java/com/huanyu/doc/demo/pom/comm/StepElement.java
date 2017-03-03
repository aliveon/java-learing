package com.huanyu.doc.demo.pom.comm;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

/**
 * 级别条件
 *
 * @author yangtao
 */
public class StepElement extends XmlElement {

  public static final String QUANTITY_VALUETYPE = "quantity";
  public static final String AMOUNT_VALUETYPE = "amount";

  public static final String RANGE_OPERATOR = "range";
  public static final String EQUAL_OPERATOR = "equal";

  @XStreamAsAttribute
  private String valueType = QUANTITY_VALUETYPE;
  @XStreamAsAttribute
  private String operator = RANGE_OPERATOR;
  @XStreamAsAttribute
  private String priceType = "actualPrice";
  @XStreamImplicit(itemFieldName = "case")
  private List<CaseElement> casz;


  /**
   * 取值类型，取值：
   * "quantity" - 数量，默认；
   * "amount" - 金额
   *
   * @return
   */
  public String getValueType() {
    return valueType;
  }

  public void setValueType(String valueType) {
    this.valueType = valueType;
  }

  /**
   * 运算符，取值：
   * "range" - 区间（闭-开），默认；
   * "equal" - 等于
   *
   * @return
   */
  public String getOperator() {
    return operator;
  }

  public void setOperator(String operator) {
    this.operator = operator;
  }

  public String getPriceType() {
    return priceType;
  }

  public void setPriceType(String priceType) {
    this.priceType = priceType;
  }

  public List<CaseElement> getCasz() {
    return casz;
  }

  public void setCasz(List<CaseElement> casz) {
    this.casz = casz;
  }

}
