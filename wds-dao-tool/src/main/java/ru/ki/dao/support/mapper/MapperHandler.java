package ru.ki.dao.support.mapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.ki.model.query.SelectElement;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * @author ikozar
 * Date 06.02.13
 */
public class MapperHandler {
    private final Logger logger = LoggerFactory.getLogger(MapperHandler.class);

    private IExtMapper dozer;

    private List<String> mappingFiles;

    public void setMappingFiles(List<String> mappingFiles) {
        this.mappingFiles = mappingFiles;
    }

    enum MapperImpl {
        ORIKA("ma.glasnost.orika.MapperFactory") {
            @Override
            public IExtMapper createMapper(List<String> mappingFiles) {
                return new OrikaMapper(mappingFiles);
            }
        },
        DOZER("org.dozer.DozerBeanMapper") {
            @Override
            public IExtMapper createMapper(List<String> mappingFiles) {
                return new DozerBeanMapperExt(mappingFiles);
            }
        };
        String className;

        MapperImpl(String className) {
            this.className = className;
        }

        abstract public IExtMapper createMapper(List<String> mappingFiles);
    }

    @PostConstruct
    public void init() {
        for (MapperImpl mapper : MapperImpl.values()) {
            try {
                Class.forName(mapper.className);
            } catch (ClassNotFoundException ignore) {
                continue;
            }
            dozer = mapper.createMapper(mappingFiles);
            break;
        }
    }

    @SuppressWarnings("unchecked")
    public List<SelectElement> getSelectionList(Class<?> classVO, Class<?> classJPA) {
        return dozer.getSelectionList(classVO, classJPA);
    }

    public <E> E map(Object obj, Class<E> destType) {
        return dozer.map(obj, destType);
    }

    public boolean checkMap(Class<?> srcClass, Class<?> destClass) {
        return dozer.checkMap(srcClass, destClass);
    }
}
