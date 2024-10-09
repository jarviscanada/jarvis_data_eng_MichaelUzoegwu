package ca.jarvis.apps.simplecalc;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class NotSoSimpleCalculatorImplTest {

  NotSoSimpleCalculator calc;

  @Mock
  SimpleCalculator mockSimpleCalc;

  @Before
  public void setUp() throws Exception {
    calc = new NotSoSimpleCalculatorImpl(mockSimpleCalc);
  }

  @Test
  public void power() {
    when(mockSimpleCalc.power(anyInt(), anyInt())).thenAnswer((invocation) -> {
      int x = invocation.getArgument(0);
      int y = invocation.getArgument(1);

      return (int) Math.pow(x, y);
    });

    for (int i = 0; i < 10; i++){
      assertEquals(i*i, mockSimpleCalc.power(i, 2));
    }
  }

  @Test
  public void abs() {
    System.out.println(calc.abs(10));

    when(mockSimpleCalc.multiply(anyInt(), anyInt())).thenAnswer((invocation) -> {
      int arg1 = invocation.getArgument(0);
      int arg2 = invocation.getArgument(1);
      return arg1 * arg2;
    });

    assertEquals(10, calc.abs(10));
  }

  @Test
  public void sqrt() {
    assertEquals(2, calc.sqrt(4), 0.0001);
  }
}