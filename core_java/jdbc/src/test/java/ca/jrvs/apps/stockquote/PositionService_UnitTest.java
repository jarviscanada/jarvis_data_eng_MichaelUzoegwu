package ca.jrvs.apps.stockquote;

import ca.jrvs.apps.stockquote.dao.PositionDao;
import ca.jrvs.apps.stockquote.dto.Position;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class PositionService_UnitTest {

  @Mock
  PositionDao positionDao;

  PositionService positionService;
  final double delta = 0.0001;

  @Before
  public void setUp() throws Exception {
    positionDao = mock(PositionDao.class);
    positionService = new PositionService(positionDao);
  }

  @Test
  public void buySecond() {
    final String ticker = "AAPL";
    final int initNumShares = 500;
    final double initValuePaid = 1000;

    Position startingPosition = new Position();
    startingPosition.setTicker(ticker);
    startingPosition.setNumOfShares(initNumShares);
    startingPosition.setValuePaid(initValuePaid);

    when(positionDao.findById(ticker)).thenReturn(Optional.of(startingPosition));

    final int sharesToBuy = 500;
    final double pricePerShare = 2;
    positionService.buy(ticker, sharesToBuy, pricePerShare);

    ArgumentCaptor<Position> positionArgumentCaptor = ArgumentCaptor.forClass(Position.class);
    verify(positionDao).save(positionArgumentCaptor.capture());
    Position newPosition = positionArgumentCaptor.getValue();

    assertEquals(
            "Ticker changed when it shouldn't have.",
            ticker,
            newPosition.getTicker()
    );
    assertEquals("Value paid wasn't updated correctly.",
            initValuePaid + sharesToBuy * pricePerShare,
            newPosition.getValuePaid(),
            delta
    );
    assertEquals(
            "Number of shares wasn't updated correctly.",
            initNumShares + sharesToBuy,
            newPosition.getNumOfShares()
    );
  }

  @Test
  public void buyFirst() {
    final String ticker = "AAPL";

    when(positionDao.findById(ticker)).thenReturn(Optional.empty());

    final int sharesToBuy = 500;
    final double pricePerShare = 2;
    positionService.buy(ticker, sharesToBuy, pricePerShare);

    ArgumentCaptor<Position> positionArgumentCaptor = ArgumentCaptor.forClass(Position.class);
    verify(positionDao).save(positionArgumentCaptor.capture());
    Position newPosition = positionArgumentCaptor.getValue();

    assertEquals(
            "Ticker changed when it shouldn't have.",
            ticker,
            newPosition.getTicker()
    );
    assertEquals("Value paid wasn't updated correctly.",
            sharesToBuy * pricePerShare,
            newPosition.getValuePaid(),
            delta
    );
    assertEquals(
            "Number of shares wasn't updated correctly.",
            sharesToBuy,
            newPosition.getNumOfShares()
    );
  }

  @Test(expected = IllegalArgumentException.class)
  public void sellNull() {
    doThrow(new IllegalArgumentException("Invalid ticker")).when(positionDao).deleteById(isNull());
    positionService.sell(null);
  }
}