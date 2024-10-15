package ca.jrvs.apps.stockquote.util;

import ca.jrvs.apps.stockquote.util.AppProperties;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnector {
  private static Connection connection;

  public static Connection getConnection() throws SQLException {
    if (connection != null && !connection.isClosed()) return connection;

    final String url = "jdbc:postgresql://%s:%s/%s".formatted(
            AppProperties.get(AppProperties.PropertyNames.SERVER),
            AppProperties.get(AppProperties.PropertyNames.PORT),
            AppProperties.get(AppProperties.PropertyNames.DATABASE)
    );

    Properties props = new Properties();
    props.setProperty("user", AppProperties.get(AppProperties.PropertyNames.USERNAME));
    props.setProperty("password", AppProperties.get(AppProperties.PropertyNames.PASSWORD));
    connection = DriverManager.getConnection(url, props);

    return connection;
  }
}
