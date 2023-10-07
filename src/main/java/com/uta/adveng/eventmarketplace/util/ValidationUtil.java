package com.uta.adveng.eventmarketplace.util;

public class ValidationUtil {
    public static void checkNotNull(String field, String value){
        if(field == null || field.isEmpty()) {
            throw new RuntimeException("Empty required Field: " + value);
        }
    }
}
