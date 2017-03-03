package com.huanyu.doc.demo.pom.comm;

import java.util.Date;

import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

/**
 * 日期范围条件
 *
 * @author yangtao
 */
public class DateRangeElement extends XmlElement {

  @XStreamAsAttribute
  private Date start;
  @XStreamAsAttribute
  private Date finish;

  /**
   * 开始日期。精确到日，表示从这一天的第一秒开始，闭区间。存储上时间部分始终为“00:00:00”。
   *
   * @return
   */
  public Date getStart() {
    return start;
  }

  public void setStart(Date start) {
    this.start = start;
  }

  /**
   * 结束日期。精确到日，促销到这一天的第一秒之前结束，开区间。存储上时间部分始终为“00:00:00”。
   *
   * @return
   */
  public Date getFinish() {
    return finish;
  }

  public void setFinish(Date finish) {
    this.finish = finish;
  }

}
