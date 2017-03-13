package com.huanyu.doc.interview.algorithm;

import com.huanyu.common.utils.RandomUtils;

/**
 * @author yangtao
 */
public class SortUtils {

  protected static final int range = 1000;
  protected static final int COUNT = 500;

  protected static long[] init(int count) {
    long[] array = new long[count];
    for (int i = 0; i < count; i++) {
      array[i] = RandomUtils.nextLong(-range, range * 2);
    }

    return array;
  }

  protected static boolean assertArray(long[] array, boolean asc) {
    if (array == null || array.length == 0)
      return false;

    int size = array.length;
    boolean assertion = false;
    for (int i = 1; i < size; i++) {
      assertion = (asc && array[i] < array[i - 1]) || (asc == false && array[i] > array[i - 1]);
      if (assertion)
        return false;
    }

    return true;
  }

  /**
   * 交换数值
   *
   * @param array       待处理数组
   * @param firstIndex  数据1位置
   * @param secondIndex 数据2位置
   */
  protected static void swap(long[] array, int firstIndex, int secondIndex) {
    if (array == null || array.length == 0)
      return;

    if (firstIndex >= array.length || secondIndex >= array.length)
      return;

    array[firstIndex] += array[secondIndex];
    array[secondIndex] = array[firstIndex] - array[secondIndex];
    array[firstIndex] -= array[secondIndex];

  }


}
