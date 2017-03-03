package com.huanyu.common.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * @author yangtao
 */
public class GenerateUUIDFactory {

  private static StringBuffer buf;
  private static SimpleDateFormat sdf;

  private static GenerateUUIDFactory instance;

  private GenerateUUIDFactory() {
    buf = new StringBuffer();
    sdf = new SimpleDateFormat("yyyyMMdd");
  }

  public static GenerateUUIDFactory getInstance() {
    if (instance == null) {
      instance = new GenerateUUIDFactory();
    }
    return instance;
  }

  public String generateUUID() {
    Date now = new Date();
    buf.setLength(0);
    buf.append(sdf.format(now));
    buf.append(now.getTime());
    buf.append(UUID.randomUUID().toString().replaceAll("-", "").substring(0, 6));
    return buf.toString();
  }

}
