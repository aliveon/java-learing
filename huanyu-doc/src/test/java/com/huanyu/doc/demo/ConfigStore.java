package com.huanyu.doc.demo;


/**
 * @author yangtao
 */
public interface ConfigStore {

  public void start();

  public void stop();

  public <T> T get(String tenantId, String name, Class<T> configClass);

  public <T> void put(String tenantId, String name, T config);

  public void cleanCache(String tenantId, String name);

  public <T> void addListener(String tenantId, String name, Class<T> configClass,
    ConfigListener<T> listener);

}
