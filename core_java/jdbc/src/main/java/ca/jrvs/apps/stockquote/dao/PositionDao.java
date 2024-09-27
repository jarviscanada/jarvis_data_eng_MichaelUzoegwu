package ca.jrvs.apps.stockquote.dao;

import ca.jrvs.apps.stockquote.dto.Position;

import java.sql.Connection;
import java.util.Optional;

public class PositionDao extends Dao implements CrudDao<Position, String> {

  private Connection connection;

  public PositionDao(Connection connection) {
    super(connection);
  }

  @Override
  public Position save(Position entity) throws IllegalArgumentException {
    return null;
  }

  @Override
  public Optional<Position> findById(String s) throws IllegalArgumentException {
    return Optional.empty();
  }

  @Override
  public Iterable<Position> findAll() {
    return null;
  }

  @Override
  public void deleteById(String s) throws IllegalArgumentException {

  }

  @Override
  public void deleteAll() {

  }
}
