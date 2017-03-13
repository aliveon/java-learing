package com.huanyu.doc.interview.algorithm;

/**
 * 选择排序
 * <p>
 * 首先在未排序序列中找到最小（大）元素，存放到排序序列的起始位置；
 * 然后，再从剩余未排序元素中继续寻找最小（大）元素，放到已排序序列的末尾。
 * 以此类推，直到所有元素均排序完毕
 *
 * @author yangtao
 */
public class SelectionSort extends SortProxy {

  public void sort(long[] array) {
    if (array == null || array.length == 0)
      return;

    // size = length -1, 是因为排序到最后,最后一个数值肯定是最小（最大）值,
    // 因为，经过之前的各次轮询，已经将最后一个数据和其他数据比较过，因此，可以避免最后一个数再轮询一次
    int size = array.length - 1, curIndex;
    for (int i = 0; i < size; i++) {
      curIndex = i;// 标记当前位置

      // j <= length -1 ,是为了将最后一个数值纳入比较范围
      for (int j = i + 1; j <= size; j++) {
        if (array[j] < array[curIndex]) {
          curIndex = j;
        }
      }

      if (i != curIndex) {
        SortUtils.swap(array, i, curIndex);
      }
    }
  }

}
