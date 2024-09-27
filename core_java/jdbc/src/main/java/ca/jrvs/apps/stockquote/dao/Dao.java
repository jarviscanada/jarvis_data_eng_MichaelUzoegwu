package ca.jrvs.apps.stockquote.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public abstract class Dao {
  protected final Connection connection;

  public Dao(Connection connection) {
    this.connection = connection;
  }

  protected <T> T useStatement(String sqlStatement, StatementHandler<T> statementHandler) {
    try (PreparedStatement statement = this.connection.prepareStatement(sqlStatement)) {
      return statementHandler.execute(statement);
    } catch (SQLException e) {
      e.printStackTrace(); // TODO: use logging
    }
    return null;
  }
}

@FunctionalInterface
interface StatementHandler<T> {
  T execute(PreparedStatement statement) throws SQLException;
}
