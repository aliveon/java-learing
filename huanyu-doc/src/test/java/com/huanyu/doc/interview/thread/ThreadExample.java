package com.huanyu.doc.interview.thread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author yangtao
 */
public class ThreadExample extends Thread {

  private final Logger logger = LoggerFactory.getLogger(this.getClass());

  private String name;
  private int source = 10;

  public ThreadExample(String name) {
    this.name = name;
  }

  public void run() {
    while (source > 0) {
      logger.info("thread--{}, now:{}", name, --source);
    }
  }


}
