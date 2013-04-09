package ru.ki;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import ru.ki.dao.support.mapper.MapperHandler;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.IOException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/db-context.xml"})
//@Ignore
public class DBTest {

    @PersistenceContext
    public EntityManager entityManager;

    @Autowired
    public JdbcTemplate jdbcTemplate;

    @Autowired
    MapperHandler mapperHandler;

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

        System.out.println("--- INIT DB");
        initDB = true;
    }

}
