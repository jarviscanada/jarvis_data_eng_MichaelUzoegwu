package ca.jrvs.apps.stockquote;

import ca.jrvs.apps.stockquote.dao.QuoteDao;
import ca.jrvs.apps.stockquote.dto.Quote;
import ca.jrvs.apps.stockquote.util.QuoteHttpHelper;

import java.util.Optional;

public class QuoteService {
  final private long QUOTE_TTL = 3600000;
  private final QuoteHttpHelper httpHelper;
  private final QuoteDao dao;

  public QuoteService(QuoteHttpHelper httpHelper, QuoteDao dao) {
    this.httpHelper = httpHelper;
    this.dao = dao;
  }

  //TODO: Update quote service tests

  /**
   * Fetches latest quote data
   *
   * @param ticker quote's symbol
   * @return Latest quote information or empty optional if ticker symbol not found
   */
  public Optional<Quote> fetchQuoteData(String ticker) {
    // First check if a fresh enough quote is in the database
    // and return it if so
    Optional<Quote> dbQuoteOpt = dao.findById(ticker);
    if (dbQuoteOpt.isPresent()) {
      final long dbQuoteTime = dbQuoteOpt.get().getTimestamp().getTime();
      if (System.currentTimeMillis() - dbQuoteTime < QUOTE_TTL) {
        return dbQuoteOpt;
      }
    }

    try {
      Quote quote = httpHelper.fetchQuoteInfo(ticker);
      dao.save(quote);
      return Optional.of(quote);
    } catch (IllegalArgumentException e) {
      return Optional.empty();
    }
  }
}