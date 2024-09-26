package ca.jrvs.apps.stockquote;

public class Position {

  private String ticker; //id
  private int numOfShares;
  private double valuePaid; //total amount paid for shares

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
