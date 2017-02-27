package com.huanyu.common.utils;

/**
 * @author yangtao
 */
public class RandomUtils {

  public static int generateRandom(int minRange, int maxRange) {
    return (int) (minRange + Math.random() * maxRange);
  }

}
