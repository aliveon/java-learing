package com.huanyu.doc.interview.algorithm;

import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.huanyu.common.utils.PerfLogger;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

/**
 * 基于cglib 实现的切面
 *
 * @author yangtao
 */
public class SortInterceptor implements MethodInterceptor {

  private static final Logger logger = LoggerFactory.getLogger(SortInterceptor.class);

  public static <T> T getProxy(Class<? extends SortProxy> clazz) {
    Enhancer e = new Enhancer();
    //设置需要创建子类的类
    e.setSuperclass(clazz);
    e.setCallback(new SortInterceptor());
    //通过字节码技术动态创建子类实例
    return (T) e.create();
  }

  public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy)
    throws Throwable {
    logger.info("before sort,array:{}", JSON.toJSONString(args[0], true));
    //    long startTime = System.currentTimeMillis();
    PerfLogger pf = new PerfLogger(logger).start("sort");
    Object o1 = proxy.invokeSuper(obj, args);
    pf.stop();
    //    long time = System.currentTimeMillis() - startTime;
    //    logger.info("{} 方式排序共耗时：{} ms", obj.getClass().getGenericSuperclass().getTypeName(), time);
    logger.info("after sort,array:{}", JSON.toJSONString(args[0], true));
    return o1;
  }

}
