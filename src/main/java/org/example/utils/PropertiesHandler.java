package org.example.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesHandler {

    private static final Properties SETTINGS = new Properties();
    public static void loadProperties(String fileName) throws IOException {

        try (InputStream in = PropertiesHandler.class.getClassLoader()
                .getResourceAsStream(fileName)) {

            if (in != null)
                SETTINGS.load(in);
        }
    }
    public static String getProperty(String key) {
        return SETTINGS.containsKey(key) ? SETTINGS.getProperty(key) : null;
    }
}

