/*
 * (c) Copyright 2005-2013 JAXIO, http://www.jaxio.com
 * Source code generated by Celerio, a Jaxio product
 * Want to use Celerio within your company? email us at info@jaxio.com
 * Follow us on twitter: @springfuse
 * Template pack-backend-jpa:src/main/java/project/dao/DAOImpl.e.vm.java
 */
package ru.ki.dao;

import org.springframework.stereotype.Component;
import ru.ki.dao.support.GenericDao;
import ru.ki.entity.Realizations;

/**
 * JPA 2 Data Access Object for {@link Realizations}.
 * Note: do not use @Transactional in the DAO layer.
 */

@Component
public class RealizationsDao extends GenericDao<Realizations, Integer> {
    public RealizationsDao() {
        super(Realizations.class);
    }
}