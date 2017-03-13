package com.huanyu.doc.interview.algorithm;

/**
 * 希尔排序
 * <p>
 * 希尔排序，也叫递减增量排序，是插入排序的一种更高效的改进版本。希尔排序是不稳定的排序算法。
 * <p>
 * 　　希尔排序是基于插入排序的以下两点性质而提出改进方法的：
 * <p>
 * 1. 插入排序在对几乎已经排好序的数据操作时，效率高，即可以达到线性排序的效率
 * 2. 但插入排序一般来说是低效的，因为插入排序每次只能将数据移动一位
 * <p>
 * 希尔排序通过将比较的全部元素分为几个区域来提升插入排序的性能。这样可以让一个元素可以一次性地朝最终位置前进一大步。
 * 然后算法再取越来越小的步长进行排序，算法的最后一步就是普通的插入排序，
 * 但是到了这步，需排序的数据几乎是已排好的了（此时插入排序较快）。
 * <p>
 * 假设有一个很小的数据在一个已按升序排好序的数组的末端。如果用复杂度为O(n^2)的排序（冒泡排序或直接插入排序），
 * 可能会进行n次的比较和交换才能将该数据移至正确位置。而希尔排序会用较大的步长移动数据，
 * 所以小数据只需进行少数比较和交换即可到正确位置。
 *
 * @author yangtao
 */
public class ShellSort implements SortProxy {

  public void sort(long[] array) {
    if (array == null || array.length == 0)
      return;

    int size = array.length, step = 0, j;
    long curValue;
    // 生成初始增量
    while (step <= size) {
      step = 3 * step + 1;
    }

    while (step >= 1) {
      // 插入排序
      for (int i = step; i < size; i++) {
        j = i - step;
        curValue = array[i];
        while ((j >= 0) && (array[j] > curValue)) {
          array[j + step] = array[j];
          j = j - step;
        }
        array[j + step] = curValue;
      }
      // 递减增量
      step = (step - 1) / 3;
    }
  }

}
