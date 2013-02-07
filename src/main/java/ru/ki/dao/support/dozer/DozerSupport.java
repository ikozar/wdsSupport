package ru.ki.dao.support.dozer;

import com.google.common.collect.ImmutableList;
import org.apache.commons.lang3.StringUtils;
import org.dozer.DozerBeanMapper;
import org.dozer.fieldmap.FieldMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.ki.dao.support.SelectElement;

import javax.annotation.PostConstruct;
import javax.persistence.Tuple;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author ikozar
 * Date 06.02.13
 */
@Service
public class DozerSupport {
    private final Logger logger = LoggerFactory.getLogger(DozerSupport.class);

    @Autowired(required = false)
    private DozerBeanMapper dozer;

    public DozerBeanMapper getDozer() {
        return dozer;
    }

    public void setDozer(DozerBeanMapper dozer) {
        this.dozer = dozer;
    }

    @PostConstruct
    public void init() {
//        if (dozer == null) {
//            dozer = new DozerBeanMapperExt();
//        }
    }

    private void checkExt() {
        if (!(dozer instanceof DozerBeanMapperExt)) {
            throw new DozerSupportException("Bean DozerBeanMapper not instantiated");
        }
    }

    @SuppressWarnings("unchecked")
    public List<SelectElement> getSelectionList(Class<?> classVO, Class<?> classJPA) {
        checkExt();
        List<FieldMap> fieldsMap = ((DozerBeanMapperExt) dozer).getFieldsMap(classVO, classJPA, StringUtils.EMPTY);
        if (fieldsMap.size() > 0) {
            List<SelectElement> selectElementList = new ArrayList<SelectElement>(fieldsMap.size());
            for (FieldMap fieldMap : fieldsMap) {
                selectElementList.add(new SelectElement(fieldMap.getDestFieldName(),
                        fieldMap.getSrcFieldName()));
            }
            return selectElementList;
        }
        return Collections.emptyList();
    }

    public boolean checkMap(Class<?> srcClass, Class<?> destClass) {
        if (Tuple.class.isAssignableFrom(srcClass)) {
            checkExt();
        } else {
            return true;
        }
        return ((DozerBeanMapperExt) dozer).getClassMap(srcClass, destClass, StringUtils.EMPTY) != null;
    }

    public <E> E map(Object obj, Class<E> destType) {
        return dozer.map(obj, destType);
    }
}
