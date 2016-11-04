package pompei.maths.utils;

import org.fest.assertions.Delta;
import org.testng.annotations.Test;

import java.math.BigDecimal;

import static org.fest.assertions.Assertions.assertThat;

public class BigDecimalMathTest {
  @Test
  public void exp1() throws Exception {
    BigDecimalMath math = new BigDecimalMath(8);

    BigDecimal e = math.exp(BigDecimal.valueOf(1));

    assertThat(e).isNotNull();
    assertThat(e.doubleValue()).isEqualTo(Math.E, Delta.delta(1e-7));
  }

  @Test
  public void exp2() throws Exception {
    BigDecimalMath math = new BigDecimalMath(8);

    BigDecimal e = math.exp(BigDecimal.valueOf(-1));

    assertThat(e).isNotNull();
    assertThat(e.doubleValue()).isEqualTo(1.0 / Math.E, Delta.delta(1e-7));
  }

  @SuppressWarnings("unused")
  private String show(BigDecimal a) {
    return a + " (p/scale " + a.precision() + "/" + a.scale() + ")";
  }
}