package ru.ki.service;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import ru.ki.model.query.GenericDao;
import ru.ki.service.exception.ExceptionCode;
import ru.ki.service.exception.ExceptionWithCode;

import java.io.IOException;

/**
 * @author ikozar
 * date    23.06.13
 */
public class AbstractService {

    @Autowired
    protected CustomJacksonObjectMapper customJacksonObjectMapper;

    @Transactional
    public Object getEntity(String clazz, String pkg, Long id) {
        return getDao(clazz, pkg).get(id);
    }

    protected Object fillEntity(Object entity, String data) {
        if (data == null) {
            throw new ExceptionWithCode(ExceptionCode.BAD_PARAMS, "Не найден параметр data.");
        }
        try {
            customJacksonObjectMapper.reader().withValueToUpdate(entity).readValue(data);
        } catch (IOException e) {
            throw new ExceptionWithCode(ExceptionCode.BAD_PARAMS, "Ошибка разбора параметра. " + ExceptionUtils.getRootCauseMessage(e));
        } catch (Exception e) {
            throw new ExceptionWithCode(ExceptionCode.BAD_PARAMS, "Ошибка получения параметра. " + ExceptionUtils.getRootCauseMessage(e));
        }
        return entity;
    }

    protected Object mergeEntity(GenericDao dao, Long id, String data) {
        Object entity = dao.get(id);
        if (entity == null) {
            throw new ExceptionWithCode(ExceptionCode.BAD_PARAMS, "Не найдена сущность по ИД=" + id);
        }
        entity = fillEntity(entity, data);
        return entity;
    }

    @Transactional
    public Object updateEntity(String clazz, String pkg, Long id, String data) {
        GenericDao dao = getDao(clazz, pkg);
        Object entity = mergeEntity(dao, id, data);
        dao.save(entity);
        return entity;
    }

    @Transactional
    public Object createEntity(Class clazz, String data) {
        try {
            return customJacksonObjectMapper.readValue(data, clazz);
        } catch (IOException e) {
            throw new ExceptionWithCode(ExceptionCode.BAD_PARAMS, "Ошибка разбора параметра");
        }
    }

    @Transactional
    public Object addEntity(String clazz, String pkg, String data) {
        GenericDao dao = getDao(clazz, pkg);
        Object entity = createEntity(dao.getJavaType(), data);
        dao.save(entity);
        return entity;
    }

    @Transactional
    public void deleteEntity(String clazz, String pkg, Long id) {
        getDao(clazz, pkg).delete(id);
    }

    protected GenericDao getDao(String clazz, String pkg) {
        GenericDao dao = GenericDao.getDao(clazz);
        if (dao == null) {
            throw new ExceptionWithCode(ExceptionCode.BAD_URL, "Не найден ДАО для класса", clazz);
        }
        if (!StringUtils.substringAfterLast(dao.getJavaType().getPackage().getName(), ".")
                .equals((pkg))) {
            throw new ExceptionWithCode(ExceptionCode.BAD_URL, "Не корректное имя пакета класса", clazz);
        }
        return dao;
    }

}
