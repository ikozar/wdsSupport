
package ru.ki.dao.support;

import org.apache.commons.beanutils.ConvertUtils;
import org.dozer.util.MappingUtils;

import java.util.Date;

public class ValueType {
    protected String valueString;
    protected Integer valueInt;
    protected Long valueLong;
    protected Float valueFloat;
    protected Double valueDouble;
    protected Date valueDate;
    protected Object valueObject;

    public ValueType() {
    }

    public ValueType(Object value) {
        this();
        setValue(value);
    }

    public String getValueString() {
        return valueString;
    }

    public void setValueString(String value) {
        this.valueString = value;
    }

    public int getValueInt() {
        return valueInt;
    }

    public void setValueInt(int value) {
        this.valueInt = value;
    }

    public long getValueLong() {
        return valueLong;
    }

    public void setValueLong(long value) {
        this.valueLong = value;
    }

    public float getValueFloat() {
        return valueFloat;
    }

    public void setValueFloat(float value) {
        this.valueFloat = value;
    }

    public double getValueDouble() {
        return valueDouble;
    }

    public void setValueDouble(double value) {
        this.valueDouble = value;
    }

    public Date getValueDate() {
        return valueDate;
    }

    public void setValueDate(Date value) {
        this.valueDate = value;
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
        if (value instanceof String) valueString = (String) value;
        else if (value instanceof Integer) valueInt = (Integer) value;
        else if (value instanceof Long) valueLong = (Long) value;
        else if (value instanceof Float) valueFloat = (Float) value;
        else if (value instanceof Double) valueDouble = (Double) value;
        else if (value instanceof Date) valueDate = (Date) value;
        else valueObject = value;
    }

    public void clearValue() {
        valueString = null;
        valueInt = null;
        valueLong = null;
        valueFloat = null;
        valueDouble = null;
        valueDate = null;
        valueObject = null;
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
