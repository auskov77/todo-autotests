package ru.buttonone.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Props {
    private static final Properties properties = new Properties();

    static {
        try {
            ClassLoader classLoader = Props.class.getClassLoader();
            InputStream inputStream = classLoader.getResourceAsStream("application.properties");
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getProperty(String key) {
        String property = System.getProperty(key);
        return property == null ? properties.getProperty(key) : property;
    }
}
