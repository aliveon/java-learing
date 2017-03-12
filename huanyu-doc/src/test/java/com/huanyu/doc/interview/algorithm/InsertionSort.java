package com.huanyu.doc.interview.algorithm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 插入排序
 * <p>
 * 抓扑克牌模型，对于未排序数据(右手抓到的牌)，在已排序序列(左手已经排好序的手牌)中从后向前扫描，找到相应位置并插入。
 *
 * @author yangtao
 */
public class InsertionSort extends Sort {

  private final Logger logger = LoggerFactory.getLogger(this.getClass());

  @Override
  protected void sort(long[] array) {
    if (array == null || array.length == 0)
      return;

    int size = array.length, j;
    long flag;
    for (int i = 1; i < size; i++) {
      // 标记待插入数据
      flag = array[i];
      // 有序序列的终止下标,标识从此下标开始之前的序列都是有序的
      j = i - 1;
      // 比较并挪位目的是：找到安放该值的位置，即下标
      j = compareMove(array, flag, j);
      // 待插入数据放置的下标位置
      array[j + 1] = flag;
    }
  }

  private int compareMove(long[] array, long insertValude, int sequenceIndex) {
    // 对已有有序序列从后往前依次比较，并向后移位。
    while (sequenceIndex >= 0 && array[sequenceIndex] > insertValude) {
      // 依次向后移动一个位置,留出待插入数据的位置
      array[sequenceIndex + 1] = array[sequenceIndex];
      --sequenceIndex;
    }
    return sequenceIndex;
  }

}
