package ru.ki.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate3.Hibernate3Module;
import org.springframework.stereotype.Repository;

/**
 * @author ikozar
 * date    28.06.13
 */
@Repository
public class CustomJacksonObjectMapper extends ObjectMapper {

    public CustomJacksonObjectMapper() {
        registerModule(new Hibernate3Module()
            .enable(Hibernate3Module.Feature.SERIALIZE_IDENTIFIER_FOR_LAZY_NOT_LOADED_OBJECTS));
    }

}
