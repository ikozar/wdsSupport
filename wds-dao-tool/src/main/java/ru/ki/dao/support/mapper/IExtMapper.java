package ru.ki.dao.support.mapper;

import ru.ki.model.query.SelectElement;

import java.util.List;

/**
 * @author ikozar
 *         date     05.04.13
 */
public interface IExtMapper {
    boolean checkMap(Class<?> srcClass, Class<?> destClass);

    @SuppressWarnings("unchecked")
    List<SelectElement> getSelectionList(Class<?> classVO, Class<?> classJPA);

    <E> E map(Object obj, Class<E> destType);
}
