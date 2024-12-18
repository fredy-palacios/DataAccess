package org.fredypalacios;

import io.github.cdimascio.dotenv.Dotenv;

public class AppConfig {
    public static final String DB_URL;
    public static final String DB_USER;
    public static final String DB_PASSWORD;

    static {
        Dotenv dotenv = Dotenv.load();
        DB_URL = dotenv.get("DB_URL");
        DB_USER = dotenv.get("DB_USER");
        DB_PASSWORD = dotenv.get("DB_PASSWORD");
    }
}