package com.huanyu.doc.demo;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author yangtao
 */
public class FlowNoGenerator {
  private static final Logger logger = LoggerFactory.getLogger(FlowNoGenerator.class);
  public static final int TOTAL_LENGTH = 20;

  private static final String FORMATER = "yyyyMMddHHmmss";
  private static final String PATH = "/FlowNoGenerator/code.txt";

  private static LoadingCache<String, AtomicLong> CUR_FLOW_COUNT_MAP =
    CacheBuilder.newBuilder().maximumSize(1).expireAfterWrite(60, TimeUnit.SECONDS)
      .build(new CacheLoader<String, AtomicLong>() {
        public AtomicLong load(String key) throws Exception {
          logger.info("key:{}", key);
          return new AtomicLong(0);
        }
      });

  private static StringBuilder builder = new StringBuilder(TOTAL_LENGTH);

  public static synchronized String generate() throws Exception {
    builder.delete(0, builder.length());
    builder.append(new SimpleDateFormat(FORMATER).format(new Date()));

    return builder.append(StringUtils
      .leftPad(String.valueOf(CUR_FLOW_COUNT_MAP.get(builder.toString()).getAndAdd(1L)),
        TOTAL_LENGTH - builder.length(), "0")).toString();

    //    return builder.append(new FlowCodeRuler(TOTAL_LENGTH - builder.length()).flow(StringUtils
    //      .leftPad(String.valueOf(CUR_FLOW_COUNT_MAP.get(builder.toString()).getAndAdd(1L)),
    //        TOTAL_LENGTH - builder.length(), "0"))).toString();
  }

  private static List<String> codes = new ArrayList<String>();

  public static void main(String[] args) throws Exception {
    for (int i = 0; i < 1000000; i++)
      codes.add(FlowNoGenerator.generate());

    Iterator<String> it = codes.iterator();
    while (it.hasNext())
      write2File(PATH, it.next());
    //      logger.info(FlowNoGenerator.generate());

  }

  public static boolean write2File(String filePath, String content) throws Exception {
    File file = new File(filePath);

    createDir(file);

    if (!file.exists()) {
      try {
        file.createNewFile();
      } catch (IOException e) {
        logger.error("创建{}文件异常,异常信息:{}", new Object[]{filePath, e});
        return false;
      }
    }
    BufferedWriter writer = null;
    try {
      writer = new BufferedWriter(new FileWriter(filePath, true), 89120);
      writer.write(content + "\n");
      writer.flush();
    } catch (Exception e) {
      logger.error("写{}文件异常,异常信息:{}", new Object[]{filePath, e});
      return false;
    } finally {
      if (writer != null)
        try {
          writer.close();
        } catch (IOException e) {
          logger.error("写{}文件关闭输出流异常,异常信息:{}", new Object[]{filePath, e});
        }
    }
    return true;
  }

  private static void createDir(File file) throws Exception {
    //判断目标文件所在的目录是否存在
    if (!file.getParentFile().exists()) {
      if (!file.getParentFile().getParentFile().exists()) {
        createDir(file.getParentFile());
      }
      logger.info("创建文件夹{}", new Object[]{file.getParentFile()});
      if (!file.getParentFile().mkdirs()) {
        logger.info("创建文件夹:{} 失败", new Object[]{file.getParentFile()});
        throw new RuntimeException("创建文件夹:" + file.getParentFile() + " 失败");
      }
    }

  }
}