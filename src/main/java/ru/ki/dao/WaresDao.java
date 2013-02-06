/*
 * (c) Copyright 2005-2013 JAXIO, http://www.jaxio.com
 * Source code generated by Celerio, a Jaxio product
 * Want to use Celerio within your company? email us at info@jaxio.com
 * Follow us on twitter: @springfuse
 * Template pack-backend-jpa:src/main/java/project/dao/DAOImpl.e.vm.java
 */
package ru.ki.dao;

import org.springframework.stereotype.Component;
import ru.ki.model.entity.Wares;
import ru.ki.dao.support.GenericDao;

@Component
public class WaresDao extends GenericDao<Wares, Integer> {
    public WaresDao() {
        super(Wares.class);
    }
}