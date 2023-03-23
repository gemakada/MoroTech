/*
 * @(#)JacksonUtils.java
 */
package com.moro.books.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JacksonUtils {

    /**
     * Represents the map for caching object mapper for every used class.
     */
    private static final Map<Class, ObjectMapper> OBJECT_MAPPER_MAP = new HashMap<>();

    /**
     * Default constructor.
     */
    public JacksonUtils() {
        super();
    }

    /**
     * This method implements conversion of given object into JSON representation.
     *
     * @param object the object to convert
     * @return JSON representation of object if possible, otherwise empty string
     */
    public String toJson(final Object object) {
        String json;
        if (object == null) {
            json = "";
        } else {
            ObjectMapper objectMapper = getObjectMapper(object.getClass());
            try {
                json = objectMapper.writeValueAsString(object);
            } catch (JsonProcessingException jpe) {
                Logger.getLogger(JacksonUtils.class.getName()).log(Level.SEVERE, null, jpe);
                json = "";
            }
        }
        return json;
    }

    /**
     * This method will provide an instance of {@link ObjectMapper} for given class.
     *
     * @param clazz the class type
     * @return new or cached instance of {@link ObjectMapper} for class type
     */
    private ObjectMapper getObjectMapper(final Class clazz) {
        ObjectMapper objectMapper = OBJECT_MAPPER_MAP.get(clazz);
        if (objectMapper == null) {
            objectMapper = JsonMapper.builder() // or different mapper for other format
                    .addModule(new ParameterNamesModule())
                    .addModule(new Jdk8Module())
                    .addModule(new JavaTimeModule())
                    .build();
            objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
            objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
            objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
            objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
            objectMapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
            OBJECT_MAPPER_MAP.put(clazz, objectMapper);
        }
        return objectMapper;
    }

}
