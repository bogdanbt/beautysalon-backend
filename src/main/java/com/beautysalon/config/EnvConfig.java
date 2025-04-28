package com.beautysalon.config;

public class EnvConfig {

    public static String get(String key) {
        String value = System.getenv(key);
        if (value == null) {
            throw new RuntimeException("Environment variable '" + key + "' is not set!");
        }
        return value;
    }
}