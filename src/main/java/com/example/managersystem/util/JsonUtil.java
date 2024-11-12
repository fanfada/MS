package com.example.managersystem.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

/**
 * json数据处理工具类
 */
@Slf4j
public class JsonUtil {

    private final static ObjectMapper objectMapper = new ObjectMapper();

    static {
        objectMapper.configure(JsonParser.Feature.ALLOW_COMMENTS, true);
        objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        objectMapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
        objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
        objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    /**
     * json字符串转json对象
     *
     * @param jsonString json字符串
     * @return jsonNode
     */
    public synchronized static JsonNode toJsonNode(final String jsonString) {

        JsonNode jsonNode = null;
        try {
            if (jsonString != null && !"".equals(jsonString)) {
                jsonNode = objectMapper.readTree(jsonString);
            }
        } catch (final JsonProcessingException e) {
            e.printStackTrace();
        }
        return jsonNode;
    }

    /**
     * Java对象转Json字符串
     *
     * @param object java对象
     * @return json
     */
    public synchronized static String toString(final Object object) {

        if (object == null) {
            return null;
        }
        String jsonString = "";
        try {
            jsonString = objectMapper.writeValueAsString(object);
        } catch (final Exception e) {
            log.warn("json error:" + e.getMessage());
        }
        return jsonString;
    }

    /**
     * JSON字符串转换为Java泛型对象
     *
     * @param jsonString json字符串
     * @param tr         java对象类型
     * @param <T>        需要转换的对象类型
     * @return Java泛型对象
     */
    public synchronized static <T> T json2GenericObject(final String jsonString, final TypeReference<T> tr) {

        if (jsonString != null && !"".equals(jsonString)) {
            try {
                return (T) (tr.getType().equals(String.class) ? jsonString : objectMapper.readValue(jsonString, tr));
            } catch (final Exception e) {
                log.warn("json error:" + e.getMessage());
            }
        }
        return null;
    }

    /**
     * Json字符串转Java对象
     *
     * @param jsonString json字符串
     * @param clazz      java类
     * @return java对象
     */
    public synchronized static Object json2Object(final String jsonString, final Class<?> clazz) {

        if (jsonString != null && !"".equals(jsonString)) {
            try {
                return objectMapper.readValue(jsonString, clazz);
            } catch (final Exception e) {
                log.warn("json error:" + e.getMessage());
            }
        }
        return "";
    }

    /**
     * JSON字符串转java对象
     *
     * @param <T>     转换泛型
     * @param jsonStr JSON字符串
     * @param clazz   类型
     * @return java对象
     */
    public synchronized static <T> T json2Java(final String jsonStr, final Class<T> clazz) {

        try {
            return objectMapper.readValue(jsonStr, clazz);
        } catch (final IOException e) {
            log.warn("json error:" + e.getMessage());
            return null;
        }
    }
}
