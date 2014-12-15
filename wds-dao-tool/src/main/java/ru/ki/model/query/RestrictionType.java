package ru.ki.model.query;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import java.util.List;

public enum RestrictionType {
    EQ,
    NE,
    LT,
    LE,
    GT,
    GE,
    LIKE,
    START,
    END,
    BETWEEN,
    IN;

    public Predicate getCriterion(Expression property, CriteriaBuilder cb, List<ValueType> values) {
        Object value = values.get(0).getValue();
        switch (this) {
            case EQ:
                return cb.equal(property, value);
            case NE:
                return cb.notEqual(property, value);
            case LT:
                return cb.lessThan(property, (Comparable) value);
            case LE:
                return cb.lessThanOrEqualTo(property, (Comparable) value);
            case GT:
                return cb.greaterThan(property, (Comparable) value);
            case GE:
                return cb.greaterThanOrEqualTo(property, (Comparable) value);
            case LIKE:
                return cb.like(property, '%' + (String) value + '%');
            case START:
                return cb.like(property, (String) value + '%');
            case END:
                return cb.like(property, '%' + (String) value);
            case BETWEEN:
//                return cb.between(property, value, values.get(1).getValue());
                throw new RuntimeException("BETWEEN not support");
            case IN:
                throw new RuntimeException("IN not support");
        }
      return null;
    }
}
