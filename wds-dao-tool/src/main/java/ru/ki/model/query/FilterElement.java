
package ru.ki.model.query;

import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class FilterElement {

    protected String field;
    protected RestrictionType operator;
    protected List<ValueType> values;

    public FilterElement() {
    }

    public FilterElement(String field, RestrictionType operator, Object values) {
        this.field = field;
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

    public String getField() {
        return field;
    }

    public void setField(String value) {
        this.field = value;
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
