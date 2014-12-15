package ru.ki.dao;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.IOException;

@Component
public class DBLoad {

    @PersistenceContext
    public EntityManager entityManager;

    @Autowired
    public JdbcTemplate jdbcTemplate;

    @PostConstruct
    public void init() throws IOException {
        initDB();
    }

    @Transactional
    public void initDB() throws IOException {
        String s = Resources.toString(Resources.getResource("data.sql"), Charsets.UTF_8);
        try {
            jdbcTemplate.execute(s.substring(1));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
