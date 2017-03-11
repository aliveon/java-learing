package com.huanyu.doc.interview.algorithm;

//import java.util.ArrayList;

import java.util.Arrays;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;


/**
 * 冒泡排序
 *
 * @author yangtao
 */
public class BubbleSort extends Sort {

  private final Logger logger = LoggerFactory.getLogger(this.getClass());


  private static final int COUNT = 500;

  @Test
  public void example() {
    long[] array = init(COUNT);
    logger.info("before sort,array:{}", JSON.toJSONString(Arrays.asList(array), true));
    sort(array);
    logger.info("fater sort,array:{}", JSON.toJSONString(Arrays.asList(array), true));
  }

  @Override
  protected void sort(long[] array) {
    int size = array.length;
    for (int i = 0; i < size; i++) {
      for (int j = i + 1; j < size; j++) {
        // 从小到大排序
        if (array[i] > array[j]) {
          array[i] += array[j];
          array[j] = array[i] - array[j];
          array[i] -= array[j];
        }
      }
    }
  }

}
