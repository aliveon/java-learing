package com.huanyu.doc.interview.algorithm;

import com.huanyu.common.utils.RandomUtils;

/**
 * @author yangtao
 */
public abstract class Sort {

  protected static final int range = 1000;

  protected abstract void sort(long[] array);

  protected long[] init(int count) {
    long[] array = new long[count];
    for (int i = 0; i < count; i++) {
      array[i] = RandomUtils.nextLong(-range, range);
    }

    return array;
  }


}
