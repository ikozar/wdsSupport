package ru.ki;

import com.google.common.base.Charsets;
import com.google.common.collect.ImmutableList;
import com.google.common.io.Resources;
import org.dozer.CustomFieldMapper;
import org.dozer.DozerBeanMapper;
import org.dozer.DozerConverter;
import org.dozer.classmap.ClassMap;
import org.dozer.classmap.ClassMapBuilder;
import org.dozer.fieldmap.FieldMap;
import org.dozer.loader.DozerBuilder;
import org.dozer.loader.api.BeanMappingBuilder;
import org.dozer.loader.api.FieldDefinition;
import org.dozer.loader.api.TypeMappingOptions;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import ru.ki.dao.support.dozer.DozerBeanMapperExt;
import ru.ki.dao.support.dozer.DozerSupport;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Tuple;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.Charset;
import java.sql.Connection;
import java.sql.Statement;
import java.util.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/db-context.xml"})
//@Ignore
public class DBTest {

    @PersistenceContext
    public EntityManager entityManager;

    @Autowired
    public JdbcTemplate jdbcTemplate;

    @Autowired
    DozerSupport dozerSupport;

    private static boolean initDB = false;

    @Before
//  @Test
    @Transactional
    public void initDB() throws IOException {
        if (initDB) return;
        String s = Resources.toString(Resources.getResource("data.sql"), Charsets.UTF_8);
        try {
//      entityManager.createNativeQuery(s).executeUpdate();
//      Connection connection = ((org.hibernate.ejb.AbstractEntityManagerImpl) entityManager).getSession().connection();
            jdbcTemplate.execute(s.substring(1));
        } catch (Exception e) {
            e.printStackTrace();
        }

        dozerSupport.setDozer(new DozerBeanMapperExt(ImmutableList.of("dozer-mappings.xml")));

        System.out.println("--- INIT DB");
        initDB = true;
    }

}
