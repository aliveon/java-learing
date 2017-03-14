package com.huanyu.common.utils;

import org.slf4j.Logger;

/**
 * 性能日志记录器
 *
 * @author yangtao
 */
public class PerfLogger {


  private Logger logger;
  private Watch watch;
  private String name;

  public PerfLogger(Logger logger) {
    this.logger = logger;
    this.watch = new Watch();
  }

  public PerfLogger start(String name) {
    this.name = name;
    watch.start();
    return this;
  }

  public PerfLogger stop() {
    logger.info("{} 耗时 {} ms。", name, watch.stop());
    return this;
  }

  public PerfLogger cp(String checkpointName) {
    logger.info("{}/{} 耗时 {} ms。", new Object[]{name, checkpointName, watch.checkpoint()});
    return this;
  }

  /**
   * 计时表
   */
  class Watch {

    private long startTime;
    private long checkpointTime;

    public void start() {
      startTime = System.currentTimeMillis();
      checkpointTime = startTime;
    }

    public long stop() {
      long stopTime = System.currentTimeMillis();
      return stopTime - startTime;
    }

    public long getCheckpointTime() {
      return checkpointTime;
    }

    public long checkpoint() {
      long current = System.currentTimeMillis();
      long gap = current - checkpointTime;
      checkpointTime = current;
      return gap;
    }
  }

}
