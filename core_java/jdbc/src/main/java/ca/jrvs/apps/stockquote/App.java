package ca.jrvs.apps.stockquote;

import ca.jrvs.apps.stockquote.dao.PositionDao;
import ca.jrvs.apps.stockquote.dao.QuoteDao;

import java.sql.Connection;
import java.sql.SQLException;

public class App {

  private static String apiKey;

  public static void main(String[] args) {
    if (args.length < 1) {
      System.out.println("Incorrect usage, missing API-Key parameter.");
    } else if (args.length > 1) {
      System.out.println("Incorrect usage, too many arguments.");
    }
    apiKey = args[0];
  }

  public static String getApiKey() {
    return apiKey;
  }
}
