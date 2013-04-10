
package ru.ki.dao.support;

import org.apache.commons.beanutils.ConvertUtils;
import org.dozer.util.MappingUtils;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlTransient;
import java.util.Date;

@XmlAccessorType(XmlAccessType.FIELD)
public class ValueType {
    @XmlTransient
    private Class type;
    protected String valueString;
    protected Integer valueInt;
    protected Long valueLong;
    protected Float valueFloat;
    protected Double valueDouble;
    protected Date valueDate;
    @XmlTransient
    protected Object valueObject;

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

    public String getValueString() {
        return valueString;
    }

    public void setValueString(String value) {
        this.valueString = value;
        type = value.getClass();
    }

    public Integer getValueInt() {
        return valueInt;
    }

    public void setValueInt(Integer value) {
        this.valueInt = value;
        type = value.getClass();
    }

    public Long getValueLong() {
        return valueLong;
    }

    public void setValueLong(Long value) {
        this.valueLong = value;
        type = value.getClass();
    }

    public Float getValueFloat() {
        return valueFloat;
    }

    public void setValueFloat(Float value) {
        this.valueFloat = value;
        type = value.getClass();
    }

    public Double getValueDouble() {
        return valueDouble;
    }

    public void setValueDouble(Double value) {
        this.valueDouble = value;
        type = value.getClass();
    }

    public Date getValueDate() {
        return valueDate;
    }

    public void setValueDate(Date value) {
        this.valueDate = value;
        type = value.getClass();
    }

    public Object getValue() {
        if (valueString != null) return valueString;
        if (valueInt != null) return valueInt;
        if (valueLong != null) return valueLong;
        if (valueFloat != null) return valueFloat;
        if (valueDouble != null) return valueDouble;
        if (valueDate != null) return valueDate;
        return valueObject;
    }

    public void setValue(Object value) {
        type = value.getClass();
        if (value instanceof String) valueString = (String) value;
        else if (value instanceof Integer) valueInt = (Integer) value;
        else if (value instanceof Long) valueLong = (Long) value;
        else if (value instanceof Float) valueFloat = (Float) value;
        else if (value instanceof Double) valueDouble = (Double) value;
        else if (value instanceof Date) valueDate = (Date) value;
        else {
            valueObject = value;
            type = Object.class;
        }
    }

    public void clearValue() {
        valueString = null;
        valueInt = null;
        valueLong = null;
        valueFloat = null;
        valueDouble = null;
        valueDate = null;
        valueObject = null;
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
