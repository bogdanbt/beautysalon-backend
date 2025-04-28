package com.beautysalon.config;


public class EnvConfig {

    public static String get(String key) {
        return System.getenv(key);
    }
}
