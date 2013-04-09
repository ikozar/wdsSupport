package ru.ki;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import ru.ki.dao.support.FilterElement;
import ru.ki.dao.support.RestrictionType;
import ru.ki.dao.support.SearchParameters;
import ru.ki.dao.support.SelectElement;
import ru.ki.model.entity.PrSex;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/db-context.xml"})
//@Ignore
public class SessionTest extends DBTest {

    private static final String TERITORY_FIND = "Kolomna";

    @Autowired
    public SessionFactory sessionFactory;

    public static class EnployeeVO {
        public String fullName;
        public String nameSubdivisiuon;
        public String teritory;
        public String other;

        public EnployeeVO() {
        }
    }

    @Transactional
    @Test
    public void testQuery() {
        String from = "from Employee";
        StringBuilder select = new StringBuilder();

        SearchParameters sp = new SearchParameters();
        sp.parameter("fullName", "Persona11", RestrictionType.START)
            .parameter("prSex", PrSex.MAN)
            .parameter("subdivision.store.teritory.name", TERITORY_FIND);
        sp.select("fullName")
                .select("subdivision.name", "nameSubdivisiuon")
                .select("subdivision.store.teritory.name", "teritory");

        List<PredicateElement> predicates = new ArrayList<PredicateElement>(sp.getParameters().size());
        for (Iterator<FilterElement> iterator = sp.getParameters().iterator(); iterator.hasNext(); ) {
            FilterElement param = iterator.next();
            RestrictionHibType operator = RestrictionHibType.valueOf(param.getOperator().name());
            predicates.add(operator.getCriterion(param.getFieldName(), param.getValues()));
        }

        StringBuilder sb = new StringBuilder();
        for (PredicateElement pe : predicates) {
            if (sb.length() > 0)
                sb.append(" and ");
            sb.append(pe.getPredicate());
        }

        if (sp.isCustomSelect()) {
            for (SelectElement se : sp.getSelectElements()) {
                if (select.length() > 0)
                    select.append(", ");
                select.append(se.getFieldName()).append(" as ")
                    .append(se.getAlias());
            }
            select.insert(0, "select ").append(' ');
        }
        select.append(from);
        if (sb.length() > 0) {
            select.append(" where ").append(sb.toString());
        }

        Query q = sessionFactory.getCurrentSession().createQuery(select.toString());
        for (PredicateElement pe : predicates) {
            q.setParameter(pe.getProperty(), pe.getValue());
        }
//        q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        q.setResultTransformer(Transformers.aliasToBean(EnployeeVO.class));
        List l = q.list();

        q = sessionFactory.getCurrentSession().createQuery("select count(*) from Employee");
        q.uniqueResult();

        System.out.println(l);
    }

}
