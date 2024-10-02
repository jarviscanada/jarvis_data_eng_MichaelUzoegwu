package ca.jrvs.apps.stockquote;

import ca.jrvs.apps.stockquote.dao.QuoteDao;
import ca.jrvs.apps.stockquote.dto.Quote;
import ca.jrvs.apps.stockquote.util.DBConnector;
import ca.jrvs.apps.stockquote.util.QuoteHttpHelper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class QuoteService_UnitTest {

  QuoteService quoteService;

  @Mock
  QuoteHttpHelper helperMock;

  @Before
  public void setUp() throws Exception {
    quoteService = new QuoteService(helperMock, new QuoteDao(DBConnector.getConnection()));
  }

  @Test
  public void fetchQuoteDataFromAPI() {
    final String ticker = "AAPL";
    Quote helperQuote = createQuote(ticker);
    when(helperMock.fetchQuoteInfo(ticker)).thenReturn(helperQuote);

    Optional<Quote> fetchedQuoteOpt = quoteService.fetchQuoteData(ticker);
    assertEquals("Expected fetched quote to match quote retrieved from http helper. Instead returned quote was " +
            "different.", helperQuote, fetchedQuoteOpt.orElseThrow()
    );
  }

  @Test
  public void fetchQuoteDataFromAPINonExistent() {
    final String ticker = "NON_EXISTENT";
    when(helperMock.fetchQuoteInfo(ticker)).thenThrow(new IllegalArgumentException(
            String.format("Could not find any data for provided symbol '%s'", ticker)
    ));

    Optional<Quote> fetchedQuoteOpt = quoteService.fetchQuoteData(ticker);

    assertTrue("Expected fetched quote to be empty. Instead quote had a value",
            fetchedQuoteOpt.isEmpty());
  }

  private Quote createQuote(String ticker) {
    Quote quote = new Quote();
    quote.setTicker(ticker);
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