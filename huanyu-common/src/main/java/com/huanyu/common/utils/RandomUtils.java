package com.huanyu.common.utils;

/**
 * @author yangtao
 */
public class RandomUtils {

  public static int nextInt(int minRange, int maxRange) {
    return (int) (minRange + Math.random() * maxRange);
  }

  public static double nextDouble(double minRange, double maxRange) {
    return (minRange + Math.random() * maxRange);
  }

  public static long nextLong(long minRange, long maxRange) {
    return (long) (minRange + Math.random() * maxRange);
  }

}
