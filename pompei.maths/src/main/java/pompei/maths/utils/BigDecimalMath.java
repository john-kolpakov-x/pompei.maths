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

      BigDecimal subtracted = newResult.subtract(result, mc);
      if (subtracted.compareTo(BigDecimal.ZERO) == 0) return result;

      result = newResult;

      i++;
      top = top.multiply(x, mc);
      bottom = bottom.multiply(BigInteger.valueOf(i));
    }
  }

  public final BigDecimal pow(BigDecimal x, BigInteger n) {

    int xZeroCompared = x.compareTo(BigDecimal.ZERO);
    int nZeroCompared = n.compareTo(BigInteger.ZERO);

    {
      if (xZeroCompared == 0 && nZeroCompared == 0) throw new IllegalArgumentException("Unknown result of 0^0");
      if (xZeroCompared == 0) return BigDecimal.ZERO;
      if (nZeroCompared == 0) return BigDecimal.ONE;
    }

    if (n.compareTo(BigInteger.ONE) == 0) return x;

    {
      if (nZeroCompared > 0) return powPlus(x, n);

      if (xZeroCompared < 0) throw new IllegalArgumentException("Unknown result of negative^negative");

      return powMinus(x, n);
    }
  }

  private BigDecimal powMinus(BigDecimal x, BigInteger n) {
    return null;
  }

  private static final BigInteger POW_PLUS_LIMIT = BigInteger.valueOf(13);

  private BigDecimal powPlus(BigDecimal x, BigInteger n) {
    if (n.compareTo(POW_PLUS_LIMIT) < 0) {
      BigDecimal result = x;
      for (int i = 0, N = n.intValue() - 1; i < N; i++) {
        result = result.multiply(x, mc);
      }
      return result;
    }

    {

      return null;
    }
  }
}
