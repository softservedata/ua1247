package com.softserve.edu14.utils;

import org.apache.log4j.Level;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.RollingFileAppender;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigLoader {
    private static final Properties properties = new Properties();

    static {
        try (InputStream input = ConfigLoader.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (input == null) {
                throw new RuntimeException("Unable to find config.properties");
            }
            properties.load(input);
            configureLog4j();
        } catch (IOException e) {
            throw new RuntimeException("Failed to load configuration properties", e);
        }
    }

    public static String getProperty(String key) {
        return properties.getProperty(key);
    }

    public static int getIntProperty(String key) {
        return Integer.parseInt(getProperty(key));
    }

    public static long getLongProperty(String key) {
        return Long.parseLong(getProperty(key));
    }

    private static void loadLogPropertiesToSystem() {
        properties.forEach((key, value) -> {
            if (key.toString().startsWith("log.")) {
                System.setProperty(key.toString(), value.toString());
            }
        });
    }

    private static void clearLogPropertiesFromSystem() {
        properties.forEach((key, value) -> {
            if (key.toString().startsWith("log.")) {
                System.clearProperty(key.toString());
            }
        });
    }

    public static void configureLog4j() {
        loadLogPropertiesToSystem();
        String logPackages = getProperty("log.packages");
        String logLevel = getProperty("log.level");
        String logPath = getProperty("log.path");

        if (logPackages != null && logLevel != null) {
            org.apache.log4j.Logger dynamicLogger = org.apache.log4j.Logger.getLogger(logPackages);
            dynamicLogger.setLevel(Level.toLevel(logLevel));
            dynamicLogger.addAppender(createRollingFileAppender(logPath));
        }
        clearLogPropertiesFromSystem();
    }

    private static RollingFileAppender createRollingFileAppender(String logPath) {
        RollingFileAppender rollingFileAppender = new RollingFileAppender();
        rollingFileAppender.setFile(logPath);
        rollingFileAppender.setAppend(false);
        rollingFileAppender.setMaxFileSize("10MB");
        rollingFileAppender.setMaxBackupIndex(5);
        rollingFileAppender.setEncoding("UTF-8");

        PatternLayout layout = new PatternLayout();
        layout.setConversionPattern("%d{yyyy-MM-dd HH:mm:ss Z} [%p] %c{1}.%M - %m%n%n");
        rollingFileAppender.setLayout(layout);

        rollingFileAppender.activateOptions();
        return rollingFileAppender;
    }
}
