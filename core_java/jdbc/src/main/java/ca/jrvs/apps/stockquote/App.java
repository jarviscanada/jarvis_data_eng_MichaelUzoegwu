package ca.jrvs.apps.stockquote;

import ca.jrvs.apps.stockquote.dao.PositionDao;
import ca.jrvs.apps.stockquote.dao.QuoteDao;
import ca.jrvs.apps.stockquote.dto.Position;

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
      System.out.println("Incorrect usage, too many arguments.");
    }

    apiKey = args[0];

    // Connect to DB
    try (Connection connection = DBConnector.getNewConnection()) {
      // Get Quote via API
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
//      for (int i = 0; i < 5; i++) {
//        qd.save(helper.fetchQuoteInfo(stockSymbols[i]));
//      }

      // DAO -- Quote
      //qd.deleteAll();
      //qd.deleteById("AAPL");
      //qd.save(quote);
      //quote = qd.findById("AAPL").get();
      //System.out.println(quote);
      //Iterable<Quote> quotes = qd.findAll();
      //quotes.forEach(System.out::println);


      PositionDao pd = new PositionDao(connection);

//      Position myPos = new Position();
//      myPos.setTicker("AMZN");
//      myPos.setNumOfShares(80);
//      myPos.setValuePaid(70);
//      pd.save(myPos);

//      pd.deleteById("MSFT");

//      pd.deleteAll();
//
//      Iterable<Position> allPos = pd.findAll();
//      allPos.forEach(System.out::println);

    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public static String getApiKey() {
    return apiKey;
  }
}
