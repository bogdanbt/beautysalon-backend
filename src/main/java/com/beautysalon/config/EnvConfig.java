package com.beautysalon.config;

import io.github.cdimascio.dotenv.Dotenv;

public class EnvConfig {
    private static final Dotenv dotenv = Dotenv.configure()
            .directory("src/main/resources")   // путь до .env
            .filename(".env")
            .load();

    public static String get(String key) {
        return dotenv.get(key);
    }
}
