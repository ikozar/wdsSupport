package ru.ki;

import com.google.common.collect.Lists;
import org.apache.commons.proxy.Interceptor;
import org.apache.commons.proxy.Invocation;
import org.apache.commons.proxy.ProxyFactory;
import org.apache.commons.proxy.factory.cglib.CglibProxyFactory;
import org.dozer.DozerBeanMapper;
import org.dozer.MappingProcessor;
import org.dozer.classmap.ClassMap;
import org.dozer.classmap.ClassMappings;
import org.dozer.fieldmap.FieldMap;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.ki.model.entity.EntityA;
import ru.ki.model.ws.ValueABC1;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import java.io.StringReader;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * Unit test for simple App.
 */
public class AppTest
{
  private final Logger logger = LoggerFactory.getLogger(AppTest.class);

  @Test
  public void testJaxb() throws JAXBException {
    ValueABC1 abc = new ValueABC1();
    abc.setA1("a1");
    abc.setB2(2);
    abc.setC2(new Date());
    JAXBContext jaxbContext = JAXBContext.newInstance(ValueABC1.class);
    StringWriter sw = new StringWriter();
    jaxbContext.createMarshaller().marshal(abc, sw);
    System.out.println("===" + sw.toString());
  }

  @Test
  public void testProxyJaxb() throws JAXBException {
    ValueABC1 abc = new ValueABC1();
    abc.setA1("a1");
    abc.setB2(2);
    abc.setC2(new Date());

    ProxyFactory factory = new CglibProxyFactory();
    ValueABC1 proxy = (ValueABC1) factory.createInterceptorProxy( abc, new InterceptorTester(), new Class[] { ValueABC1.class } );

    JAXBContext jaxbContext = JAXBContext.newInstance(ValueABC1.class);
    StringWriter sw = new StringWriter();
    jaxbContext.createMarshaller().marshal(proxy, sw);
    System.out.println("===" + sw.toString());
  }

  @Test
  public void testProxyJaxb1() throws JAXBException {
    Map<String, Object> abcProxy = new HashMap<String, Object>();
//    abcProxy.put("a1", "a1");
//    abcProxy.put("b2", 2);
//    abcProxy.put("c2", new Date());

    ProxyFactory factory = new CglibProxyFactory();
    ValueABC1 proxy = (ValueABC1) factory.createInterceptorProxy( abcProxy, new InterceptorTester1(), new Class[] { ValueABC1.class } );
    proxy.setA1("a1");
    proxy.setB2(2);
    proxy.setC2(new Date());

    JAXBContext jaxbContext = JAXBContext.newInstance(ValueABC1.class);
    StringWriter sw = new StringWriter();
    jaxbContext.createMarshaller().marshal(proxy, sw);
    System.out.println("===" + sw.toString());

    String s = "<root_ClassABC><a1>a1</a1><b2>2</b2><c2>2013-01-23T07:52:23.559+03:00</c2></root_ClassABC>";
//    Map<String, Object> abcProxy1 = new HashMap<String, Object>();
//    ValueABC1 proxy1 = ( ValueABC1 ) factory.createInterceptorProxy( abcProxy, new InterceptorTester1(), new Class[] { ValueABC1.class } );
    ValueABC1 abc = (ValueABC1) jaxbContext.createUnmarshaller().unmarshal(new StringReader(s));
    System.out.printf("+++" + abc);
  }

  @Test
  public void testDozer() throws JAXBException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
    Mapper dozer = new Mapper(Lists.newArrayList("dozer-mappings.xml"));
    ValueABC1 abc = new ValueABC1();
    abc.setA1("a1");
    abc.setB2(2);
    abc.setC2(new Date());

    List<FieldMap> fieldMaps1 = dozer.getFieldsMap(ValueABC1.class, EntityA.class, "");

    EntityA a = dozer.map(abc, EntityA.class);

    List<FieldMap> fieldMaps = dozer.getFieldsMap(ValueABC1.class, EntityA.class, "");

    System.out.println("===" + fieldMaps);
  }


  private static class Mapper extends DozerBeanMapper
  {
    private final Logger logger = LoggerFactory.getLogger(AppTest.class);
    private Method getClassMap;

    public Mapper(List<String> mappingFiles) {
      super(mappingFiles);
      try {
        getClassMap = MappingProcessor.class.getDeclaredMethod("getClassMap", new Class[] {Class.class, Class.class, String.class});
        getClassMap.setAccessible(true);

        org.dozer.Mapper mapper = getMappingProcessor();
        Field field = MappingProcessor.class.getDeclaredField("classMappings");
        field.setAccessible(true);
        ClassMappings mapping = (ClassMappings) field.get(mapper);

        field = ClassMappings.class.getDeclaredField("classMappings");
        field.setAccessible(true);
        Map<String, ClassMap> classMappings = (Map<String, ClassMap>) field.get(mapping);

        System.out.println(classMappings);

      } catch (NoSuchMethodException e) {
        e.printStackTrace();
      } catch (IllegalAccessException e) {
        e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
      } catch (NoSuchFieldException e) {
        e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
      }
    }

    public List<FieldMap> getFieldsMap(Class<?> srcClass, Class<?> destClass, String mapId) {
      try {
        return  ((ClassMap) getClassMap.invoke((MappingProcessor) getMappingProcessor(), new Object[] {srcClass, destClass, mapId})).getFieldMaps();
      } catch (IllegalAccessException e) {
        logger.error("IllegalAccessException " + e.getMessage());
      } catch (InvocationTargetException e) {
        logger.error("InvocationTargetException " + e.getMessage());
      }
      return Collections.EMPTY_LIST;
    }

  }

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
