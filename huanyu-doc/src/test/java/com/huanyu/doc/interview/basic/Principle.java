package com.huanyu.doc.interview.basic;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 基础原理
 *
 * @author yangtao
 */
public class Principle {

  private final Logger logger = LoggerFactory.getLogger(this.getClass());

  /**
   * 异常处理机制
   * <p>
   * {@link http://www.cnblogs.com/moonandstar08/p/5389003.html}
   */
  @Test
  public void tryCatchFinally() {
    /**
     * 翻阅了java 官方教程的finally语句。发现了官方教程对这个特殊情况有说明：
     * The finally block always executes when the try block exits. T
     * his ensures that the finally block is executed even if an unexpected exception occurs.
     * But finally is useful for more than just exception handling —
     * it allows the programmer to avoid having cleanup code accidentally bypassed by a return,
     * continue, or break. Putting cleanup code in a finally block is always a good practice,
     * even when no exceptions are anticipated.
     *
     * Note: If the JVM exits while the try or catch code is being executed,
     * then the finally block may not execute. Likewise,
     * if the thread executing the try or catch code is interrupted or killed,
     * the finally block may not execute even though the application as a whole continues.
     *
     * 个人简单翻译：
     *
     * 当try语句退出时肯定会执行finally语句。这确保了即使发了一个意想不到的异常也会执行finally语句块。
     * 但是finally的用处不仅是用来处理异常——它可以让程序员不会因为return、continue、或者break语句而忽略了清理代码。
     * 把清理代码放在finally语句块里是一个很好的做法，即便可能不会有异常发生也要这样做。
     */
    // try{return}finally
    int result = exceptionHandleReturn();
    logger.info("exceptionHandleReturn, result= {} \n ", result);
    Assert.assertEquals(2, result);

    /**
     * 注意，当try或者catch的代码在运行的时候，JVM退出了。
     * 那么finally语句块就不会执行。同样，如果线程在运行try或者catch的代码时被中断了或者被杀死了(killed)，
     * 那么finally语句可能也不会执行了，即使整个运用还会继续执行。
     *
     *
     * 从上面的官方说明，我们知道无论try里执行了return语句、break语句、还是continue语句，finally语句块还会继续执行。
     * 同时，在stackoverflow里也找到了一个答案，我们可以调用System.exit()来终止它：
     *
     * finally will be called. The only time finally won’t be called is: if you call System.exit(),
     * another thread interrupts current one, or if the JVM crashes first.
     *
     * 另外，在java的语言规范有讲到，如果在try语句里有return语句，finally语句还是会执行。
     * 它会在把控制权转移到该方法的调用者或者构造器前执行finally语句。
     * 也就是说，使用return语句把控制权转移给其他的方法前会执行finally语句。
     *
     */
    // try{exit}finally
    int res = exceptionHandleExit();
    logger.info("exceptionHandleExit, result= {} \n", res);
    Assert.assertEquals(1, res);
  }

  private int exceptionHandleReturn() {
    int i = 1;
    try {
      logger.info("in try, i= {}", i);
      // 等效于： int temp = ++i; return temp;返回的是临时变量,而不是 i
      return ++i;// 在return 的时候, i = 3;temp = 2;
    } catch (Exception e) {
      logger.error("", e);
    } finally {
      // finally 语句块在 return 之前执行, 用于资源回收、清理操作。
      ++i;
      logger.info("in finnally, i= {}", i);
    }
    return 0;
  }

  private int exceptionHandleExit() {
    int i = 1;
    try {
      logger.info("in try, i= {}", i);
      i /= 0;
    } catch (Exception e) {
      logger.error("", e);
      //      System.exit(1);
      Runtime.getRuntime().halt(1);
    } finally {
      // finally 语句块在 return 之前执行, 用于资源回收、清理操作。
      ++i;
      logger.info("in finnally, i= {}", i);
    }
    return i;
  }

}
