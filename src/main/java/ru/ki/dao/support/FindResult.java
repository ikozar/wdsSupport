package ru.ki.dao.support;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.ki.dao.support.mapper.MapperHandler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FindResult<E> {
    private final Logger logger = LoggerFactory.getLogger(FindResult.class);

    private Long count = 0L;
    private List<E> resultList;
    private MapperHandler mapperHandler;

    public FindResult(MapperHandler mapperHandler) {
        this.mapperHandler = mapperHandler;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        if (count == null) {
            logger.warn("findCount returned null!");
        } else {
            this.count = count;
        }
    }

    public int getCountResult() {
        return resultList.size();
    }

    public List<E> getResultList() {
        return resultList;
    }

    public void setResultList(List resultList, Class<E> returnType) {
        if (resultList != null && !resultList.isEmpty()) {
            count = (long) resultList.size();
            if (returnType.isAssignableFrom(resultList.get(0).getClass())
                 || !mapperHandler.checkMap(resultList.get(0).getClass(), returnType)) {
                this.resultList = resultList;
            } else {
                this.resultList = new ArrayList<E>(resultList.size());
                for (Object result : resultList) {
                    this.resultList.add(mapperHandler.map(result, returnType));
                }
            }
        } else {
            this.resultList = Collections.emptyList();
        }
    }
}
