package ca.jrvs.apps.stockquote.dao;

import ca.jrvs.apps.stockquote.Quote;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

public class QuoteDao extends Dao implements CrudDao<Quote, String> {

  private static class Cols {
    public static final String SYMBOL = "symbol";
    public static final String OPEN = "open";
    public static final String HIGH = "high";
    public static final String LOW = "low";
    public static final String PRICE = "price";
    public static final String VOLUME = "volume";
    public static final String LTD = "latest_trading_day";
    public static final String PREV_CLOSE = "previous_close";
    public static final String CHANGE = "change";
    public static final String CHANGE_PERCENT = "change_percent";
    public static final String TIMESTAMP = "timestamp";
  }

  private static final String INSERT = "INSERT INTO quote (symbol, \"open\", high, low, price, volume, " +
          "latest_trading_day, previous_close, \"change\", change_percent, \"timestamp\") VALUES(?, ?, ?, ?, ?, ?, ?," +
          " ?, ?, ?, ?)";
  private static final String UPDATE = "UPDATE quote SET " +
          "symbol = ?, " +
          "\"open\" = ?, " +
          "high = ?, " +
          "low = ?, " +
          "price = ?, " +
          "volume = ?, " +
          "latest_trading_day = ?, " +
          "previous_close = ?, " +
          "\"change\" = ?, " +
          "change_percent = ?, " +
          "\"timestamp\" = ? " +
          "WHERE symbol = ?";
  private static final String GET_BY_ID = "SELECT * FROM \"quote\" WHERE symbol = ?;";
  private static final String GET_ALL = "SELECT * FROM \"quote\";";
  private static final String DELETE_BY_ID = "DELETE FROM \"quote\" WHERE symbol = ?;";
  private static final String DELETE_ALL = "DELETE FROM \"quote\";";

  public QuoteDao(Connection connection) {
    super(connection);
  }

  @Override
  public Quote save(Quote entity) throws IllegalArgumentException {
    if (entity == null) {
      throw new IllegalArgumentException("Quote is null");
    }

    if (findById(entity.getTicker()).isPresent()) {
      return useStatement(UPDATE, (PreparedStatement statement) -> {
        setSaveStatementFromQuote(statement, entity, true);
        statement.execute();
        return findById(entity.getTicker()).get();
      });
    }

    return useStatement(INSERT, (PreparedStatement statement) -> {
      setSaveStatementFromQuote(statement, entity, false);
      statement.execute();
      return findById(entity.getTicker()).get();
    });
  }

  @Override
  public Optional<Quote> findById(String symbol) throws IllegalArgumentException {
    if (symbol == null) {
      throw new IllegalArgumentException("Symbol is null");
    }

    return useStatement(GET_BY_ID, (PreparedStatement statement) -> {
      statement.setString(1, symbol);
      ResultSet resultSet = statement.executeQuery();
      if (resultSet.next()) {
        return Optional.of(quoteFromResultSet(resultSet));
      }
      return Optional.empty();
    });
  }

  @Override
  public Iterable<Quote> findAll() {
    return useStatement(GET_ALL, (PreparedStatement statement) -> {
      ArrayList<Quote> quotes = new ArrayList<>();
      ResultSet resultSet = statement.executeQuery();
      while (resultSet.next()) {
        quotes.add(quoteFromResultSet(resultSet));
      }
      return quotes;
    });
  }

  @Override
  public void deleteById(String symbol) throws IllegalArgumentException {
    useStatement(DELETE_BY_ID, (PreparedStatement statement) -> {
      statement.setString(1, symbol);
      statement.execute();
      return Boolean.TRUE;
    });
  }

  @Override
  public void deleteAll() {
    useStatement(DELETE_ALL, PreparedStatement::execute);
  }

  private Quote quoteFromResultSet(ResultSet resultSet) throws SQLException {
    Quote quote = new Quote();

    quote.setTicker(resultSet.getString(Cols.SYMBOL));
    quote.setOpen(resultSet.getDouble(Cols.OPEN));
    quote.setHigh(resultSet.getDouble(Cols.HIGH));
    quote.setLow(resultSet.getDouble(Cols.LOW));
    quote.setPrice(resultSet.getDouble(Cols.PRICE));
    quote.setVolume(resultSet.getInt(Cols.VOLUME));
    quote.setLatestTradingDay(resultSet.getDate(Cols.LTD));
    quote.setPreviousClose(resultSet.getDouble(Cols.PREV_CLOSE));
    quote.setChange(resultSet.getDouble(Cols.CHANGE));
    quote.setChangePercent(resultSet.getString(Cols.CHANGE_PERCENT));
    quote.setTimestamp(resultSet.getTimestamp(Cols.TIMESTAMP));

    return quote;
  }

  private void setSaveStatementFromQuote(PreparedStatement statement, Quote entity, boolean isUpdate) throws SQLException {
    statement.setString(1, entity.getTicker());
    statement.setDouble(2, entity.getOpen());
    statement.setDouble(3, entity.getHigh());
    statement.setDouble(4, entity.getLow());
    statement.setDouble(5, entity.getPrice());
    statement.setInt(6, entity.getVolume());
    statement.setDate(7, entity.getLatestTradingDay());
    statement.setDouble(8, entity.getPreviousClose());
    statement.setDouble(9, entity.getChange());
    statement.setString(10, entity.getChangePercent());
    statement.setTimestamp(11, entity.getTimestamp());
    if (isUpdate) statement.setString(12, entity.getTicker());
  }
}
