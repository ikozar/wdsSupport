package ru.ki;

import org.dozer.DozerBeanMapper;
import org.dozer.loader.api.BeanMappingBuilder;
import org.junit.Ignore;
import org.junit.Test;

import javax.persistence.Tuple;
import javax.persistence.criteria.*;
import javax.persistence.metamodel.ManagedType;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import ru.ki.dao.EmployeeDao;
import ru.ki.dao.support.FindResult;
import ru.ki.dao.support.RestrictionType;
import ru.ki.dao.support.SearchParameters;
import ru.ki.model.entity.*;
import ru.ki.model.ws.EmployeeVO;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class DozerTest extends DBTest {

    @Autowired
    EmployeeDao employeeDao;

    private Class type;
    SearchParameters sp;

    private static final String TERITORY_FIND = "Kolomna";

//    @After
    public Object checkResult() {
        FindResult findResult = employeeDao.find(sp, type);
        assertEquals(findResult.getCount().longValue(), 3L);
        assertTrue(type.isAssignableFrom(findResult.getResultList().get(0).getClass()));
        System.out.println("--- size: " + findResult.getCount() + ", type: " + findResult.getResultList().get(0).getClass().getSimpleName());
        return findResult.getResultList().get(0);
    }

    private SearchParameters preparePersonalFilter(Class type) {
        this.type = type;
        sp = new SearchParameters();
        sp.parameter("fullName", "Persona11", RestrictionType.START);
        sp.parameter("prSex", PrSex.MAN);
        if (type == Employee.class) {
            sp.parameter("subdivision.store.teritory.name", TERITORY_FIND);
        } else if (type == EmployeeVO.class) {
            sp.parameter("teritory", TERITORY_FIND);
//            sp.parameter("subdivision.store.teritory.naimTer", TERITORY_FIND);
        } else {
            sp.parameter("subdivision.store.teritory.name", TERITORY_FIND);
        }
        return sp;
    }

    @Test
    public void testFindTuple() {
        SearchParameters sp = preparePersonalFilter(Tuple.class);
        sp.select("fullName")
            .select("subdivision.name", "nameSubdivisiuon")
            .select("subdivision.store.teritory.name", "teritory");
        assertEquals(((Tuple) checkResult()).get("teritory"), TERITORY_FIND);
    }

    @Test
    public void testFindVO() {
        SearchParameters sp = preparePersonalFilter(EmployeeVO.class);
        sp.setSelectElements(mapperHandler.getSelectionList(EmployeeVO.class, employeeDao.getJavaType()));
        assertEquals(((EmployeeVO) checkResult()).getTeritory(), TERITORY_FIND);
    }

    @Test
    @Transactional
//    @Ignore
    public void testFindEntity() {
        SearchParameters sp = preparePersonalFilter(Employee.class);
        assertEquals(((Employee) checkResult()).getSubdivision().getStore().getTeritory().getName(),
                TERITORY_FIND);
    }

    @Test
    public void testFindQBEVO() {
        EmployeeVO employeeVO = new EmployeeVO();
        employeeVO.setPrSex(PrSex.MAN.name());
        employeeVO.setTeritory(TERITORY_FIND);
        Map<String, Object> msp = mapperHandler.map(employeeVO, Map.class);
        for (Iterator<Map.Entry<String, Object>> iterator = msp.entrySet().iterator(); iterator.hasNext(); ) {
            if (iterator.next().getValue() == null)
                iterator.remove();
        }
        SearchParameters sp = new SearchParameters();
        sp.setParameters(msp);
        sp.setSelectElements(mapperHandler.getSelectionList(EmployeeVO.class, employeeDao.getJavaType()));
        FindResult findResult = employeeDao.find(sp, EmployeeVO.class);
        System.out.println("--- size: " + findResult.getCount() + ", type: " + findResult.getResultList().get(0).getClass().getSimpleName());
    }

    @Test
    @Ignore
    public void testQueryBuilder() throws NoSuchFieldException, IllegalAccessException,
            InstantiationException, NoSuchMethodException, InvocationTargetException {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        ManagedType mt = entityManager.getMetamodel().entity(Employee.class);
        //    CriteriaQuery<EmployeeVO> c = cb.createQuery(EmployeeVO.class);

//        CriteriaQuery cbQuery = cb.createQuery();
        CriteriaQuery<Employee> cbQuery = cb.createQuery(Employee.class);
        Root<Employee> root = cbQuery.from(Employee.class);
        Join<Employee, Subdivision> subdivision = root.join("subdivision");
        Join<Subdivision, Store> store = subdivision.join("store");
        Join<Store, Teritory> teritory = store.join("teritory");
        cbQuery.where(cb.equal(teritory.get("naimTer"), TERITORY_FIND));

//        cbQuery.select(cb.count(root));
        List l = entityManager.createQuery(cbQuery).getResultList();
        System.out.println("==== " + l.get(0));

        CriteriaQuery<Tuple> criteriaQuery = cb.createTupleQuery();
//        root = criteriaQuery.from(Employee.class);
        root = criteriaQuery.from(Employee.class);
        subdivision = root.join("subdivision");
        store = subdivision.join("store");
        teritory = store.join("teritory");
        List<Selection<?>> selectionList = new ArrayList<Selection<?>>();
        selectionList.add(root.get("fioPers").alias("fioPers"));
        selectionList.add(root.get("subdivision").alias("subdivision"));
        criteriaQuery.multiselect(selectionList);
        criteriaQuery.where(cbQuery.getRestriction());
        List<Tuple> l1 = entityManager.createQuery(criteriaQuery).getResultList();
        System.out.println("----" + l1);

        DozerBeanMapper dozer = new DozerBeanMapper();
        BeanMappingBuilder foo = new BeanMappingBuilder() {
            @Override
            protected void configure() {
                mapping(EmployeeVO.class, Tuple.class/*, TypeMappingOptions.oneWay()*/)
                        .fields("fioPers", "this")
                        .fields("subdivisionName", field("this").mapKey("subdivision.name"))
                ;
            }
        };
        dozer.addMapping(foo);

        EmployeeVO c1 = dozer.map(l1.get(0), EmployeeVO.class);
        System.out.printf("--- " + c1);
/*
        foo = new BeanMappingBuilder() {
            @Override
            protected void configure() {
                mapping(Tuple.class, EmployeeVO.class, TypeMappingOptions.oneWay()).fields(
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
        //    EmployeeVO c0 = dozer.map(map, EmployeeVO.class);
        //    l.get(0).getElements().get(1);
*/
    }
}
