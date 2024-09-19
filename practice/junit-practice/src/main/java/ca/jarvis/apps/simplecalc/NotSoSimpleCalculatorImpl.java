package ca.jarvis.apps.simplecalc;

public class NotSoSimpleCalculatorImpl implements  NotSoSimpleCalculator{
  private SimpleCalculator calc;

  public NotSoSimpleCalculatorImpl(SimpleCalculator calc) {
    this.calc = calc;
  }

  @Override
  public int power(int x, int y) {
    // TODO Auto-generated method stub
    return calc.power(x, y);
  }

  @Override
  public int abs(int x) {
    return calc.multiply(x, -1);
  }

  @Override
  public double sqrt(int x) {
    // TODO Auto-generated method stub
    return Math.sqrt(x);
  }
}
