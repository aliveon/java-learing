package com.huanyu.doc.interview.algorithm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 鸡尾酒排序（也叫定向冒泡排序）
 * {@link http://www.cnblogs.com/eniac12/p/5329396.html}
 * <p>
 * 鸡尾酒排序，也叫定向冒泡排序，是冒泡排序的一种改进。此算法与冒泡排序的不同处在于从低到高然后从高到低，
 * 而冒泡排序则仅从低到高去比较序列里的每个元素。他可以得到比冒泡排序稍微好一点的效能。
 *
 * @author yangtao
 */
public class CocktailSort extends SortProxy {

  private final Logger logger = LoggerFactory.getLogger(this.getClass());

  @Override
  public String getSubscriptionName() {
    return "鸡尾酒排序";
  }

  @Override
  public void sort(long[] array) {
    if (array == null || array.length == 0)
      return;

    // 初始化边界
    int left = 0;
    int right = array.length - 1;
    // 折半排序
    while (left < right) {
      // 前半轮,将最大元素放到后面
      for (int i = left; i < right; i++) {
        if (array[i] > array[i + 1]) {
          SortUtils.swap(array, i, i + 1);
        }
      }
      right--;

      // 后半轮,将最小元素放到前面
      for (int i = right; i > left; i--) {
        if (array[i - 1] > array[i]) {
          SortUtils.swap(array, i - 1, i);
        }
      }
      left++;
    }
  }

}
