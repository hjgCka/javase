package com.hjg.jackson.example.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

public class ConvertUtil {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static <T> T convert2Obj(Object object, Class<T> cls) {
        return objectMapper.convertValue(object, cls);
    }

    public static <T> List<T> convert2List(Object object, Class<T> cls) {
        return objectMapper.convertValue(object, new TypeReference<List<T>>() {});
    }

}
