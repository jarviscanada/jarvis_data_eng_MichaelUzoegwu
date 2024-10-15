package ca.jrvs.apps.stockquote;

import ca.jrvs.apps.stockquote.dao.PositionDao;
import ca.jrvs.apps.stockquote.dao.QuoteDao;
import ca.jrvs.apps.stockquote.util.AppProperties;
import ca.jrvs.apps.stockquote.util.DBConnector;
import ca.jrvs.apps.stockquote.util.QuoteHttpHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;

public class App {

  private final static Logger LOGGER = LoggerFactory.getLogger(App.class);

  public static void main(String[] args) {

    try {
      Class.forName(AppProperties.get(AppProperties.PropertyNames.DB_CLASS));
    } catch (ClassNotFoundException e) {
      LOGGER.error("Could not load database driver '{}'", AppProperties.PropertyNames.DB_CLASS, e);
    }

    try {
      Connection connection = DBConnector.getConnection();

      PositionDao positionDao = new PositionDao(connection);
      PositionService positionService = new PositionService(positionDao);

      QuoteDao quoteDao = new QuoteDao(connection);
      QuoteHttpHelper helper = new QuoteHttpHelper(AppProperties.get(AppProperties.PropertyNames.API_KEY));
      QuoteService quoteService = new QuoteService(helper, quoteDao);

      StockQuoteController controller = new StockQuoteController(quoteService, positionService, positionDao);
      controller.initClient();
    } catch (Exception e) {
      LOGGER.error("Something went wrong while running the app.", e);
    }
  }
}