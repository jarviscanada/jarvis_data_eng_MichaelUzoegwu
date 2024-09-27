package ca.jrvs.apps.stockquote;

import ca.jrvs.apps.stockquote.dao.QuoteDao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

public class App {

  private static String apiKey;

  public static void main(String[] args) {
    if (args.length < 1) {
      System.out.println("Incorrect usage, missing API-Key parameter.");
    } else if (args.length > 1) {
      System.out.println("Incorrect usage, too many parameters.");
    }

    // Connect to DB
    final String url = "jdbc:postgresql://%s:%s/%s".formatted("localhost", "5433", "stock_quote");
    Properties props = new Properties();
    props.setProperty("user", "postgres");
    props.setProperty("password", "password");

    try (Connection connection = DriverManager.getConnection(url, props)) {
      // Get Quote via API
      apiKey = args[0];
      QuoteHttpHelper helper = new QuoteHttpHelper();
      String[] stockSymbols = {
              "AAPL",  // Apple Inc.
              "GOOGL", // Alphabet Inc. (Google)
              "AMZN",  // Amazon.com Inc.
              "MSFT",  // Microsoft Corporation
              "TSLA",  // Tesla Inc.
              "FB",    // Meta Platforms Inc. (formerly Facebook)
              "NFLX",  // Netflix Inc.
              "NVDA",  // NVIDIA Corporation
              "BRK.A", // Berkshire Hathaway Inc.
              "JPM"    // JPMorgan Chase & Co.
      };

      QuoteDao qd = new QuoteDao(connection);

      //Pop DB
      for (int i = 0; i < 5; i++) {
        qd.save(helper.fetchQuoteInfo(stockSymbols[i]));
      }

      // DAO
      //qd.deleteAll();
      //qd.deleteById("AAPL");
      //qd.save(quote);
      //quote = qd.findById("AAPL").get();
      //System.out.println(quote);
      //Iterable<Quote> quotes = qd.findAll();
      //quotes.forEach(System.out::println);
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public static String getApiKey() {
    return apiKey;
  }
}
