package ca.jarvis.apps.simplecalc;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class SimpleCalculatorImplTest {

  SimpleCalculator calculator;

  @Before
  public void setUp() throws Exception {
    calculator = new SimpleCalculatorImpl();
  }

  @Test
  public void add() {
    assertEquals("Adding 3 and 5 together",8, calculator.add(3, 5));
  }

  @Test
  public void subtract() {
    assertEquals("10 - 7 = 3",3, calculator.subtract(10, 7));
  }

  @Test
  public void multiply() {
    assertEquals("8 * 5 = 40",40,  calculator.multiply(8, 5));
  }

  @Test
  public void divide() {
    assertEquals("10 / 2 = 5", 5.0, calculator.divide(10, 2), 0.001);
  }

  @Test
  public void power() {
    assertEquals("3^3 = 27", 27.0, calculator.power(3, 3), 0.001);
  }

  @Test
  public void abs() {
    assertEquals("abs(-5) = 5", 5.0, calculator.abs(-5), 0.001);
  }
}