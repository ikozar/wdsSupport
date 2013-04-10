package ru.ki.dao.support.mapper;

import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.loader.ConfigurationLoader;
import ma.glasnost.orika.metadata.ClassMap;
import ma.glasnost.orika.metadata.FieldMap;
import ma.glasnost.orika.metadata.MapperKey;
import ma.glasnost.orika.metadata.TypeFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.ki.dao.support.SelectElement;

import javax.persistence.Tuple;
import java.util.*;

/**
 * @author  ikozar
 * date 06.02.13
 */
public class OrikaMapper implements IExtMapper {
    private final Logger logger = LoggerFactory.getLogger(OrikaMapper.class);

    private MapperFactory factory;
    private ConfigurationLoader loader;
    private MapperFacade orika;

    public OrikaMapper() {
        factory = new OricaTupleMapperFactory.Builder().build();
        loader = new ConfigurationLoader();
    }

    public OrikaMapper(List<String> mappingFiles) {
        this();
        load(mappingFiles);
    }

    private void load(List<String> mappingFiles) {
        if (mappingFiles != null) {
            for (String file : mappingFiles) {
                logger.debug("======================== " + getClass().getClassLoader().getResource(".").getPath());
                logger.debug("Load config: " + file);
                loader.load(factory, file);
            }
        }
        orika = factory.getMapperFacade();
    }

    @Override
    public boolean checkMap(Class<?> srcClass, Class<?> destClass) {
        if (Tuple.class.isAssignableFrom(srcClass)) {
            return factory.existsRegisteredMapper(TypeFactory.valueOf(srcClass), TypeFactory.valueOf(destClass), false);
        }
        return true;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<SelectElement> getSelectionList(Class<?> classVO, Class<?> classJPA) {
        MapperKey mapperKey = new MapperKey(TypeFactory.valueOf(classJPA), TypeFactory.valueOf(classVO));
        ClassMap classMap = factory.getClassMap(mapperKey);
        if (classMap == null)
            return Collections.EMPTY_LIST;
        Set<FieldMap> fieldsMap = classMap.getFieldsMapping();
        if (fieldsMap.size() > 0) {
            List<SelectElement> selectElementList = new ArrayList<SelectElement>(fieldsMap.size());
            for (FieldMap fieldMap : fieldsMap) {
                selectElementList.add(new SelectElement(fieldMap.getDestination().getExpression(),
                        fieldMap.getSource().getName()));
            }
            return selectElementList;
        }
        return Collections.emptyList();
    }

    @Override
    public <E> E map(Object obj, Class<E> destType) {
        return orika.map(obj, destType);
    }
}
