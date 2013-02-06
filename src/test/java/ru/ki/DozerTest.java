package ru.ki;

import org.dozer.DozerBeanMapper;
import org.dozer.DozerConverter;
import org.dozer.classmap.ClassMap;
import org.dozer.classmap.ClassMapBuilder;
import org.dozer.loader.DozerBuilder;
import org.dozer.loader.api.BeanMappingBuilder;
import org.junit.After;
import org.junit.Ignore;
import org.junit.Test;

import javax.persistence.Tuple;
import javax.persistence.criteria.*;
import javax.persistence.metamodel.ManagedType;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import ru.ki.dao.PersonalDao;
import ru.ki.dao.support.FindResult;
import ru.ki.dao.support.RestrictionType;
import ru.ki.dao.support.SearchParameters;
import ru.ki.dao.support.dozer.DozerSupport;
import ru.ki.model.entity.*;
import ru.ki.model.ws.PersonalVO;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class DozerTest extends DBTest {

    @Autowired
    PersonalDao personalDao;

    private Class type;
    SearchParameters sp;

    @After
    public void check() {
        FindResult findResult = personalDao.find(sp, type);
        assertEquals(findResult.getCount().longValue(), 3L);
        assertTrue(type.isAssignableFrom(findResult.getResultList().get(0).getClass()));
        System.out.println("--- size: " + findResult.getCount() + ", type: " + findResult.getResultList().get(0).getClass().getSimpleName());
    }

    private SearchParameters preparePersonalFilter(Class type) {
        this.type = type;
        sp = new SearchParameters();
        sp.parameter("fioPers", "Persona11", RestrictionType.START);
        sp.parameter("prSex", PrSex.MAN);
        if (type == Personal.class) {
            sp.parameter("subdivision.store.teritory.naimTer", "Мытищи");
        } else if (type == PersonalVO.class) {
//            sp.parameter("teritory", "Мытищи");
            sp.parameter("subdivision.store.teritory.naimTer", "Мытищи");
        } else {
            sp.parameter("subdivision.store.teritory.naimTer", "Мытищи");
        }
        return sp;
    }

    @Test
    public void testFindTuple() {
        SearchParameters sp = preparePersonalFilter(Tuple.class);
        sp.select("fioPers").select("subdivision.naimSubdiv", "naimSubdiv");
    }

    @Test
    public void testFindVO() {
        SearchParameters sp = preparePersonalFilter(PersonalVO.class);
        sp.setSelectElements(dozerSupport.getSelectionList(PersonalVO.class, personalDao.getJavaType()));
    }

    @Test
//    @Ignore
    public void testFindEntity() {
        SearchParameters sp = preparePersonalFilter(Personal.class);
    }

    @Test
    @Ignore
    public void testQueryBuilder() throws NoSuchFieldException, IllegalAccessException,
            InstantiationException, NoSuchMethodException, InvocationTargetException {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        ManagedType mt = entityManager.getMetamodel().entity(Personal.class);
        //    CriteriaQuery<PersonalVO> c = cb.createQuery(PersonalVO.class);

//        CriteriaQuery cbQuery = cb.createQuery();
        CriteriaQuery<Personal> cbQuery = cb.createQuery(Personal.class);
        Root<Personal> root = cbQuery.from(Personal.class);
        Join<Personal, Subdivision> subdivision = root.join("subdivision");
        Join<Subdivision, Store> store = subdivision.join("store");
        Join<Store, Teritory> teritory = store.join("teritory");
        cbQuery.where(cb.equal(teritory.get("naimTer"), "Мытищи"));

//        cbQuery.select(cb.count(root));
        List l = entityManager.createQuery(cbQuery).getResultList();
        System.out.println("==== " + l.get(0));

        CriteriaQuery<Tuple> criteriaQuery = cb.createTupleQuery();
//        root = criteriaQuery.from(Personal.class);
        root = criteriaQuery.from(Personal.class);
        subdivision = root.join("subdivision");
        store = subdivision.join("store");
        teritory = store.join("teritory");
        List<Selection<?>> selectionList = new ArrayList<Selection<?>>();
        selectionList.add(root.get("fioPers").alias("fioPers"));
//        selectionList.add(subdivision.get("naimSubdiv").alias("naimSubdiv"));
        selectionList.add(root.get("subdivision").alias("subdivision"));
        criteriaQuery.multiselect(selectionList);
//        criteriaQuery.multiselect(root.get("fioPers").alias("fioPers"),
//                subdivision.get("naimSubdiv").alias("naimSubdiv"));
        //    List<Object[]> l = entityManager.createQuery(criteriaQuery).getResultList();
        criteriaQuery.where(cbQuery.getRestriction());
        List<Tuple> l1 = entityManager.createQuery(criteriaQuery).getResultList();
        System.out.println("----" + l1);

        DozerBeanMapper dozer = new DozerBeanMapper();
        BeanMappingBuilder foo = new BeanMappingBuilder() {
            @Override
            protected void configure() {
                mapping(PersonalVO.class, Tuple.class/*, TypeMappingOptions.oneWay()*/)
                        .fields("fioPers", "this")
                        .fields("subdivisionName", field("this").mapKey("subdivision.naimSubdiv"))
                ;
            }
        };
        dozer.addMapping(foo);

        PersonalVO c1 = dozer.map(l1.get(0), PersonalVO.class);
        System.out.printf("--- " + c1);
/*
        foo = new BeanMappingBuilder() {
            @Override
            protected void configure() {
                mapping(Tuple.class, PersonalVO.class, TypeMappingOptions.oneWay()).fields(
                        new FieldDefinition("this"), "createdDt").fields(new FieldDefinition("this"),
                        "titleDocument");
            }
        };
        dozer.setMappings(Collections.singletonList(foo));
        //    ((ArrayList) ClassMapBuilder.buildTimeGenerators).add(new TestCustomerStopListDAO.TupleMappingGenerator())
        //    Object o = new ClassMapBuilder.createDefaultClassMap().;
        Field f = ClassMapBuilder.class.getDeclaredField("buildTimeGenerators");
        f.setAccessible(true);
        Constructor con = ClassMapBuilder.class.getDeclaredConstructor(null);
        con.setAccessible(true);
        ClassMapBuilder c = (ClassMapBuilder) con.newInstance();
        //    ClassMapBuilder.class.privateGetDeclaredConstructors(false)[0].setAccessible(true);
        //    ClassMapBuilder.class.getDeclaredMethod("newInstance", new Class[0]).setAccessible(true);
        //    ClassMapBuilder c = ClassMapBuilder.createDefaultClassMap(new Configuration(), Object.class, Object.class);
        ((ArrayList) f.get(c)).add(new DozerTest.TupleMappingGenerator());
        //    ((ArrayList) ClassMapBuilder.buildTimeGenerators).add(new TestCustomerStopListDAO.TupleMappingGenerator());

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("createdDt", new Date());
        map.put("titleDocument", "titleDocument");
        //    PersonalVO c0 = dozer.map(map, PersonalVO.class);
        //    l.get(0).getElements().get(1);
*/
    }
}
