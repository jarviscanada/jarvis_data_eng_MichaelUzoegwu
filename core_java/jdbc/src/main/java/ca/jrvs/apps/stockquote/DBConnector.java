package ca.jrvs.apps.stockquote;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnector {
  private static final String url = "jdbc:postgresql://%s:%s/%s".formatted("localhost", "5433", "stock_quote");

  public static Connection getNewConnection() throws SQLException {
    Properties props = new Properties();
    props.setProperty("user", "postgres");
    props.setProperty("password", "password");
    return DriverManager.getConnection(url, props);
  }
}
