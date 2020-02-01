package pompei.maths.utils;

import org.fest.assertions.Delta;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.math.BigInteger;

import static org.fest.assertions.Assertions.assertThat;

public class BigDecimalMathMyTest {
  @Test
  public void exp_one() {
    BigDecimalMathMy math = new BigDecimalMathMy(12);

    BigDecimal e = math.exp(BigDecimal.valueOf(1));

    System.out.println("e = " + show(e));

    assertThat(e).isNotNull();
    assertThat(e.doubleValue()).isEqualTo(Math.E, Delta.delta(1e-10));
  }

  @Test
  public void exp_minusOne() {
    BigDecimalMathMy math = new BigDecimalMathMy(12);

    BigDecimal e = math.exp(BigDecimal.valueOf(-1));

    assertThat(e).isNotNull();
    assertThat(e.doubleValue()).isEqualTo(1.0 / Math.E, Delta.delta(1e-10));

    System.out.println(show(e));
  }

  @Test
  public void exp_bigValue() {
    BigDecimalMathMy math = new BigDecimalMathMy(12);

    double expected = Math.exp(230);
    System.out.println("expected = " + expected);


    BigDecimal actual = math.exp(BigDecimal.valueOf(230));

    System.out.println(show(actual));
    assertThat(actual.doubleValue()).isEqualTo(expected, Delta.delta(1e89));

  }

  @Test(enabled = false)
  public void exp_long() {
    BigDecimalMathMy math = new BigDecimalMathMy(30);

    BigDecimal actual = math.exp(new BigDecimal("543275425436542543"));

    System.out.println(show(actual));

  }

  @Test
  public void doing_half() {

    BigInteger a = new BigInteger("43201590043043024504305403403210534205");

    BigInteger a2 = a.mod(BigInteger.TWO);

    System.out.println("a  = " + a);
    System.out.println("a2 = " + a2);

  }

  @SuppressWarnings("unused")
  private String show(BigDecimal a) {
    return a + " (p/scale " + a.precision() + "/" + a.scale() + ")";
  }
}
