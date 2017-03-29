package com.huanyu.doc.interview.algorithm;

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
  public void bubble_example() {
    //    logger.info("before sort,array:{}", JSON.toJSONString(array, true));
    Assert.assertEquals(false, SortUtils.assertArray(array, true));
    //    new BinaryInsertionSort().sort(array);

    // improve 1: bringing in aop interceptor to instand of calculating the time of the method cost
    SortProxy sortProxy = SortInterceptor.getProxy(BubbleSort.class);
    sortProxy.sort(array);
    //    logger.info("after sort,array:{}", JSON.toJSONString(array, true));
    Assert.assertEquals(true, SortUtils.assertArray(array, true));
  }

  @Test
  public void bubble() {
    Assert.assertEquals(false, SortUtils.assertArray(array, true));
    SortProxy sortProxy = SortInterceptor.getProxy(BubbleSort.class);
    sortProxy.sort(array);
    Assert.assertEquals(true, SortUtils.assertArray(array, true));
  }

  @Test
  public void cocktail() {
    Assert.assertEquals(false, SortUtils.assertArray(array, true));
    SortProxy sortProxy = SortInterceptor.getProxy(CocktailSort.class);
    sortProxy.sort(array);
    Assert.assertEquals(true, SortUtils.assertArray(array, true));
  }

  @Test
  public void insertion() {
    Assert.assertEquals(false, SortUtils.assertArray(array, true));
    SortProxy sortProxy = SortInterceptor.getProxy(InsertionSort.class);
    sortProxy.sort(array);
    Assert.assertEquals(true, SortUtils.assertArray(array, true));
  }

  @Test
  public void binaryInsertion() {
    Assert.assertEquals(false, SortUtils.assertArray(array, true));
    SortProxy sortProxy = SortInterceptor.getProxy(BinaryInsertionSort.class);
    sortProxy.sort(array);
    Assert.assertEquals(true, SortUtils.assertArray(array, true));
  }

  @Test
  public void shell() {
    Assert.assertEquals(false, SortUtils.assertArray(array, true));
    SortProxy sortProxy = SortInterceptor.getProxy(ShellSort.class);
    sortProxy.sort(array);
    Assert.assertEquals(true, SortUtils.assertArray(array, true));
  }

  @Test
  public void selection() {
    Assert.assertEquals(false, SortUtils.assertArray(array, true));
    SortProxy sortProxy = SortInterceptor.getProxy(SelectionSort.class);
    sortProxy.sort(array);
    Assert.assertEquals(true, SortUtils.assertArray(array, true));
  }

  @Test
  public void quick() {
    //    array = SortUtils.init(10);  // 开启 log4j 的 debug 模式，查看快速排序的排序过程
    Assert.assertEquals(false, SortUtils.assertArray(array, true));
    SortProxy sortProxy = SortInterceptor.getProxy(Quicksort.class);
    sortProxy.sort(array);
    Assert.assertEquals(true, SortUtils.assertArray(array, true));
  }

}
