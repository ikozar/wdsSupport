
package ru.ki.model.query;

import org.apache.commons.beanutils.ConvertUtils;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlTransient;
import java.util.Date;

@XmlAccessorType(XmlAccessType.FIELD)
public class ValueType {
    @XmlTransient
    private Class type;
    protected String vString;
    protected Integer vInt;
    protected Long vLong;
    protected Float vFloat;
    protected Double vDouble;
    protected Date vDate;
    @XmlTransient
    protected Object vObject;

    public ValueType() {
    }

    public ValueType(Object value) {
        this();
        setValue(value);
    }

    public Class getType() {
        return type;
    }

    public void setType(Class type) {
        this.type = type;
    }

    public String getvString() {
        return vString;
    }

    public void setvString(String value) {
        this.vString = value;
        type = value.getClass();
    }

    public Integer getvInt() {
        return vInt;
    }

    public void setvInt(Integer value) {
        this.vInt = value;
        type = value.getClass();
    }

    public Long getvLong() {
        return vLong;
    }

    public void setvLong(Long value) {
        this.vLong = value;
        type = value.getClass();
    }

    public Float getvFloat() {
        return vFloat;
    }

    public void setvFloat(Float value) {
        this.vFloat = value;
        type = value.getClass();
    }

    public Double getvDouble() {
        return vDouble;
    }

    public void setvDouble(Double value) {
        this.vDouble = value;
        type = value.getClass();
    }

    public Date getvDate() {
        return vDate;
    }

    public void setvDate(Date value) {
        this.vDate = value;
        type = value.getClass();
    }

    public Object getValue() {
        if (vString != null) return vString;
        if (vInt != null) return vInt;
        if (vLong != null) return vLong;
        if (vFloat != null) return vFloat;
        if (vDouble != null) return vDouble;
        if (vDate != null) return vDate;
        return vObject;
    }

    public void setValue(Object value) {
        type = value.getClass();
        if (value instanceof String) vString = (String) value;
        else if (value instanceof Integer) vInt = (Integer) value;
        else if (value instanceof Long) vLong = (Long) value;
        else if (value instanceof Float) vFloat = (Float) value;
        else if (value instanceof Double) vDouble = (Double) value;
        else if (value instanceof Date) vDate = (Date) value;
        else {
            vObject = value;
            type = Object.class;
        }
    }

    public void clearValue() {
        vString = null;
        vInt = null;
        vLong = null;
        vFloat = null;
        vDouble = null;
        vDate = null;
        vObject = null;
        type = null;
    }

    public void convertTo(Class javaType) {
        Object value = getValue();
        if (javaType.isEnum()) {
            try {
                value = Enum.valueOf(javaType, value.toString());
            } catch (Exception e) {
                throw new RuntimeException("Enum " + javaType.getSimpleName() +
                    '.' + value.toString() + " not found");
            }
        }
        value = ConvertUtils.convert(value, javaType);
        clearValue();
        setValue(value);
    }
}
