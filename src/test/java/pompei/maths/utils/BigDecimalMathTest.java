package pompei.maths.utils;

import java.math.BigDecimal;
import org.fest.assertions.Delta;
import org.testng.annotations.Test;


import static org.fest.assertions.Assertions.assertThat;

public class BigDecimalMathTest {
  @Test
  public void exp_one() throws Exception {
    BigDecimalMath math = new BigDecimalMath(12);

    BigDecimal e = math.exp(BigDecimal.valueOf(1));

    System.out.println("e = " + show(e));

    assertThat(e).isNotNull();
    assertThat(e.doubleValue()).isEqualTo(Math.E, Delta.delta(1e-10));
  }

  @Test
  public void exp_minusOne() throws Exception {
    BigDecimalMath math = new BigDecimalMath(12);

    BigDecimal e = math.exp(BigDecimal.valueOf(-1));

    assertThat(e).isNotNull();
    assertThat(e.doubleValue()).isEqualTo(1.0 / Math.E, Delta.delta(1e-10));

    System.out.println(show(e));
  }

  @Test
  public void exp_bigValue() throws Exception {
    BigDecimalMath math = new BigDecimalMath(12);

    double expected = Math.exp(230);
    System.out.println("expected = " + expected);


    BigDecimal actual = math.exp(BigDecimal.valueOf(230));

    System.out.println(show(actual));
    assertThat(actual.doubleValue()).isEqualTo(expected, Delta.delta(1e89));

  }

  @SuppressWarnings("unused")
  private String show(BigDecimal a) {
    return a + " (p/scale " + a.precision() + "/" + a.scale() + ")";
  }
}
