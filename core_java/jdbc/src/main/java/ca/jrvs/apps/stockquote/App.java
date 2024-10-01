package ca.jrvs.apps.stockquote;

import ca.jrvs.apps.stockquote.dao.PositionDao;
import ca.jrvs.apps.stockquote.util.DBConnector;

public class App {

  private static String apiKey;


  public static void main(String[] args) {
    apiKey = args[0];

    try {
      PositionDao positionDao = new PositionDao(DBConnector.getConnection());
      PositionService ps = new PositionService(positionDao);
      ps.buy("AAPL", 500, 30);
    } catch (Exception e) {
      e.printStackTrace();
    }


  }

  public static String getApiKey() {
    return apiKey;
  }
}
