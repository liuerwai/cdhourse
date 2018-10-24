package com.cdhouse.utils;

import java.io.IOException;
import java.util.Properties;

public enum PropertyUtils {

    INSTANCE(new Properties());
    public Properties properties;

    PropertyUtils(Properties properties) {
        this.properties = properties;
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        try {
            this.properties.load(loader.getResourceAsStream("application.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getPropertiesKey(String key) {

        return properties.getProperty(key, "default");
    }

}
