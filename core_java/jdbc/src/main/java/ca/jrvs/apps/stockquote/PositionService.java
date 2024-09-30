package ca.jrvs.apps.stockquote;

import ca.jrvs.apps.stockquote.dao.PositionDao;
import ca.jrvs.apps.stockquote.dto.Position;

import java.sql.Connection;
import java.util.Optional;

public class PositionService {
  final private PositionDao dao;

  public PositionService(Connection connection, PositionDao dao) {
    this.dao = dao;
  }

  /**
   * Processes a buy order and updates the database accordingly
   *
   * @param ticker         quote's symbol
   * @param numberOfShares number of shares in buy order
   * @param price          price per share in buy order
   * @return The position in our database after processing the buy
   * @throws IllegalArgumentException If ticker is null, {@code numberOfShares} is less than 0 or {@code price} is
   *                                  less than 0
   */
  public Position buy(String ticker, int numberOfShares, double price) throws IllegalArgumentException {
    if (ticker == null || numberOfShares < 0 || price < 0) {
      throw new IllegalArgumentException("Invalid arguments in buy order.");
    }

    Optional<Position> startPositionOpt = dao.findById(ticker);

    if (startPositionOpt.isEmpty()) {
      Position emptyPosition = new Position();
      emptyPosition.setTicker(ticker);
      emptyPosition.setNumOfShares(0);
      emptyPosition.setValuePaid(0);
      startPositionOpt = Optional.of(emptyPosition);
    }

    Position startPosition = startPositionOpt.get();
    Position newPosition = new Position();

    newPosition.setTicker(ticker);
    newPosition.setNumOfShares(startPosition.getNumOfShares() + numberOfShares);
    newPosition.setValuePaid(startPosition.getValuePaid() + numberOfShares * price);

    return dao.save(newPosition);
  }

  /**
   * Sells all shares of the given ticker symbol
   *
   * @param ticker quote's symbol
   */
  public void sell(String ticker) throws IllegalArgumentException {
    dao.deleteById(ticker);
  }
}
