package com.huanyu.common.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

/**
 * @author yangtao
 */
public class FlowNoGenerator {

  public static final int TOTAL_LENGTH = 20;
  public static final String PREFIX = "prefix";
  public static final String TRAN_ID = "tranId";

  private static final Logger logger = LoggerFactory.getLogger(FlowNoGenerator.class);
  private static final String FORMATER = "yyyyMMddHHmmss";


  private static LoadingCache<String, AtomicLong> CUR_FLOW_COUNT_MAP =
    CacheBuilder.newBuilder().maximumSize(1).expireAfterWrite(60, TimeUnit.SECONDS)
      .build(new CacheLoader<String, AtomicLong>() {
        public AtomicLong load(String key) throws Exception {
          return new AtomicLong(0);
        }
      });

  private static StringBuilder builder = new StringBuilder();

  public static synchronized String generate() throws Exception {
    builder.delete(0, builder.length());
    builder.append(new SimpleDateFormat(FORMATER).format(new Date()));

    return builder.append(StringUtils
      .leftPad(String.valueOf(CUR_FLOW_COUNT_MAP.get(builder.toString()).getAndAdd(1L)),
        TOTAL_LENGTH - builder.length(), "0")).toString();
  }

  public static Map<String, String> split(String flowNo) {
    Map<String, String> map = new HashMap<String, String>();

    if (TOTAL_LENGTH >= flowNo.length())
      return map;

    map.put(PREFIX, flowNo.substring(0, flowNo.length() - FlowNoGenerator.TOTAL_LENGTH));
    map.put(TRAN_ID,
      flowNo.substring(flowNo.length() - FlowNoGenerator.TOTAL_LENGTH, flowNo.length()));

    return map;
  }

}