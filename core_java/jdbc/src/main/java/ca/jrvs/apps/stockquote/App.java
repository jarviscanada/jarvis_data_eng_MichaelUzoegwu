package ca.jrvs.apps.stockquote;

import ca.jrvs.apps.stockquote.dao.QuoteHttpHelper;

public class App {

  private static String apiKey;

  public static void main(String[] args) {
    if (args.length < 1) {
      System.out.println("Incorrect usage, missing API-Key parameter.");
    } else if (args.length > 1) {
      System.out.println("Incorrect usage, too many parameters.");
    }

    apiKey = args[0];
    QuoteHttpHelper helper = new QuoteHttpHelper();
    helper.fetchQuoteInfo("AAPL");
  }

  public static String getApiKey() {
    return apiKey;
  }
}
