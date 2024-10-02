package ca.jrvs.apps.stockquote;

import ca.jrvs.apps.stockquote.dao.PositionDao;
import ca.jrvs.apps.stockquote.dao.QuoteDao;
import ca.jrvs.apps.stockquote.util.AppProperties;
import ca.jrvs.apps.stockquote.util.DBConnector;
import ca.jrvs.apps.stockquote.util.QuoteHttpHelper;

public class App {

  public static void main(String[] args) {
    try {
      Class.forName(AppProperties.get(AppProperties.PropertyNames.DB_CLASS));
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }

    try {
      PositionDao positionDao = new PositionDao(DBConnector.getConnection());
      PositionService positionService = new PositionService(positionDao);

      QuoteHttpHelper helper = new QuoteHttpHelper(AppProperties.get(AppProperties.PropertyNames.API_KEY));
      QuoteService quoteService = new QuoteService(helper);

      StockQuoteController controller = new StockQuoteController(quoteService, positionService, positionDao);

      String[] testArgsBuyTSLA = {"buy", "AAPL", "300"};
      String[] testArgsSellTSLA = {"sell", "TSLA"};
      String[] testArgsCheckStockTSLA = {"check-stock", "TSLA"};
      String[] testArgsCheckPositionTSLA = {"check-position", "TSLA"};

      controller.initClient(testArgsBuyTSLA);
      //controller.initClient(testArgsSellTSLA);
      //controller.initClient(testArgsCheckStockTSLA);
      //controller.initClient(testArgsCheckPositionTSLA);

    } catch (Exception e) {
      e.printStackTrace();
    }


  }
}
