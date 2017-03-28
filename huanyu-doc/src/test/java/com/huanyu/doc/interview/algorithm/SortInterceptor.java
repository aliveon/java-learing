package com.huanyu.doc.interview.algorithm;

import com.alibaba.fastjson.JSON;
import com.huanyu.common.utils.PerfLogger;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

/**
 * 基于cglib 实现的切面
 *
 * @author yangtao
 */
public class SortInterceptor implements MethodInterceptor {

  private static final Logger LOG = LoggerFactory.getLogger(SortInterceptor.class);

  public static <T> T getProxy(Class<? extends SortProxy> clazz) {
    Enhancer enhancer = new Enhancer();
    //设置需要创建子类的类
    enhancer.setSuperclass(clazz);
    enhancer.setCallback(new SortInterceptor());
    //通过字节码技术动态创建子类实例
    return (T) enhancer.create();
  }

  public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy)
    throws Throwable {
    LOG.info("before sort,array:{}", JSON.toJSONString(args[0], true));
    PerfLogger pf = new PerfLogger(LOG).start("sort");
    Object o1 = proxy.invokeSuper(obj, args);
    pf.stop();
    LOG.info("after sort,array:{}", JSON.toJSONString(args[0], true));
    return o1;
  }

}
