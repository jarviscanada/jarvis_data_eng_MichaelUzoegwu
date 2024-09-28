package ca.jrvs.apps.stockquote.dao;

import ca.jrvs.apps.stockquote.dto.Position;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

public class PositionDao extends Dao implements CrudDao<Position, String> {

  private static class Cols {
    public static final String SYMBOL = "symbol";
    public static final String NUM_SHARES = "number_of_shares";
    public static final String VAL_PAID = "value_paid";
  }

  private final String INSERT = "INSERT INTO \"position\" (symbol, number_of_shares, value_paid) VALUES (? ,? , ?);";
  private final String UPDATE = "UPDATE \"position\" SET " +
          "number_of_shares = ?," +
          "value_paid = ? " +
          "WHERE symbol = ?;";
  private final String GET_BY_ID = "SELECT * FROM \"position\" WHERE symbol = ?;";
  private final String GET_ALL = "SELECT * FROM \"position\";";
  private final String DELETE_BY_ID = "DELETE FROM \"position\" WHERE symbol = ?;";
  private final String DELETE_ALL = "DELETE  FROM \"position\";";

  private Connection connection;

  public PositionDao(Connection connection) {
    super(connection);
  }

  @Override
  public Position save(Position entity) throws IllegalArgumentException {
    if (entity == null) {
      throw new IllegalArgumentException("Position is null");
    }

    if (findById(entity.getTicker()).isPresent()) {
      return useStatement(UPDATE, (statement) -> {
        statement.setInt(1, entity.getNumOfShares());
        statement.setDouble(2, entity.getValuePaid());
        statement.setString(3, entity.getTicker());
        statement.execute();
        return findById(entity.getTicker()).get();
      });
    }

    return useStatement(INSERT, (statement) -> {
      statement.setString(1, entity.getTicker());
      statement.setInt(2, entity.getNumOfShares());
      statement.setDouble(3, entity.getValuePaid());
      statement.execute();
      return findById(entity.getTicker()).get();
    });
  }

  @Override
  public Optional<Position> findById(String symbol) throws IllegalArgumentException {
    if (symbol == null) {
      throw new IllegalArgumentException("Symbol is null");
    }

    return useStatement(GET_BY_ID, (statement) -> {
      statement.setString(1, symbol);
      ResultSet resultSet = statement.executeQuery();
      if (resultSet.next()) {
        return Optional.of(positionFromResultSet(resultSet));
      }
      return Optional.empty();
    });
  }

  @Override
  public Iterable<Position> findAll() {
    return useStatement(GET_ALL, (statement) -> {
      ArrayList<Position> positions = new ArrayList<>();
      ResultSet resultSet = statement.executeQuery();
      while (resultSet.next()) {
        positions.add(positionFromResultSet(resultSet));
      }
      return positions;
    });
  }

  @Override
  public void deleteById(String symbol) throws IllegalArgumentException {
    if (symbol == null) {
      throw new IllegalArgumentException("Symbol is null");
    }

    useStatement(DELETE_BY_ID, (statement) -> {
      statement.setString(1, symbol);
      statement.execute();
      return Boolean.TRUE;
    });
  }

  @Override
  public void deleteAll() {
    useStatement(DELETE_ALL, PreparedStatement::execute);
  }

  private Position positionFromResultSet(ResultSet resultSet) throws SQLException {
    Position position = new Position();

    position.setTicker(resultSet.getString(Cols.SYMBOL));
    position.setNumOfShares(resultSet.getInt(Cols.NUM_SHARES));
    position.setValuePaid(resultSet.getDouble(Cols.VAL_PAID));

    return position;
  }
}
