package ru.ki.dao.support.dozer;

import org.dozer.DozerBeanMapper;
import org.dozer.MappingProcessor;
import org.dozer.classmap.ClassMap;
import org.dozer.classmap.ClassMappings;
import org.dozer.fieldmap.FieldMap;
import org.dozer.loader.api.BeanMappingBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.Tuple;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author  ikozar
 * date 06.02.13
 */
public class DozerBeanMapperExt extends DozerBeanMapper {
    private final Logger logger = LoggerFactory.getLogger(DozerBeanMapperExt.class);

    private Method getClassMapMethod;

    public DozerBeanMapperExt() {
        super();
        init();
    }

    public DozerBeanMapperExt(List<String> mappingFiles) {
        super(mappingFiles);
        init();
    }

    private void init() {
        try {
            Method getClassMapMethod = MappingProcessor.class.getDeclaredMethod("getClassMap", new Class[] {Class.class, Class.class, String.class});
            getClassMapMethod.setAccessible(true);

            org.dozer.Mapper mapper = getMappingProcessor();
            Field field = MappingProcessor.class.getDeclaredField("classMappings");
            field.setAccessible(true);
            ClassMappings mapping = (ClassMappings) field.get(mapper);

            field = ClassMappings.class.getDeclaredField("classMappings");
            field.setAccessible(true);
            Map<String, ClassMap> classMappings = (Map<String, ClassMap>) field.get(mapping);

            this.getClassMapMethod = getClassMapMethod;

        } catch (NoSuchMethodException e) {
            logger.error("NoSuchMethodException " + e.getMessage());
        } catch (IllegalAccessException e) {
            logger.error("IllegalAccessException " + e.getMessage());
        } catch (NoSuchFieldException e) {
            logger.error("NoSuchFieldException " + e.getMessage());
        }
    }

    public ClassMap getClassMap(final Class<?> srcClass, Class<?> destClass, String mapId) {
        try {
            if (getClassMapMethod != null) {
                ClassMap classMap = (ClassMap) getClassMapMethod.invoke((MappingProcessor) getMappingProcessor(), new Object[] {srcClass, destClass, mapId});
                if (classMap == null && Tuple.class.isAssignableFrom(srcClass)) {
                    addMapping(new BeanMappingBuilder() {
                        @Override
                        protected void configure() {
                            mapping(srcClass, Tuple.class);
                        }}
                    );
                    classMap = (ClassMap) getClassMapMethod.invoke((MappingProcessor) getMappingProcessor(), new Object[] {srcClass, destClass, mapId});
                }
                return classMap;
            }
        } catch (IllegalAccessException e) {
            logger.error("IllegalAccessException " + e.getMessage());
        } catch (InvocationTargetException e) {
            logger.error("InvocationTargetException " + e.getMessage());
        }
        return null;
    }

    public List<FieldMap> getFieldsMap(Class<?> srcClass, Class<?> destClass, String mapId) {
        ClassMap classMap = getClassMap(srcClass, destClass, mapId);
        if (classMap != null) {
            return  classMap.getFieldMaps();
        }
        return Collections.EMPTY_LIST;
    }

}
