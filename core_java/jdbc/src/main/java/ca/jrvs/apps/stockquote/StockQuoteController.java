package ca.jrvs.apps.stockquote;

import ca.jrvs.apps.stockquote.dao.PositionDao;
import ca.jrvs.apps.stockquote.dto.Position;
import ca.jrvs.apps.stockquote.dto.Quote;

import java.util.*;

public class StockQuoteController {

  private final String BUY_MODE = "buy";
  private final String SELL_MODE = "sell";
  private final String CHECK_POSITION_MODE = "check-position";
  private final String CHECK_STOCK_MODE = "check-stock";
  private final Map<String, Integer> modeArgLengths = new HashMap<>();
  private Set<String> modes = new HashSet<>();

  QuoteService quoteService;
  PositionService positionService;
  PositionDao positionDao;

  public StockQuoteController(QuoteService quoteService, PositionService positionService, PositionDao positionDao) {
    this.quoteService = quoteService;
    this.positionService = positionService;
    this.positionDao = positionDao;

    // Used to verify validity of arguments passed in
    modes = new HashSet<>(Set.of(BUY_MODE, SELL_MODE, CHECK_POSITION_MODE, CHECK_STOCK_MODE));
    modeArgLengths.put(BUY_MODE, 3);
    modeArgLengths.put(SELL_MODE, 2);
    modeArgLengths.put(CHECK_POSITION_MODE, 2);
    modeArgLengths.put(CHECK_STOCK_MODE, 2);
  }

  public void initClient(String[] args) {

    if (args.length == 0 || !modes.contains(args[0]) || args.length != modeArgLengths.get(args[0])) {
      System.out.println("Invalid arguments. Example usage:");
      System.out.println("Buy                 : java App buy <ticker> <number_of_shares>");
      System.out.println("Sell                : java App sell <ticker>");
      System.out.println("Check position info : java App check-position <ticker>");
      System.out.println("Check stock info    : java App check-stock <ticker>");
      return;
    }

    final String mode = args[0];
    final String ticker = args[1];


    switch (mode) {
      case BUY_MODE -> {
        try {
          handleBuyMode(ticker, Integer.parseInt(args[2]));
        } catch (NumberFormatException e) {
          System.out.println("Invalid number of shares. Could not parse into number.");
        }
      }
      case SELL_MODE -> handleSellMode(ticker);
      case CHECK_STOCK_MODE -> handleCheckStock(ticker);
      case CHECK_POSITION_MODE -> handleCheckPosition(ticker);
    }
  }

  private void handleBuyMode(String ticker, int numberOfShares) {
    Optional<Quote> quote = quoteService.fetchQuoteData(ticker);
    if (quote.isEmpty()) {
      System.out.printf("Could not process order. No information found on '%s' symbol.%n", ticker);
      return;
    }
    Position position = positionService.buy(ticker, numberOfShares, quote.get().getPrice());
    System.out.printf("Successfully processed BUY order of %d share(s) of '%s' for $%.2f.%n", numberOfShares,
            ticker,
            quote.get().getPrice() * numberOfShares);
    System.out.println("Current position:");
    printPosition(position, 0.0);
  }

  private void handleSellMode(String ticker) {
    Optional<Position> positionOpt = positionService.getPosition(ticker);
    if (positionOpt.isEmpty()) {
      System.out.printf("Could not process order. You do not currently possess any shares in '%s'.", ticker);
      return;
    }

    Quote quote = quoteService.fetchQuoteData(ticker).orElseThrow();

    positionService.sell(ticker);
    System.out.printf("Successfully processed SELL order of all %d shares of '%s' for $%.2f.%n",
            positionOpt.get().getNumOfShares(),
            ticker,
            quote.getPrice() * positionOpt.get().getNumOfShares()
    );
  }

  private void handleCheckStock(String ticker) {
    Optional<Quote> quoteOpt = quoteService.fetchQuoteData(ticker);
    if (quoteOpt.isEmpty()) {
      System.out.printf("Could not process order. No information found on '%s'.", ticker);
      return;
    }
    printQuote(quoteOpt.get());
  }

  private void handleCheckPosition(String ticker) {
    Optional<Position> positionOpt = positionService.getPosition(ticker);
    if (positionOpt.isEmpty()) {
      System.out.printf("You do not have any shares in '%s'.", ticker);
      return;
    }

    Quote quote = quoteService.fetchQuoteData(ticker).orElseThrow();
    Double relativePrice = positionOpt.get().getNumOfShares() * quote.getPrice() - positionOpt.get().getValuePaid();

    printPosition(positionOpt.get(), relativePrice);
  }

  private void printPosition(Position position, Double relativePrice) {
    String sign = relativePrice >= 0 ? "+" : "-";
    System.out.printf("----------%s----------%n", position.getTicker());
    System.out.printf("Total Number of Shares Owned : %d%n", position.getNumOfShares());
    System.out.printf("Total value paid             : $%.2f%n", position.getValuePaid());
    System.out.printf("Money made if sold           : %s$%.2f%n", sign, relativePrice);
    System.out.printf("----------%s----------%n", position.getTicker());
  }

  private void printQuote(Quote quote) {
    System.out.printf("----------%s----------%n", quote.getTicker());
    System.out.printf("Opening price      : $%.2f%n", quote.getOpen());
    System.out.printf("Highest price      : $%.2f%n", quote.getHigh());
    System.out.printf("Lowest price       : $%.2f%n", quote.getLow());
    System.out.printf("Current price      : $%.2f%n", quote.getPrice());
    System.out.printf("Volume             : %d%n", quote.getVolume());
    System.out.printf("Latest trading day : %s%n", quote.getLatestTradingDay().toString());
    System.out.printf("Previous close     : $%.2f%n", quote.getPreviousClose());
    System.out.printf("Change             : $%.2f%n", quote.getChange());
    System.out.printf("Change percent     : %s%n", quote.getChangePercent());
    System.out.printf("----------%s----------%n", quote.getTicker());
  }
}