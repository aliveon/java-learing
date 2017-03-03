package com.huanyu.doc.demo.pom.comm;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

/**
 * xml元素节点定义
 *
 * @author yangtao
 */
public abstract class XmlElement {

  @XStreamAsAttribute
  private String id;
  @XStreamAsAttribute
  private String name;
  @XStreamImplicit(itemFieldName = "element")
  private List<XmlElement> childrends;


  public XmlElement() {
  }

  /**
   * id属性
   *
   * @return
   */
  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  /**
   * 名称属性
   *
   * @return
   */
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  /**
   * 子元素集合
   *
   * @return
   */
  public List<XmlElement> getChildrends() {
    return childrends;
  }

  public void setChildrends(List<XmlElement> childrends) {
    this.childrends = childrends;
  }

}
