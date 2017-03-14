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

  @Test
  public void bubble() {
    //    logger.info("before sort,array:{}", JSON.toJSONString(array, true));
    Assert.assertEquals(false, SortUtils.assertArray(array, true));
    //    new BubbleSort().sort(array);
    SortProxy sortProxy = SortInterceptor.getProxy(BubbleSort.class);
    sortProxy.sort(array);
    //    logger.info("after sort,array:{}", JSON.toJSONString(array, true));
    Assert.assertEquals(true, SortUtils.assertArray(array, true));
  }

  @Test
  public void cocktail() {
    //    logger.info("before sort,array:{}", JSON.toJSONString(array, true));
    Assert.assertEquals(false, SortUtils.assertArray(array, true));
    //    new CocktailSort().sort(array);
    SortProxy sortProxy = SortInterceptor.getProxy(CocktailSort.class);
    sortProxy.sort(array);
    //    logger.info("after sort,array:{}", JSON.toJSONString(array, true));
    Assert.assertEquals(true, SortUtils.assertArray(array, true));
  }

  @Test
  public void insertion() {
    //    logger.info("before sort,array:{}", JSON.toJSONString(array, true));
    Assert.assertEquals(false, SortUtils.assertArray(array, true));
    //    new InsertionSort().sort(array);
    SortProxy sortProxy = SortInterceptor.getProxy(InsertionSort.class);
    sortProxy.sort(array);
    //    logger.info("after sort,array:{}", JSON.toJSONString(array, true));
    Assert.assertEquals(true, SortUtils.assertArray(array, true));
  }

  @Test
  public void binaryInsertion() {
    //    logger.info("before sort,array:{}", JSON.toJSONString(array, true));
    Assert.assertEquals(false, SortUtils.assertArray(array, true));
    //    new BinaryInsertionSort().sort(array);
    SortProxy sortProxy = SortInterceptor.getProxy(BinaryInsertionSort.class);
    sortProxy.sort(array);
    //    logger.info("after sort,array:{}", JSON.toJSONString(array, true));
    Assert.assertEquals(true, SortUtils.assertArray(array, true));
  }

  @Test
  public void shell() {
    //    logger.info("before sort,array:{}", JSON.toJSONString(array, true));
    Assert.assertEquals(false, SortUtils.assertArray(array, true));
    //    new ShellSort().sort(array);
    SortProxy sortProxy = SortInterceptor.getProxy(ShellSort.class);
    sortProxy.sort(array);
    //    logger.info("after sort,array:{}", JSON.toJSONString(array, true));
    Assert.assertEquals(true, SortUtils.assertArray(array, true));
  }

  @Test
  public void selection() {
    //    logger.info("before sort,array:{}", JSON.toJSONString(array, true));
    Assert.assertEquals(false, SortUtils.assertArray(array, true));
    //    new SelectionSort().sort(array);
    SortProxy sortProxy = SortInterceptor.getProxy(SelectionSort.class);
    sortProxy.sort(array);

    //    logger.info("after sort,array:{}", JSON.toJSONString(array, true));
    Assert.assertEquals(true, SortUtils.assertArray(array, true));
  }


}
