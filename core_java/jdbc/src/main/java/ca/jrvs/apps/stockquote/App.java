package ca.jrvs.apps.stockquote;

import ca.jrvs.apps.stockquote.dao.PositionDao;
import ca.jrvs.apps.stockquote.dao.QuoteDao;
import ca.jrvs.apps.stockquote.util.AppProperties;
import ca.jrvs.apps.stockquote.util.DBConnector;
import ca.jrvs.apps.stockquote.util.QuoteHttpHelper;

import java.sql.Connection;

public class App {

  public static void main(String[] args) {
    try {
      Class.forName(AppProperties.get(AppProperties.PropertyNames.DB_CLASS));
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }

    try {
      Connection connection = DBConnector.getConnection();

      PositionDao positionDao = new PositionDao(connection);
      PositionService positionService = new PositionService(positionDao);

      QuoteDao quoteDao = new QuoteDao(connection);
      QuoteHttpHelper helper = new QuoteHttpHelper(AppProperties.get(AppProperties.PropertyNames.API_KEY));
      QuoteService quoteService = new QuoteService(helper, quoteDao);

      StockQuoteController controller = new StockQuoteController(quoteService, positionService, positionDao);
      controller.initClient(args);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}