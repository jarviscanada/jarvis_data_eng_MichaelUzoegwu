package ca.jrvs.apps.stockquote;

import ca.jrvs.apps.stockquote.dao.PositionDao;
import ca.jrvs.apps.stockquote.dto.Position;
import ca.jrvs.apps.stockquote.util.DBConnector;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PositionService_IntTest {

  PositionService positionService;
  PositionDao positionDao;
  final double delta = 0.0001;

  @Before
  public void setUp() throws Exception {
    positionDao = new PositionDao(DBConnector.getConnection());
    positionService = new PositionService(positionDao);
  }

  @Test
  public void buyUpdate() {
    final String ticker = "TSLA";
    final int firstSharesToBuy = 500;
    final double firstPricePerShare = 2;

    Position startPosition = positionService.buy(ticker, firstSharesToBuy, firstPricePerShare);

    assertEquals(
            "Ticker changed when it shouldn't have.",
            ticker,
            startPosition.getTicker()
    );
    assertEquals("Value paid wasn't updated correctly.",
            firstSharesToBuy * firstPricePerShare,
            startPosition.getValuePaid(),
            delta
    );
    assertEquals(
            "Number of shares wasn't updated correctly.",
            firstSharesToBuy,
            startPosition.getNumOfShares()
    );

    final int secondSharesToBuy = 3700;
    final double secondPricePerShare = 23;

    Position secondPosition = positionService.buy(ticker, secondSharesToBuy, secondPricePerShare);
    assertEquals(
            "Ticker changed when it shouldn't have.",
            ticker,
            secondPosition.getTicker()
    );
    assertEquals("Value paid wasn't updated correctly.",
            secondSharesToBuy * secondPricePerShare + firstSharesToBuy * firstPricePerShare,
            secondPosition.getValuePaid(),
            delta
    );
    assertEquals(
            "Number of shares wasn't updated correctly.",
            secondSharesToBuy + firstSharesToBuy,
            secondPosition.getNumOfShares()
    );
  }

  @Test(expected = IllegalArgumentException.class)
  public void buyInvalidShares() {
    positionService.buy("AAPL", -30, 50);
  }

  @Test(expected = IllegalArgumentException.class)
  public void buyInvalidPrice() {
    positionService.buy("AAPL", 30, -50);
  }

  @Test(expected = IllegalArgumentException.class)
  public void buyNullTicker() {
    positionService.buy(null, 30, 50);
  }

  @Test(expected = IllegalArgumentException.class)
  public void sellNull() {
    positionService.sell(null);
  }
}