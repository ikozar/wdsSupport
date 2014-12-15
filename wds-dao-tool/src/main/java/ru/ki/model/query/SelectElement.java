
package ru.ki.model.query;

import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SelectElement {

    protected String fieldName;
    protected String alias;

    public SelectElement() {
    }

    public SelectElement(String fieldName, String alias) {
        this.fieldName = fieldName;
        this.alias = alias;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String value) {
        this.fieldName = value;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }
}
