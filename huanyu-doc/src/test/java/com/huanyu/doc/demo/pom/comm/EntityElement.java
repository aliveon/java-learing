package com.huanyu.doc.demo.pom.comm;

import java.math.BigDecimal;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

/**
 * @author yangtao
 */
@XStreamAlias("entity")
public class EntityElement {

  public static final String PRODUCT_TYPE = "product";
  public static final String BRAND_TYPE = "brand";
  public static final String CATEGORY_TYPE = "category";
  public static final String VENDOR_TYPE = "vendor";


  @XStreamAsAttribute
  private String uuid;
  @XStreamAsAttribute
  private String type;
  @XStreamAsAttribute
  private String code;
  @XStreamAsAttribute
  private String name;
  @XStreamAsAttribute
  private BigDecimal qpc;
  @XStreamAsAttribute
  private String specification;
  @XStreamAsAttribute
  private String manufactory;
  @XStreamAsAttribute
  private String qpcStr;
  @XStreamAsAttribute
  private String measureUnit;


  public String getUuid() {
    return uuid;
  }

  public void setUuid(String uuid) {
    this.uuid = uuid;
  }

  /**
   * 实体类型，取值：product
   * {@link PRODUCT_TYPE}，brand {@link BRAND_TYPE}，category {@link CATEGORY_TYPE}，vendor {@link VENDOR_TYPE}。
   *
   * @return
   */
  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  /**
   * 商品包装数量，实体类型为product时存在
   *
   * @return
   */
  public BigDecimal getQpc() {
    return qpc;
  }

  public void setQpc(BigDecimal qpc) {
    this.qpc = qpc;
  }

  /**
   * 商品规格，实体类型为product时存在
   *
   * @return
   */
  public String getSpecification() {
    return specification;
  }

  public void setSpecification(String specification) {
    this.specification = specification;
  }

  /**
   * 生产厂家，实体类型为product时存在
   *
   * @return
   */
  public String getManufactory() {
    return manufactory;
  }

  public void setManufactory(String manufactory) {
    this.manufactory = manufactory;
  }

  /**
   * 包装数量说明，实体类型为product时存在
   *
   * @return
   */
  public String getQpcStr() {
    return qpcStr;
  }

  public void setQpcStr(String qpcStr) {
    this.qpcStr = qpcStr;
  }

  /**
   * 计量单位，实体类型为product时存在
   *
   * @return
   */
  public String getMeasureUnit() {
    return measureUnit;
  }

  public void setMeasureUnit(String measureUnit) {
    this.measureUnit = measureUnit;
  }

}
