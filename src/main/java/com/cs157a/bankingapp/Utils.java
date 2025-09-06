package com.cs157a.bankingapp;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.sql.Date;

public class Utils {
    public static String toJSON(Object o) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(o);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static int customerFromToken(String token) {
        return Integer.parseInt(token.split("Z")[1]);
    }

    public static Date dateFromString(String date) {
        return Date.valueOf(date);
    }
}
