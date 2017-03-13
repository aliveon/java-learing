package com.huanyu.doc.interview.algorithm;

//import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 冒泡排序
 * <p>
 * 它重复地走访过要排序的元素，一次比较相邻两个元素，如果他们的顺序错误就把他们调换过来，
 * 直到没有元素再需要交换，排序完成。这个算法的名字由来是因为越小(或越大)的元素会经由交换慢慢“浮”到数列的顶端。
 *
 * @author yangtao
 */
public class BubbleSort extends SortProxy {

  private final Logger logger = LoggerFactory.getLogger(this.getClass());

  public void sort(long[] array) {
    if (array == null || array.length == 0)
      return;

    int size = array.length;
    for (int i = 0; i < size; i++) {
      for (int j = i + 1; j < size; j++) {
        // 从小到大排序
        if (array[i] > array[j]) {
          SortUtils.swap(array, i, j);
        }
      }
    }
  }

}
