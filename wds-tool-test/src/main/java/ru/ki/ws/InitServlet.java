package ru.ki.ws;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.HttpRequestHandler;
import org.springframework.web.context.support.XmlWebApplicationContext;
import org.springframework.web.servlet.HttpServletBean;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author ikozar
 * date    09.04.13
 */
@Component
public class InitServlet extends HttpServletBean {

    @PersistenceContext
    public EntityManager entityManager;

    @Autowired
    public JdbcTemplate jdbcTemplate;

//    @PostConstruct
    public void prepare() throws ServletException {
        try {
//
            String s = Resources.toString(Resources.getResource("data.sql"), Charsets.UTF_8);
            jdbcTemplate.execute(s.substring(1));
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("--- INIT DB");
    }

    @Override
    protected void initServletBean() throws ServletException {
        jdbcTemplate = (JdbcTemplate) ((XmlWebApplicationContext) getServletContext().getAttribute("org.springframework.web.context.WebApplicationContext.ROOT")).getBean("jdbcTemplate");
        super.initServletBean();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        prepare();
        resp.getWriter().print("DONE");
    }

}
