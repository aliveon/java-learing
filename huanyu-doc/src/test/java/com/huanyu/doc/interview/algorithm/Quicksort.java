package com.huanyu.doc.interview.algorithm;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 快速排序
 * http://www.cnblogs.com/HuoAA/p/4338424.html
 *
 * @author yangtao
 */
public class Quicksort extends SortProxy {

  private final Logger logger = LoggerFactory.getLogger(this.getClass());

  @Override
  public String getSubscriptionName() {
    return "快速排序";
  }

  @Override
  public void sort(long[] array) {
    if (array == null || array.length == 0)
      return;

    quicksort(array, 0, array.length - 1);
  }

  private void quicksort(long[] array, int left, int right) {
    int start = left;
    int end = right;
    long pivot = array[left];

    while (end > start) {
      //从后往前比较
      while (end > start && array[end] >= pivot)  //如果没有比关键值小的，比较下一个，直到有比关键值小的交换位置，然后又从前往后比较
        end--;
      if (array[end] <= pivot) {
        long temp = array[end];
        array[end] = array[start];
        array[start] = temp;
      }
      debug("从后往前比较:{}", JSON.toJSONString(array, true));
      //从前往后比较
      while (end > start && array[start] <= pivot)//如果没有比关键值大的，比较下一个，直到有比关键值大的交换位置
        start++;
      if (array[start] >= pivot) {
        long temp = array[start];
        array[start] = array[end];
        array[end] = temp;
      }
      debug("从前往后比较:{}", JSON.toJSONString(array, true));
      //此时第一次循环比较结束，关键值的位置已经确定了。
      // 左边的值都比关键值小，右边的值都比关键值大，但是两边的顺序还有可能是不一样的，进行下面的递归调用
    }

    //递归
    if (start > left)
      quicksort(array, left, start - 1);//左边序列。第一个索引位置到关键值索引-1
    if (end < right)
      quicksort(array, end + 1, right);//右边序列。从关键值索引+1到最后一个
  }

  private void debug(String format, Object... obj) {
    if (logger.isDebugEnabled())
      logger.debug(format, obj);
  }

}
