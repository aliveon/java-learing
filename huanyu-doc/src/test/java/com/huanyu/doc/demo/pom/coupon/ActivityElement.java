package com.huanyu.doc.demo.pom.coupon;

import java.math.BigDecimal;

import com.huanyu.doc.demo.pom.comm.XmlElement;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

/**
 * 券活动元素
 *
 * @author yangtao
 */
public class ActivityElement extends XmlElement {

  @XStreamAsAttribute
  private boolean isMgr;
  @XStreamAsAttribute
  private String logo;
  @XStreamAsAttribute
  private String no;
  @XStreamAsAttribute
  private BigDecimal parValue;
  @XStreamAsAttribute
  private String remark;
  @XStreamAsAttribute
  private String restriction;
  @XStreamAsAttribute
  private String form = "amount";
  @XStreamAsAttribute
  private String sort = "voucher";
  @XStreamAsAttribute
  private String uuid;

  /**
   * @return
   */
  public boolean isMgr() {
    return isMgr;
  }

  public void setMgr(boolean mgr) {
    isMgr = mgr;
  }

  /**
   * 活动图标
   *
   * @return
   */
  public String getLogo() {
    return logo;
  }

  public void setLogo(String logo) {
    this.logo = logo;
  }

  /**
   * 活动号
   *
   * @return
   */
  public String getNo() {
    return no;
  }

  public void setNo(String no) {
    this.no = no;
  }

  /**
   * 券面额
   *
   * @return
   */
  public BigDecimal getParValue() {
    return parValue;
  }

  public void setParValue(BigDecimal parValue) {
    this.parValue = parValue;
  }

  /**
   * 券说明
   *
   * @return
   */
  public String getRemark() {
    return remark;
  }

  public void setRemark(String remark) {
    this.remark = remark;
  }

  /**
   * 限定
   *
   * @return
   */
  public String getRestriction() {
    return restriction;
  }

  public void setRestriction(String restriction) {
    this.restriction = restriction;
  }

  public String getForm() {
    return form;
  }

  public void setForm(String form) {
    this.form = form;
  }

  public String getSort() {
    return sort;
  }

  public void setSort(String sort) {
    this.sort = sort;
  }

  public String getUuid() {
    return uuid;
  }

  public void setUuid(String uuid) {
    this.uuid = uuid;
  }

}
