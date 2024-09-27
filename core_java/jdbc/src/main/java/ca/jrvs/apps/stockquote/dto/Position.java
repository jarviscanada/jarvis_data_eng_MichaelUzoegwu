package ca.jrvs.apps.stockquote.dto;

public class Position {

  private final String ticker; //id
  private final int numOfShares;
  private final double valuePaid; //total amount paid for shares

  public Position(String ticker, int numOfShares, double valuePaid) {
    this.ticker = ticker;
    this.numOfShares = numOfShares;
    this.valuePaid = valuePaid;
  }

  @Override
  public String toString() {
    return "Position{" +
            "ticker='" + ticker + '\'' +
            ", numOfShares=" + numOfShares +
            ", valuePaid=" + valuePaid +
            '}';
  }
}
