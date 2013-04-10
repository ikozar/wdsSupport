
package ru.ki.dao.support;

import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class FilterElement {

    protected String fieldName;
    protected RestrictionType operator;
    protected List<ValueType> values;

    public FilterElement() {
    }

    public FilterElement(String fieldName, RestrictionType operator, Object values) {
        this.fieldName = fieldName;
        this.operator = operator;
        this.values = Lists.newArrayList();
        if (values instanceof Collection) {
            for (Object value : (Collection) values) {
                this.values.add(new ValueType(value));
            }
        } else {
            this.values.add(new ValueType(values));
        }
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String value) {
        this.fieldName = value;
    }

    public RestrictionType getOperator() {
        return operator;
    }

    public void setOperator(RestrictionType value) {
        this.operator = value;
    }

    public List<ValueType> getValues() {
        if (values == null) {
            values = new ArrayList<ValueType>();
        }
        return this.values;
    }

    public void setValues(List<ValueType> values) {
        this.values = values;
    }
}
