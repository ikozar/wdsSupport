package ru.ki.model.ws;

import javax.xml.bind.annotation.*;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: ikozar
 * Date: 22.01.13
 * Time: 15:35
 * To change this template use File | Settings | File Templates.
 */
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlType(name = "ValueABC1")
@XmlRootElement(name = "root_ClassABC")
public class ValueABC1 {
  String a1;
  Integer b2;
  Date c2;

  @XmlElement(name = "a1")
  public String getA1() {
    return a1;
  }

  public void setA1(String a1) {
    this.a1 = a1;
  }

  @XmlElement(name = "b2")
  public Integer getB2() {
    return b2;
  }

  public void setB2(Integer b2) {
    this.b2 = b2;
  }

  @XmlElement(name = "c2")
  public Date getC2() {
    return c2;
  }

  public void setC2(Date c2) {
    this.c2 = c2;
  }
}
