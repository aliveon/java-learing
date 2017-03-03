package com.huanyu.doc.demo;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author yangtao
 */
public class MockConfigStore implements ConfigStore {

  private static final Logger logger = LoggerFactory.getLogger(MockConfigStore.class);

  private static final String OSS_ADDRESS = "http://oss-cn-qingdao.aliyuncs.com";
  private static final String OSS_KEY_ID = "QNHu4EkolmrtW1VA";
  private static final String OSS_KEY_SECRET = "PsLrW91LiMM1fKoVCer6EIbPBVuT02";
  private static final String OSS_BUCKET = "cloudmembertest";

  private static Map<String, String> cache = new ConcurrentHashMap<String, String>();

  static {
    MockConfigStore store = new MockConfigStore();
    OssConfig config = new OssConfig();
    config.setAccessAddress(OSS_ADDRESS);
    config.setAccessKeyId(OSS_KEY_ID);
    config.setAccessKeySecret(OSS_KEY_SECRET);
    config.setBucket(OSS_BUCKET);
    store.put("-", OssConfig.NAME, config);
  }


  public void start() {
    logger.info("单元测试ConfigStore初始化start");
  }

  public void stop() {
    logger.info("单元测试ConfigStore终止stop");
  }

  public <T> T get(String tenantId, String name, Class<T> configClass) {
    try {
      T config = (T) Class.forName(configClass.getName()).getConstructor().newInstance();
      for (Field field : config.getClass().getDeclaredFields()) {
        if (field.getModifiers() != Modifier.PRIVATE)
          continue;
        field.setAccessible(true);
        field.set(config, cache.get(field.getName()));
        field.setAccessible(false);
      }
      return config;
    } catch (Exception e) {
      logger.error("获取配置异常,", e);
    }

    return null;
  }

  public <T> void put(String tenantId, String name, T config) {
    try {
      for (Field field : Arrays.asList(config.getClass().getDeclaredFields())) {
        if (field.getModifiers() != Modifier.PRIVATE)
          continue;
        field.setAccessible(true);
        cache.put(field.getName(), (String) field.get(config));
        field.setAccessible(false);
      }
    } catch (Exception e) {
      logger.error("添加配置异常,", e);
    }
  }

  public void cleanCache(String tenantId, String name) {
    //    cache.clear();
  }

  public <T> void addListener(String tenantId, String name, Class<T> configClass,
    ConfigListener<T> listener) {

  }

  private void init() {
    OssConfig config = new OssConfig();
    config.setAccessAddress(OSS_ADDRESS);
    config.setAccessKeyId(OSS_KEY_ID);
    config.setAccessKeySecret(OSS_KEY_SECRET);
    config.setBucket(OSS_BUCKET);
    put("-", OssConfig.NAME, config);
  }

}
