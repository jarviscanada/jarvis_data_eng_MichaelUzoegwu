package ca.jrvs.apps.stockquote.dao;

import static org.junit.Assert.*;

import ca.jrvs.apps.stockquote.util.DBConnector;
import ca.jrvs.apps.stockquote.dto.Quote;
import org.junit.Before;
import org.junit.Test;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.Optional;

public class QuoteDaoTest {
  QuoteDao quoteDao;

  @Before
  public void setUp() throws Exception {
    quoteDao = new QuoteDao(DBConnector.getConnection());
  }

  @Test
  public void save() {
    final String symbol = "AAPL";
    Quote savedQuote = createQuote(symbol);

    quoteDao.save(savedQuote);
    Optional<Quote> foundQuoteOpt = quoteDao.findById(symbol);

    assertTrue(String.format("Expected quote '%s' to be saved. Instead did not find quote.", symbol),
            foundQuoteOpt.isPresent());

    assertEquals("Expected created quote to match quote saved in DB. Instead quotes did not match.",
            savedQuote,
            foundQuoteOpt.get());
  }

  @Test(expected = IllegalArgumentException.class)
  public void saveNull() {
    quoteDao.save(null);
  }

  @Test
  public void deleteById() {
    final String symbol = "MSFT";
    Quote savedQuote = createQuote(symbol);

    quoteDao.save(savedQuote);
    Optional<Quote> foundQuoteOpt = quoteDao.findById(symbol);

    assertTrue(String.format("Expected quote '%s' to be saved. Instead did not find quote.", symbol),
            foundQuoteOpt.isPresent());

    quoteDao.deleteById(symbol);
    Optional<Quote> deletedQuoteOpt = quoteDao.findById(symbol);

    assertTrue(String.format("Expected quote '%s' to not be found. Instead it was found", symbol),
            deletedQuoteOpt.isEmpty());
  }

  @Test(expected = IllegalArgumentException.class)
  public void deleteByIdNull() {
    quoteDao.deleteById(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void findByIdNull() {
    quoteDao.findById(null);
  }

  @Test
  public void findByIdNonExistent() {
    final String symbol = "DOES_NOT_EXIST";

    Optional<Quote> nonExistentQuoteOpt = quoteDao.findById(symbol);
    assertTrue(String.format("Expected quote '%s' to not be found. Instead it was found", symbol),
            nonExistentQuoteOpt.isEmpty());
  }

  private Quote createQuote(String symbol) {
    Quote quote = new Quote();
    quote.setTicker(symbol);
    quote.setOpen(145.10);
    quote.setHigh(147.20);
    quote.setLow(144.50);
    quote.setPrice(146.00);
    quote.setVolume(1000000);
    quote.setLatestTradingDay(new Date(10921048));
    quote.setPreviousClose(145.50);
    quote.setChange(0.50);
    quote.setChangePercent("0.34%");
    quote.setTimestamp(new Timestamp(System.currentTimeMillis()));
    return quote;
  }
}