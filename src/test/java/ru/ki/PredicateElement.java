package ru.ki;

/**
 * @author ikozar
 *         date     12.03.13
 */
public class PredicateElement {
    private String property;
    private String predicate;
    private Object value;

    public PredicateElement(String property, String predicate, Object value) {
        this.property = property;
        this.predicate = predicate;
        this.value = value;
    }

    public String getProperty() {
        return property;
    }

    public String getPredicate() {
        return predicate;
    }

    public Object getValue() {
        return value;
    }
}
