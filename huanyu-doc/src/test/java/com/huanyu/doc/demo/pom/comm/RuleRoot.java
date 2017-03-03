package com.huanyu.doc.demo.pom.comm;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

/**
 * pom规则,主(根)元素
 *
 * @author yangtao
 */
@XStreamAlias("pom")
public class RuleRoot extends XmlElement {

  @XStreamAsAttribute
  @XStreamAlias("class")
  private String clazz;
  @XStreamImplicit(itemFieldName = "reminder")
  private List<ReminderElement> reminder;


  public RuleRoot() {
    this.clazz = "com.huanyu.web.doc.PomDocument";
  }

  public List<ReminderElement> getReminder() {
    return reminder;
  }

  public void setReminder(List<ReminderElement> reminder) {
    this.reminder = reminder;
  }

}
