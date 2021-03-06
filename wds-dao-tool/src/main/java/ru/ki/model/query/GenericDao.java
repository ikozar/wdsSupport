package ru.ki.model.query;

import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import ru.ki.MyStringUtils;
import ru.ki.dao.support.mapper.MapperHandler;
import ru.ki.model.FindResult;

import javax.persistence.*;
import javax.persistence.criteria.*;
import java.io.Serializable;
import java.util.*;

/**
 * JPA 2 Generic DAO with find by example/range/pattern and CRUD support. 
 */
public abstract class GenericDao<E, PK extends Serializable> {
    static final public String ROOT = "ROOT";

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired(required = true)
    private MapperHandler mapperHandler;

    private Class<E> type;
    private Logger log;
    private String cacheRegion;

    private static Map<String, GenericDao> daoRegistry = new HashMap<String, GenericDao>();

    protected EntityManager getEntityManager() {
        return entityManager;
    }

    /**
     * This constructor needs the real type of the generic type E so it can be passed to the {@link javax.persistence.EntityManager}.
     */
    public GenericDao(Class<E> type) {
        this.type = type;
        this.log = LoggerFactory.getLogger(getClass());
        this.cacheRegion = type.getCanonicalName();
        daoRegistry.put(type.getSimpleName(), this);
    }

    public static GenericDao getDao(String typeName) {
        return daoRegistry.get(typeName);
    }

    @SuppressWarnings("unchecked")
    public <T> FindResult<E> find(SearchParameters sp) {
        return find(sp, type);
    }

    @SuppressWarnings("unchecked")
//    public <T> FindResult<T> find(SearchParameters sp, @NotNull Class<T> typeReturn) {
    public <T> FindResult<T> find(SearchParameters sp, Class<T> typeReturn) {
        FindResult<T> findResult = new FindResult<T>(mapperHandler);

        if (typeReturn != Tuple.class && typeReturn != getJavaType()
                && !sp.isCustomSelect()) {
            sp.setSelects(mapperHandler.getSelectionList(typeReturn, getJavaType()));
        }
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery criteriaQuery;
        if (sp.isCustomSelect()) {
            criteriaQuery = builder.createQuery(Tuple.class);
        } else {
            criteriaQuery = builder.createQuery(type);
        }
        // criteriaQuery.distinct(true); // TODO: when active, breaks the sort on x-to-one property!

        Map<String, From> joins = new LinkedHashMap<String, From>();
        joins.put(ROOT, criteriaQuery.from(type));
        for (String join : sp.getJoins()) {
            findOrCreateJoinByPath(join, joins, true);
        }
        Predicate predicate = getPredicate(joins, builder, sp);
        if (predicate != null) {
            criteriaQuery = criteriaQuery.where(predicate);
        }

        if (sp.isCustomSelect()) {
            criteriaQuery.multiselect(getSelections(sp, joins));
        }

        createOrder(sp, builder, criteriaQuery, joins);

        TypedQuery typedQuery = entityManager.createQuery(criteriaQuery);
        setCacheHints(typedQuery, sp);          // cache

        if (sp.isPagination()) {
            if (sp.getFirstResult() >= 0) {
                typedQuery.setFirstResult(sp.getFirstResult());
            }
            if (sp.getMaxResults() > 0) {
                typedQuery.setMaxResults(sp.getMaxResults());
            }
        }

        findResult.setResultList(typedQuery.getResultList(), typeReturn);

        if (sp.isPagination()) {
            if (sp.getMaxResults() > findResult.getCountResult()) {
                findResult.setCount((long) findResult.getCountResult());
            } else {
                builder = entityManager.getCriteriaBuilder();
                CriteriaQuery<Long> criteriaQueryCount = builder.createQuery(Long.class);
                // criteriaQuery.distinct(true); // TODO: not taken into account !

                joins.clear();
                joins.put(ROOT, criteriaQueryCount.from(type));
                predicate = getPredicate(joins, builder, sp);
                if (predicate != null) {
                    criteriaQueryCount = criteriaQueryCount.where(predicate);
                }

                // count
                criteriaQueryCount = criteriaQueryCount.select(builder.count(joins.get(ROOT)));

                if (predicate != null) {
                    criteriaQueryCount = criteriaQueryCount.where(predicate);
                }

                TypedQuery<Long> countQuery = entityManager.createQuery(criteriaQueryCount);
                setCacheHints(countQuery, sp);

                findResult.setCount(countQuery.getSingleResult());
            }
        }

        if (log.isDebugEnabled()) {
            log.debug("Returned " + findResult.getCountResult() + " from " +
                findResult.getCount() + " elements of " + typeReturn.getSimpleName());
        }

        return findResult;
    }

    private List<Selection<?>> getSelections(SearchParameters sp, Map<String, From> joins) {
        if (!sp.isCustomSelect() || sp.getSelects().isEmpty()) {
            return Collections.EMPTY_LIST;
        }
        List<Selection<?>> selectionList = new ArrayList<Selection<?>>();
        for (SelectElement se : sp.getSelects()) {
            selectionList.add(findPath(se.getFieldName(), joins).alias(se.getAlias()));
        }
        return selectionList;
    }

    public E get(PK id) {
        if (id == null) {
            return null;
        }

        E entityFound = getEntityManager().find(type, id);

        if (entityFound == null) {
            log.warn("get returned null with pk=" + id);
        }

        return entityFound;
    }

    public Class<E> getJavaType() {
        return type;
    }

    public void refresh(E entity) {
        if (entityManager.contains(entity)) {
            entityManager.refresh(entity);
        }
    }

    public <T> T findUnique(SearchParameters sp, Class<T> typeReturn) {
        return findUniqueOrNone(sp, typeReturn);
    }

    public E findUnique(SearchParameters sp) {
        return findUniqueOrNone(sp, type);
    }

    private  <T> T findUniqueOrNone(SearchParameters sp, Class<T> typeReturn) {
        // this code is an optimization to prevent using a count
        sp.setFirstResult(0);
        sp.setMaxResults(2);
        FindResult<T> fr = find(sp, typeReturn);

        if (fr.getCount() > 1) {
            throw new NonUniqueResultException("Developper: You expected 1 result but we found more !");
        }

        if (fr.getCount() == 0) {
            return null;
        }

        return fr.getResultList().get(0);
    }

    private Path findPath(String name, Map<String, From> joins) {
        Path att = null;
        if (name.contains(".")) {
            String[] names = MyStringUtils.splitOnLastSeparator(name, '.');
            return findOrCreateJoinByPath(names[0], joins, false).get(names[1]);
        } else {
            return joins.get(ROOT).get(name);
        }
    }

    private From findOrCreateJoinByPath(String name, Map<String, From> joins, boolean fetch) {
        From from = joins.get(name);
        if (from == null) {
            String[] names = MyStringUtils.splitOnLastSeparator(name, '.');
            if (names == null) {
                from = joins.get(ROOT).join(name);
                from.alias(name + '_' + joins.size());
            } else {
                from = findOrCreateJoinByPath(names[0], joins, fetch);
                from = fetch ? (From) from.fetch(names[1]) : from.join(names[1]);
                from.alias(names[1] + '_' + joins.size());
            }
            joins.put(name, from);
        }
        return from;
    }


//----------------------------------------------------
    protected <R> Predicate getPredicate(Map<String, From> joins, CriteriaBuilder builder, SearchParameters sp) {
        From from;
        joins.get(ROOT).alias(ROOT);

        List<Predicate> predicates = new ArrayList<Predicate>(sp.getFilters().size());
        boolean repeat = false;
        FilterElement param = null;
        String name = null;
        for (Iterator<FilterElement> iterator = sp.getFilters().iterator(); true; ) {
            if (!repeat) {
                if (!iterator.hasNext())
                    break;
                param = iterator.next();
                name = param.getField();
            }
            repeat = false;
            Path att = null;
            try {
                att = findPath(name, joins);
            } catch (IllegalArgumentException e) {
                SelectElement se = sp.findSelectElementByAlias(name);
                if (se != null && !name.equals(se.getFieldName())) {
                    repeat = true;
                    name = se.getFieldName();
                } else {
                    log.error("Not found field " + name + " for restriction");
                }
                continue;
            }
            if (!att.getJavaType().isAssignableFrom(param.values.get(0).getValue().getClass())) {
                for (int ip=0; ip<param.values.size(); ip++) {
                    param.values.get(ip).convertTo(att.getJavaType());
                }
            }
            if (param.operator == null)
                param.operator = RestrictionType.EQ;
            predicates.add(param.operator.getCriterion(att, builder, param.values));
        }

      return builder.and(predicates.toArray(new Predicate[predicates.size()]));
    }

    protected void createOrder(SearchParameters sp, CriteriaBuilder builder, CriteriaQuery criteriaQuery, Map<String, From> joins) {
        if (!sp.hasOrders())
            return;
        List<Order> orders = new ArrayList<Order>(sp.getOrders().size());
        for (OrderBy orderBy : sp.getOrders()) {
            orders.add(orderBy.isOrderDesc() ?
                builder.desc(findPath(orderBy.getFieldName(), joins)) :
                builder.asc(findPath(orderBy.getFieldName(), joins)));
        }
        criteriaQuery.orderBy(orders);
    }

    /**
     * You may override this method to add a Predicate to the default find method.
     */
    protected <R> Predicate getExtraPredicate(Root<E> root, CriteriaQuery<R> query, CriteriaBuilder builder, E entity, SearchParameters sp) {
        return null;
    }

    /**
     * Save or update the passed entity E to the repository.
     * 
     * @param entity the entity to be saved or updated.
     */
    public void save(E entity) {
        Validate.notNull(entity, "The entity to save cannot be null element");

/*
        // creation with auto generated id
        if (!entity.isIdSet()) {
            getEntityManager().persist(entity);
            return;
        }
*/
      getEntityManager().persist(entity);
    }

    /**
     * Merge the state of the given entity into the current persistence context.
     */
    public E merge(E entity) {
        return getEntityManager().merge(entity);
    }

    /**
     * Delete the passed entity E from the repository.
     * 
     * @param entity the entity to be deleted.
     */
    public void delete(E entity) {
        if (getEntityManager().contains(entity)) {
            getEntityManager().remove(entity);
/*
        } else {
            // could be a delete on a transient instance
            E entityRef = getEntityManager().getReference(type, entity.getId());

            if (entityRef != null) {
                getEntityManager().remove(entityRef);
            } else {
                log.warn("Attempt to delete an instance that is not present in the database: " + entity.toString());
            }
*/
        }
    }

    // -----------------
    // Commons
    // -----------------

    /**
     * Set hints for 2d level cache.
     */
    protected void setCacheHints(TypedQuery<?> typedQuery, SearchParameters sp) {
        if (sp.isCacheable()) {
            typedQuery.setHint("org.hibernate.cacheable", true);

            if (sp.hasCacheRegion()) {
                typedQuery.setHint("org.hibernate.cacheRegion", sp.getCacheRegion());
            } else {
                typedQuery.setHint("org.hibernate.cacheRegion", cacheRegion);
            }
        }
    }

    // -----------------
    // Hibernate Search
    // -----------------
    protected String[] getIndexedFields() {
        return new String[0];
    }
}