package ca.jrvs.apps.stockquote.dto;

import java.util.Objects;

public class Position {

  private String ticker; //id
  private int numOfShares;
  private double valuePaid; //total amount paid for shares

  @Override
  public String toString() {
    return "Position{" +
            "ticker='" + ticker + '\'' +
            ", numOfShares=" + numOfShares +
            ", valuePaid=" + valuePaid +
            '}';
  }

  public String getTicker() {
    return ticker;
  }

  public void setTicker(String ticker) {
    this.ticker = ticker;
  }

  public int getNumOfShares() {
    return numOfShares;
  }

  public void setNumOfShares(int numOfShares) {
    this.numOfShares = numOfShares;
  }

  public double getValuePaid() {
    return valuePaid;
  }

  public void setValuePaid(double valuePaid) {
    this.valuePaid = valuePaid;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Position position = (Position) o;
    return numOfShares == position.numOfShares && Double.compare(position.valuePaid, valuePaid) == 0 && Objects.equals(ticker, position.ticker);
  }

  @Override
  public int hashCode() {
    return Objects.hash(ticker, numOfShares, valuePaid);
  }
}
