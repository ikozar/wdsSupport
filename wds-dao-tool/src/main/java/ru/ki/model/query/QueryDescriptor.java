package ru.ki.model.query;

import ru.ki.dao.support.SearchParameters;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author ikozar
 * date    09.04.13
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "queryDescriptor")
public class QueryDescriptor {
    private String entityClass;
    private String returnType;
    private SearchParameters searchParameters;

    public String getEntityClass() {
        return entityClass;
    }

    public void setEntityClass(String entityClass) {
        this.entityClass = entityClass;
    }

    public String getReturnType() {
        return returnType;
    }

    public void setReturnType(String returnType) {
        this.returnType = returnType;
    }

    public SearchParameters getSearchParameters() {
        return searchParameters;
    }

    public void setSearchParameters(SearchParameters searchParameters) {
        this.searchParameters = searchParameters;
    }
}
