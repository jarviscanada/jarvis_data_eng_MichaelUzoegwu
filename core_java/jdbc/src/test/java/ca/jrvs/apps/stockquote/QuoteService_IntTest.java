package ca.jrvs.apps.stockquote;

import ca.jrvs.apps.stockquote.dto.Quote;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class QuoteService_IntTest {

  static QuoteService quoteService;
  static QuoteHttpHelper helper;

  @BeforeClass
  public static void setUp() throws Exception {
    final String apiKey = System.getenv("API_KEY");
    helper = new QuoteHttpHelper(apiKey);
    quoteService = new QuoteService(helper);
  }

  @Test
  public void fetchQuoteDataFromAPI() {
    final String ticker = "AAPL";

    Optional<Quote> fetchedQuoteOpt = quoteService.fetchQuoteDataFromAPI(ticker);
    assertTrue(
            String.format("Expected fetchedQuote '%s' to be present. Instead fetched quote is empty.", ticker),
            fetchedQuoteOpt.isPresent()
    );

    Quote fetchedQuote = fetchedQuoteOpt.get();
    assertEquals(ticker, fetchedQuote.getTicker());
    assertTrue("Expected open to be >= 0.", fetchedQuote.getOpen() >= 0);
    assertTrue("Exptected high to be >= 0.", fetchedQuote.getHigh() >= 0);
    assertTrue("Expected low to be >= 0.", fetchedQuote.getLow() >= 0);
    assertTrue("Expected price to be >= 0.", fetchedQuote.getPrice() >= 0);
    assertTrue("Expected volume to be >= 0.", fetchedQuote.getVolume() >= 0);
    assertNotNull("Expected latest trading day to be not null.", fetchedQuote.getLatestTradingDay());
    assertTrue("Expected previous close to be >= 0.", fetchedQuote.getPreviousClose() >= 0);
    assertTrue("Expected change to be >= 0.", fetchedQuote.getChange() >= 0);
    assertNotNull("Expected change percent to be not null.", fetchedQuote.getChangePercent());
    assertFalse("Expected change percent to not be empty.", fetchedQuote.getChangePercent().isEmpty());
  }

  @Test
  public void fetchQuoteDataFromAPINonExistent() {
    final String ticker = "NON_EXISTENT";
    Optional<Quote> fetchedQuoteOpt = quoteService.fetchQuoteDataFromAPI(ticker);
    assertTrue("Expected fetched quote to be empty. Instead quote had a value",
            fetchedQuoteOpt.isEmpty());
  }
}