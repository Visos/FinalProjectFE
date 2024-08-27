package com.betacom.fe.utilities;

import com.fasterxml.jackson.databind.ObjectMapper;

public class WebUtils {
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static Object convertInObject(Object obj, Class clazz) {
        Object result = new Object();
        ObjectMapper mapper = new ObjectMapper();

        try {
            result = mapper.convertValue(obj, clazz);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }

        return result;
    }

}