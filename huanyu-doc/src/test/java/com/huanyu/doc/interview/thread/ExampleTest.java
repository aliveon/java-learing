package com.huanyu.doc.interview.thread;

import com.huanyu.common.utils.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 在程序开发中只要是多线程肯定永远以实现Runnable接口为主，因为实现Runnable接口相比继承Thread类有如下好处：
 * <p>
 * 1. 避免单点继承的局限，一个类可以继承多个接口。
 * 2. 适合于资源的共享
 * <p>
 * 使用Runnable实现多线程可以达到资源共享目的。
 * <p>
 * <p>
 * 有三种方法可以使终止线程：
 * 1. 使用退出标志，使线程正常退出，也就是当run方法完成后线程终止。
 * 2. 使用stop方法强行终止线程（这个方法不推荐使用，因为stop和suspend、resume一样，也可能发生不可预料的结果）。
 * 3. 使用interrupt方法中断线程
 * <p>
 * <p>
 * {@link http://blog.csdn.net/bug_moving/article/details/55504221}
 *
 * @author yangtao
 */
public class ExampleTest {

  private static final Logger logger = LoggerFactory.getLogger(ExampleTest.class);

  public static void main(String[] args) {
    if (RandomUtils.nextInt(1, 3) >= 2) {
      new ExampleTest().runnableTest();
    } else {
      new ExampleTest().threadTest();
    }
  }

  public void runnableTest() {
    RunnableExample runner = new RunnableExample();
    Thread one = new Thread(runner, "one");
    Thread two = new Thread(runner, "two");
    Thread three = new Thread(runner, "three");

    one.start();
    two.start();
    three.start();
  }

  public void threadTest() {
    ThreadExample one = new ThreadExample("one");
    ThreadExample two = new ThreadExample("two");
    ThreadExample three = new ThreadExample("three");

    one.start();
    two.start();
    three.start();
  }

}
