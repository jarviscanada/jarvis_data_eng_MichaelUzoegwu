package ca.jrvs.apps.stockquote.dao;

import static org.junit.Assert.*;

import ca.jrvs.apps.stockquote.DBConnector;
import ca.jrvs.apps.stockquote.dto.Position;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

public class PositionDaoTest {
  PositionDao posDao;
  final double delta = 0.0001;

  @Before
  public void setUp() throws Exception {
    posDao = new PositionDao(DBConnector.getNewConnection());
  }

  /**
   * Tests positionDao.save() and positionDao.findById()
   */
  @Test
  public void saveUpdate() {
    final int[] numShares = {333, 891};
    final double[] valPaid = {450351, 322758};

    // First iteration test if inserts are successful.
    // Second iteration tests if updates are successful.
    for (int i = 0; i < 2; i++) {
      final int curNumShares = numShares[i];
      final double curValPaid = valPaid[i];
      Position pos = new Position();

      pos.setTicker("AAPL");
      pos.setNumOfShares(curNumShares);
      pos.setValuePaid(curValPaid);
      posDao.save(pos);

      Optional<Position> savedPosOpt = posDao.findById(pos.getTicker());
      if (savedPosOpt.isEmpty()) {
        fail("Test failed due no matching element found in DB. Position '%s' wasn't saved successfully."
                .formatted(pos.getTicker())
        );
      }
      assertEquals("Checking if Positions are equal [%s]".formatted(pos.getTicker()), pos, savedPosOpt.get());
    }
  }

  @Test
  public void findAll() {
    String[] symbols = {"AMZN", "AAPL", "MSFT"};
    int[] numShares = {111, 222, 333};
    double[] valPaid = {888, 321, 732000};

    List<Position> savedPositions = savePositions(symbols, numShares, valPaid);
    Iterable<Position> foundPositions = posDao.findAll();

    for (Position savedPos : savedPositions) {
      // To not assume same order
      // Find associated Position from result list
      for (Position foundPos : foundPositions) {
        if (!foundPos.getTicker().equals(savedPos.getTicker())) continue;
        assertEquals("Comparing positions", savedPos, foundPos);
      }
    }
  }

  @Test
  public void deleteById() {
    Position pos = new Position();
    pos.setTicker("TSLA");
    pos.setNumOfShares(3000);
    pos.setValuePaid(480000);

    posDao.save(pos);

    Optional<Position> savedPosOpt = posDao.findById(pos.getTicker());
    assertTrue(
            String.format("Expected Position '%s' to be found but it was not found.", pos.getTicker()),
            savedPosOpt.isPresent()
    );

    posDao.deleteById(pos.getTicker());

    Optional<Position> deletedPosOpt = posDao.findById(pos.getTicker());
    assertTrue(
            String.format("Expected Position '%s' to not be found but it was found.", pos.getTicker()),
            deletedPosOpt.isEmpty()
    );
  }

  @Test
  public void deleteAll() {
  }

  private List<Position> savePositions(String[] symbols, int[] numShares, double[] valPaid) {

    List<Position> positions = new ArrayList<>();

    for (int i = 0; i < symbols.length; i++) {
      Position pos = new Position();
      pos.setTicker(symbols[i]);
      pos.setNumOfShares(numShares[i]);
      pos.setValuePaid(valPaid[i]);
      posDao.save(pos);
      positions.add(pos);
    }

    return positions;
  }

  private void assertPositionSaved(Position pos) {
    Optional<Position> savedPosOpt = posDao.findById(pos.getTicker());
    if (savedPosOpt.isEmpty()) {
      fail("Test failed due no matching element found in DB. Position '%s' wasn't saved successfully."
              .formatted(pos.getTicker())
      );
    }
  }
}