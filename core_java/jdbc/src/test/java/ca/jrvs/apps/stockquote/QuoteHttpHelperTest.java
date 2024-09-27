package ca.jrvs.apps.stockquote;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import java.text.SimpleDateFormat;

@RunWith(MockitoJUnitRunner.class)
public class QuoteHttpHelperTest {

  @Spy
  private QuoteHttpHelper httpHelper = new QuoteHttpHelper();

  @Test(expected = IllegalArgumentException.class)
  public void fetchQuoteInfoInvalidSymbol() {
    final String wrongSymbol = "WRONG_SYMBOL";
    doReturn("{\n" +
            "\t\"Global Quote\": {}\n" +
            "}").when(httpHelper).fetchQuoteJSON(wrongSymbol);
    httpHelper.fetchQuoteInfo(wrongSymbol);
  }

  @Test
  public void fetchQuoteInfoAAPL() {
    final String aaplSymbol = "AAPL";
    final String applInfo = "{\n" +
            "\t\"Global Quote\": {\n" +
            "\t\t\"01. symbol\": \"AAPL\",\n" +
            "\t\t\"02. open\": \"227.3000\",\n" +
            "\t\t\"03. high\": \"228.5000\",\n" +
            "\t\t\"04. low\": \"225.4100\",\n" +
            "\t\t\"05. price\": \"227.5200\",\n" +
            "\t\t\"06. volume\": \"36358772\",\n" +
            "\t\t\"07. latest trading day\": \"2024-09-26\",\n" +
            "\t\t\"08. previous close\": \"226.3700\",\n" +
            "\t\t\"09. change\": \"1.1500\",\n" +
            "\t\t\"10. change percent\": \"0.5080%\"\n" +
            "\t}\n" +
            "}";
    doReturn(applInfo).when(httpHelper).fetchQuoteJSON(aaplSymbol);
    double delta = 0.0001;


    Quote aaplQuote = httpHelper.fetchQuoteInfo(aaplSymbol);
    assertEquals("Comparing ticker (symbol)", "AAPL", aaplQuote.getTicker());
    assertEquals("Comparing opening price", 227.3, aaplQuote.getOpen(), delta);
    assertEquals("Comparing highest price", 228.5, aaplQuote.getHigh(), delta);
    assertEquals("Comparing lowest price", 225.41, aaplQuote.getLow(), delta);
    assertEquals("Comparing price", 227.52, aaplQuote.getPrice(), delta);
    assertEquals("Comparing volume", 36358772, aaplQuote.getVolume());

    // Compare dates
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    // TODO: see why trading day is differing by one day
    assertEquals("Comparing latest trading day", "2024-09-26", dateFormat.format(aaplQuote.getLatestTradingDay()));

    assertEquals("Compare previous close", 226.37, aaplQuote.getPreviousClose(), delta);
    assertEquals("Compare change", 1.15, aaplQuote.getChange(), delta);
    assertEquals("Compare change %", "0.508%", aaplQuote.getChangePercent());
  }
}