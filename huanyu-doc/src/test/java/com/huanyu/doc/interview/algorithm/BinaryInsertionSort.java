package com.huanyu.doc.interview.algorithm;

/**
 * 二分插入排序(对于插入排序的优化,优化点：定位插入位置使用折半查找提高查找效率)
 * <p>
 * 整体还是基于抓扑克牌模型,
 * 相对于插入排序,如果比较操作的代价比交换操作大的话,可以采用二分查找法来减少比较操作的数目,我们称为二分插入排序
 *
 * @author yangtao
 */
public class BinaryInsertionSort implements SortProxy {

  public void sort(long[] array) {
    if (array == null || array.length == 0)
      return;

    int size = array.length, left, middle, right;
    long flag;

    for (int i = 1; i < size; i++) {
      // 右手抓到一张扑克牌
      flag = array[i];

      // 左手牌左右边界进行初始化
      left = 0;
      right = i - 1;

      // 拿在左手上的牌总是排序好的,所以可以采用二分搜索法（折半查找）定位新牌的位置
      while (left <= right) {
        middle = (left + right) / 2;
        if (array[middle] > flag)
          right = middle - 1;
        else
          left = middle + 1;
      }

      // 将欲插入新牌位置右边的牌整体向右移动一个单位
      for (int j = i - 1; j >= left; j--) {
        array[j + 1] = array[j];
      }

      // 将抓到的牌插入手牌
      array[left] = flag;
    }
  }

}
