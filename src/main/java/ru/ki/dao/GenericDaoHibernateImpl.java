package ru.ki.dao;


import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

//import genericdao.finder.FinderArgumentTypeFactory;
//import genericdao.finder.FinderExecutor;
//import genericdao.finder.FinderNamingStrategy;
//import genericdao.finder.impl.SimpleFinderArgumentTypeFactory;
//import genericdao.finder.impl.SimpleFinderNamingStrategy;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.type.Type;
//import org.springframework.orm.hibernate3.SessionFactoryUtils;

/**
 * Hibernate implementation of GenericDao
 * A typesafe implementation of CRUD and finder methods based on Hibernate and Spring AOP
 * The finders are implemented through the executeFinder method. Normally called by the FinderIntroductionInterceptor
 */
public class GenericDaoHibernateImpl<T, PK extends Serializable> implements IGenericDao<T, PK>
{
  private SessionFactory sessionFactory;
//  private FinderNamingStrategy namingStrategy = new SimpleFinderNamingStrategy(); // Default. Can override in config
//  private FinderArgumentTypeFactory argumentTypeFactory = new SimpleFinderArgumentTypeFactory(); // Default. Can override in config

  private Class<T> type;

  public GenericDaoHibernateImpl(Class<T> type)
  {
    this.type = type;
  }

  public PK create(T o)
  {
    return (PK) getSession().save(o);
  }

  public T read(PK id)
  {
    return (T) getSession().get(type, id);
  }

  public void update(T o)
  {
    getSession().update(o);
  }

  public void delete(T o)
  {
    getSession().delete(o);
  }

  public List<T> executeFinder(Method method, final Object[] queryArgs)
  {
//    final Query namedQuery = prepareQuery(method, queryArgs);
//    return (List<T>) namedQuery.list();
    return null;
  }

  public Iterator<T> iterateFinder(Method method, final Object[] queryArgs)
  {
//    final Query namedQuery = prepareQuery(method, queryArgs);
//    return (Iterator<T>) namedQuery.iterate();
    return null;
  }

  public Session getSession()
  {
    return sessionFactory.getCurrentSession();
  }

  public void setSessionFactory(SessionFactory sessionFactory)
  {
    this.sessionFactory = sessionFactory;
  }

}
