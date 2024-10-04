package ca.jrvs.apps.stockquote.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class AppProperties {

  private static Properties properties;
  private final static Logger LOGGER = LoggerFactory.getLogger(AppProperties.class);
  private final static String propertiesPath = "properties.txt";

  public static class PropertyNames {
    public final static String DB_CLASS = "db-class";
    public final static String SERVER = "server";
    public final static String DATABASE = "database";
    public final static String PORT = "port";
    public final static String USERNAME = "username";
    public final static String PASSWORD = "password";
    public final static String API_KEY = "api-key";
  }

  public static String get(String key) {
    if (properties == null) properties = readProperties();
    return properties.getProperty(key);
  }

  private static Properties readProperties() {
    Properties props = new Properties();

    try (InputStream inputStream = AppProperties.class.getClassLoader().getResourceAsStream(propertiesPath)) {
      if (inputStream == null) {
        LOGGER.error("Could not read properties from {}", propertiesPath);
      }
      props.load(inputStream);
    } catch (IOException e) {
      LOGGER.error("Could not read properties from {}", propertiesPath, e);
    }

    return props;
  }
}