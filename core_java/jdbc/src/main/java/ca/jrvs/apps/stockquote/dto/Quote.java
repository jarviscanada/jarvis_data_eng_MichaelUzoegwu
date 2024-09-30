package ca.jrvs.apps.stockquote.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.sql.Timestamp;
import java.sql.Date;
import java.util.Calendar;
import java.util.Objects;

public class Quote {
  @JsonProperty("01. symbol")
  private String ticker;

  @JsonProperty("02. open")
  private double open;

  @JsonProperty("03. high")
  private double high;

  @JsonProperty("04. low")
  private double low;

  @JsonProperty("05. price")
  private double price;

  @JsonProperty("06. volume")
  private int volume;
  
  @JsonProperty("07. latest trading day")
  private Date latestTradingDay;

  @JsonProperty("08. previous close")
  private double previousClose;

  @JsonProperty("09. change")
  private double change;

  @JsonProperty("10. change percent")
  private String changePercent;

  private Timestamp timestamp;

  public String getTicker() {
    return ticker;
  }

  public void setTicker(String ticker) {
    this.ticker = ticker;
  }

  public double getOpen() {
    return open;
  }

  public void setOpen(double open) {
    this.open = open;
  }

  public double getHigh() {
    return high;
  }

  public void setHigh(double high) {
    this.high = high;
  }

  public double getLow() {
    return low;
  }

  public void setLow(double low) {
    this.low = low;
  }

  public double getPrice() {
    return price;
  }

  public void setPrice(double price) {
    this.price = price;
  }

  public int getVolume() {
    return volume;
  }

  public void setVolume(int volume) {
    this.volume = volume;
  }

  public Date getLatestTradingDay() {
    return latestTradingDay;
  }

  public void setLatestTradingDay(Date latestTradingDay) {
    // Setting date to not include time information.
    // This ensures consistency and avoids issues with time zone differences in the database.
    // Without this, dates might be stored with unintended time components, causing discrepancies.
    Calendar cal = Calendar.getInstance();
    cal.setTime(latestTradingDay);
    cal.set(Calendar.HOUR_OF_DAY, 0);
    cal.set(Calendar.MINUTE, 0);
    cal.set(Calendar.SECOND, 0);
    cal.set(Calendar.MILLISECOND, 0);
    this.latestTradingDay = new Date(cal.getTimeInMillis());
  }

  public double getPreviousClose() {
    return previousClose;
  }

  public void setPreviousClose(double previousClose) {
    this.previousClose = previousClose;
  }

  public double getChange() {
    return change;
  }

  public void setChange(double change) {
    this.change = change;
  }

  public String getChangePercent() {
    return changePercent;
  }

  public void setChangePercent(String changePercent) {
    this.changePercent = changePercent;
  }

  public Timestamp getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(Timestamp timestamp) {
    this.timestamp = timestamp;
  }

  @Override
  public String toString() {
    return "Quote{" +
            "ticker='" + ticker + '\'' +
            ", open=" + open +
            ", high=" + high +
            ", low=" + low +
            ", price=" + price +
            ", volume=" + volume +
            ", latestTradingDay=" + latestTradingDay +
            ", previousClose=" + previousClose +
            ", change=" + change +
            ", changePercent='" + changePercent + '\'' +
            ", timestamp=" + timestamp +
            '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    final double TOLERANCE = 1e-9;
    Quote quote = (Quote) o;

    return Math.abs(quote.open - open) < TOLERANCE &&
            Math.abs(quote.high - high) < TOLERANCE &&
            Math.abs(quote.low - low) < TOLERANCE &&
            Math.abs(quote.price - price) < TOLERANCE &&
            volume == quote.volume &&
            Math.abs(quote.previousClose - previousClose) < TOLERANCE &&
            Math.abs(quote.change - change) < TOLERANCE &&
            Objects.equals(ticker, quote.ticker) &&
            Objects.equals(latestTradingDay, quote.latestTradingDay) &&
            Objects.equals(changePercent, quote.changePercent) &&
            Objects.equals(timestamp, quote.timestamp);
  }

  @Override
  public int hashCode() {
    return Objects.hash(ticker, open, high, low, price, volume, latestTradingDay, previousClose, change, changePercent, timestamp);
  }
}