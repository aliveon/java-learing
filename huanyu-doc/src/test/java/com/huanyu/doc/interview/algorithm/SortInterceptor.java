package com.huanyu.doc.interview.algorithm;

import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

/**
 * @author yangtao
 */
public class SortInterceptor implements MethodInterceptor {

  private final Logger logger = LoggerFactory.getLogger(this.getClass());

  private Enhancer enhancer = new Enhancer();

  public Object getProxy(Class clazz) {
    //设置需要创建子类的类
    enhancer.setSuperclass(clazz);
    enhancer.setCallback(this);
    //通过字节码技术动态创建子类实例
    return enhancer.create();
  }


  public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy)
    throws Throwable {
    long startTime = System.currentTimeMillis();
    Object o1 = proxy.invokeSuper(method, args);
    logger.info("共耗时：{} ms", System.currentTimeMillis() - startTime);
    return o1;
  }

}
