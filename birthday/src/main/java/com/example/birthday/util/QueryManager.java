package com.example.birthday.util;

import com.google.common.io.CharStreams;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class QueryManager {

    public static final String BASE_PATH = "QueryStore/";
    public static final String pingDB = "/pingDB.sql";

    public static String getSql(String fileName) throws IOException {
        try (InputStream inputStream = QueryManager.class.getClassLoader().getResourceAsStream(BASE_PATH + fileName);
             InputStreamReader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8)) {
            return CharStreams.toString(reader);
        }
    }
}