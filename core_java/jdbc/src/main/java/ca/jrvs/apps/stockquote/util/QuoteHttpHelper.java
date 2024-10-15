package ca.jrvs.apps.stockquote.util;

import ca.jrvs.apps.stockquote.dto.Quote;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.sql.Timestamp;

public class QuoteHttpHelper {

  private final String apiKey;
  private final OkHttpClient client = new OkHttpClient();
  private final static Logger LOGGER = LoggerFactory.getLogger(QuoteHttpHelper.class);

  public QuoteHttpHelper(String apiKey) {
    this.apiKey = apiKey;
  }

  /**
   * Fetch latest quote data from Alpha Vantage endpoint
   *
   * @param symbol
   * @return Quote with latest data
   * @throws IllegalArgumentException - if no data was found for the given symbol
   */
  public Quote fetchQuoteInfo(String symbol) throws IllegalArgumentException {
    final String body = fetchQuoteJSON(symbol);

    // Status code of response is not 404 when the symbol is incorrect
    // Need to manually check for empty response
    if (body.replaceAll("\\s|\"", "").equals("{GlobalQuote:{}}")) {
      throw new IllegalArgumentException("Could not find any data for provided symbol '" + symbol + "'");
    }

    try {
      // Covert to Quote object
      ObjectMapper mapper = new ObjectMapper();
      SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
      mapper.setDateFormat(df);

      JsonNode quoteNode = mapper.readTree(body).path("Global Quote");
      Quote quote = mapper.readValue(quoteNode.toString(), Quote.class);
      quote.setTimestamp(Timestamp.from(Instant.now()));

      return quote;
    } catch (IOException e) {
      LOGGER.error("Could not deserialize JSON response for '{}'", symbol, e);
    }

    return null;
  }

  public String fetchQuoteJSON(String symbol) {
    final String url = "https://alpha-vantage.p.rapidapi.com/query?function=GLOBAL_QUOTE&symbol=" + symbol + "&datatype=json";

    Request request = new Request.Builder()
            .url(url)
            .header("X-RapidAPI-Key", apiKey)
            .header("X-RapidAPI-Host", "alpha-vantage.p.rapidapi.com")
            .build();

    try (Response response = client.newCall(request).execute()) {
      return response.body().string();
    } catch (IOException e) {
      LOGGER.error("Failed to fetch quote from API", e);
    }

    return null;
  }
}