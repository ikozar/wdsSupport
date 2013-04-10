package ru.ki;

import org.apache.commons.proxy.Interceptor;
import org.apache.commons.proxy.Invocation;
import org.junit.Ignore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.*;

/**
 * Unit test for simple App.
 */
@Ignore
public class AppTest
{
  private final Logger logger = LoggerFactory.getLogger(AppTest.class);

  private static class InterceptorTester implements Interceptor
  {
    private Object[] arguments;
    private Method method;
    private Object proxy;
    private Class invocationClass;

    public Object intercept( Invocation methodInvocation ) throws Throwable
    {
      arguments = methodInvocation.getArguments();
      method = methodInvocation.getMethod();
      proxy = methodInvocation.getProxy();
      invocationClass = methodInvocation.getClass();

      return methodInvocation.proceed();
    }
  }

  private static class InterceptorTester1 implements Interceptor
  {
    private Object[] arguments;
    private Method method;
    private Object proxy;
    private Class invocationClass;

    public Object intercept( Invocation methodInvocation ) throws Throwable
    {
      arguments = methodInvocation.getArguments();
      method = methodInvocation.getMethod();
      proxy = methodInvocation.getProxy();
      invocationClass = methodInvocation.getClass();
      String key = method.getName().substring(3, 4).toLowerCase() + method.getName().substring(4);

      if (method.getName().startsWith("get"))
        return ((Map) proxy).get(key);
      else if (method.getName().startsWith("set"))
        ((Map) proxy).put(key, arguments[0]);
      return null;
    }
  }

}
