package ca.jrvs.apps.stockquote.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.stream.Stream;

public class AppProperties {
  private static Properties properties;
  private final static String propertiesPath = "src/main/resources/properties.txt";

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
    Path path = Paths.get(propertiesPath);

    try (Stream<String> lines = Files.lines(path)) {
      lines.forEach((line) -> {
        String[] keyValue = line.split(":", 2);
        if (keyValue.length == 2) {
          props.setProperty(keyValue[0], keyValue[1]);
        }
      });
    } catch (IOException e) {
      e.printStackTrace();
    }

    return props;
  }
}