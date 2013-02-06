package ru.ki;

import java.lang.reflect.Field;

/**
 * Hello world!
 *
 */
public class App 
{

  static class Other
  {
    private String str = "hihi";
  }

    public static void main( String[] args ) throws NoSuchFieldException, IllegalAccessException {
      Other t = new Other();
      Field field = Other.class.getDeclaredField("str");
      field.setAccessible(true);
      Object value = field.get(t);
      System.out.println(value);

    }
}
