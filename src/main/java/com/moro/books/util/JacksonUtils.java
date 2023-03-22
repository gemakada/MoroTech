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
                    // and possibly other configuration, modules, then:
                    .build();
            // to enable standard indentation ("pretty-printing"):
            // objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
            // to allow serialization of "empty" POJOs (no properties to serialize)
            // (without this setting, an exception is thrown in those cases)
            objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
            // to write java.util.Date, Calendar as number (timestamp):
            objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
            objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

            // DeserializationFeature for changing how JSON is read as POJOs:
            // to prevent exception when encountering unknown property:
            objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
            // to allow coercion of JSON empty String ("") to null Object value:
            objectMapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
            OBJECT_MAPPER_MAP.put(clazz, objectMapper);
        }
        return objectMapper;
    }

}
