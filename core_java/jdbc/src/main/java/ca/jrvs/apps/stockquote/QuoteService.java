package ca.jrvs.apps.stockquote;

import ca.jrvs.apps.stockquote.dao.QuoteDao;
import ca.jrvs.apps.stockquote.dto.Quote;
import ca.jrvs.apps.stockquote.util.QuoteHttpHelper;

import java.util.Optional;

public class QuoteService {
  final private QuoteHttpHelper httpHelper;
  private QuoteDao dao;

  public QuoteService(QuoteHttpHelper httpHelper) {
    this.httpHelper = httpHelper;
  }

  /**
   * Fetches latest quote data from endpoint
   *
   * @param ticker quote's symbol
   * @return Latest quote information or empty optional if ticker symbol not found
   */
  public Optional<Quote> fetchQuoteDataFromAPI(String ticker) {
    try {
      Quote quote = httpHelper.fetchQuoteInfo(ticker);
      return Optional.of(quote);
    } catch (IllegalArgumentException e) {
      return Optional.empty();
    }
  }
}
