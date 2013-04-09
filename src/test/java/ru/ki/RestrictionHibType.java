package ru.ki;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.Restrictions;
import ru.ki.dao.support.ValueType;

import java.util.List;

public enum RestrictionHibType {
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

    public PredicateElement getCriterion(String property, List<ValueType> values) {
        Object value = values.get(0).getValue();
        String nameParam = property.replace('.', '_');
        String param = ':' + nameParam;
        String predicate = StringUtils.EMPTY;
        switch (this) {
            case EQ:
                predicate = Restrictions.eq(property, param).toString();
                break;
            case NE:
                predicate = Restrictions.ne(property, param).toString();
                break;
            case LT:
                predicate = Restrictions.lt(property, param).toString();
                break;
            case LE:
                predicate = Restrictions.le(property, param).toString();
                break;
            case GT:
                predicate = Restrictions.gt(property, param).toString();
                break;
            case GE:
                predicate = Restrictions.ge(property, param).toString();
                break;
            case LIKE:
                value = '%' + (String) value + '%';
                predicate = Restrictions.like(property, param).toString();
                break;
            case START:
                value = (String) value + '%';
                predicate = Restrictions.like(property, param).toString();
                break;
            case END:
                value = '%' + (String) value + '%';
                predicate = Restrictions.like(property, param).toString();
                break;
            case BETWEEN:
//                return cb.between(property, value, values.get(1).getValue());
                throw new RuntimeException("BETWEEN not support");
            case IN:
                throw new RuntimeException("IN not support");
        }
        return new PredicateElement(nameParam, predicate, value);
    }
}
