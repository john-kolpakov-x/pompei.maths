package pompei.maths.utils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.math.RoundingMode;

public class BigDecimalMath {
  private final MathContext mc;

  public BigDecimalMath(int precision) {
    mc = new MathContext(precision, RoundingMode.HALF_UP);
  }

  public final BigDecimal exp(BigDecimal x) {

    int cmp = x.compareTo(BigDecimal.ZERO);

    if (cmp == 0) return BigDecimal.ONE;

    if (cmp < 0) return BigDecimal.ONE.divide(expPositive(x.negate()), mc);

    return expPositive(x);
  }

  public static void main(String[] args) {
    BigDecimal bd = BigDecimal.valueOf(234.3242);
    System.out.println(bd.ulp());
  }
  
  private BigDecimal expPositive(BigDecimal x) {
    
    return expPositiveSmall(x);
  }

  private BigDecimal expPositiveSmall(BigDecimal x) {
    BigDecimal result = BigDecimal.ONE;

    BigDecimal top = x;
    BigInteger bottom = BigInteger.ONE;
    long i = 1;

    while (true) {
      BigDecimal delta = top.divide(new BigDecimal(bottom), mc);

      BigDecimal newResult = result.add(delta, mc);

      if (newResult.subtract(result, mc).compareTo(BigDecimal.ZERO) == 0) return result;

      result = newResult;

      i++;
      top = top.multiply(x, mc);
      bottom = bottom.multiply(BigInteger.valueOf(i));
    }
  }
}
