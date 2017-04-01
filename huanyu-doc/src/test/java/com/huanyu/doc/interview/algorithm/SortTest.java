package com.huanyu.doc.interview.algorithm;

import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 排序测试
 * <p>
 * 常用排序算法总结(一){@link http://www.cnblogs.com/eniac12/p/5329396.html}
 *
 * @author yangtao
 */
public class SortTest {

  private final Logger logger = LoggerFactory.getLogger(this.getClass());

  private static final int COUNT = 500;
  private long[] array = null;

  @Before
  public void setup() {
    array = SortUtils.init(COUNT);
  }

  @After
  public void destory() {
    array = null;
  }

  //  @Test
  public void bubble_V1() {
    //    logger.info("before sort,array:{}", JSON.toJSONString(array, true));
    Assert.assertEquals(false, SortUtils.assertArray(array, true));
    new BinaryInsertionSort().sort(array);
    //    logger.info("after sort,array:{}", JSON.toJSONString(array, true));
    Assert.assertEquals(true, SortUtils.assertArray(array, true));
  }

  //  @Test
  public void bubble_V2() {
    Assert.assertEquals(false, SortUtils.assertArray(array, true));
    // improve 1: bringing in aop interceptor to instand of calculating the time of the method cost
    SortProxy sortProxy = SortInterceptor.getProxy(BubbleSort.class);
    sortProxy.sort(array);
    Assert.assertEquals(true, SortUtils.assertArray(array, true));
  }

  @Test
  public void operateLog() {
    logger.debug("debug:{}", "before modify");
    logger.info("superclass:{}", logger.getClass().getSuperclass());
    LogManager.getLogger(this.getClass()).setLevel(Level.DEBUG);
    logger.debug("debug:{}", "after modify");
  }

  @Test
  public void quick() {
    array = SortUtils.init(10);  // 查看快速排序的排序过程
    Assert.assertEquals(false, SortUtils.assertArray(array, true));
    SortProxy sortProxy = SortInterceptor.getProxy(Quicksort.class);
    LogManager.getLogger(Quicksort.class).setLevel(Level.DEBUG); //  开启 log4j 的 debug 模式
    sortProxy.sort(array);
    Assert.assertEquals(true, SortUtils.assertArray(array, true));
  }

  @Test
  public void compare() {
    compare(BubbleSort.class, CocktailSort.class, InsertionSort.class, BinaryInsertionSort.class,
      ShellSort.class, SelectionSort.class, Quicksort.class);
  }

  private void compare(Class<? extends SortProxy>... classes) {
    long[] copyArray;
    for (Class<? extends SortProxy> clazz : classes) {
      copyArray = array.clone();
      Assert.assertEquals(false, SortUtils.assertArray(copyArray, true));
      SortProxy sortProxy = SortInterceptor.getProxy(clazz);
      sortProxy.sort(copyArray);
      Assert.assertEquals(true, SortUtils.assertArray(copyArray, true));
    }
  }

}
