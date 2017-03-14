package com.huanyu.doc.interview.thread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author yangtao
 */
public class RunnableExample implements Runnable {

  private final Logger logger = LoggerFactory.getLogger(this.getClass());

  private int source = 10;

  public void run() {
    while (source > 0) {
      logger.info("thread--{}, now:{}", Thread.currentThread().getName(), --source);
    }
  }

}
