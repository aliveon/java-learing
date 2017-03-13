package com.huanyu.doc.interview.algorithm;

/**
 * @author yangtao
 */
public abstract class SortProxy {

  /**
   * 排序名称描述
   *
   * @return
   */
  public abstract String getSubscriptionName();

  /**
   * 对数组进行排序
   *
   * @param array 待排序数组
   */
  public abstract void sort(long[] array);

}
