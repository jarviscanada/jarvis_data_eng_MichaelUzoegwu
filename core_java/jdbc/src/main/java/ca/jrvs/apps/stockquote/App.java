package ca.jrvs.apps.stockquote;

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
    Quote quote = helper.fetchQuoteInfo("AAPL");
    System.out.println(quote);
  }

  public static String getApiKey() {
    return apiKey;
  }
}
