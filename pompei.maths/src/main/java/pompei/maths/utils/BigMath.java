package pompei.maths.utils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;

public class BigMath {

  public final MathContext mc;

  public BigMath(int precisions) {
    this(new MathContext(precisions));
  }

  public BigMath(MathContext mc) {
    this.mc = mc;
  }

  public BigMath copy() {
    return new BigMath(mc);
  }

  private BigDecimal e = null;

  public BigDecimal e() {

    if (e != null) return e;

    long n = 1;
    BigInteger down = BigInteger.ONE;
    BigDecimal result = BigDecimal.ONE;

    while (true) {

      BigDecimal adding = BigDecimal.ONE.divide(new BigDecimal(down), mc);

      BigDecimal newResult = result.add(adding, mc);

      if (newResult.compareTo(result) == 0) return e = result;

      result = newResult;

      n++;
      down = down.multiply(BigInteger.valueOf(n));
    }
  }

  private final BigDecimal e2nArray[] = new BigDecimal[100];

  /**
   * Calculates e^(2^n)
   *
   * @param n argument
   * @return result
   */
  public BigDecimal e2n(int n) {
    if (n < 0) throw new IllegalArgumentException("n = " + n);
    if (n == 0) return e();

    final int index = n - 1;

    BigDecimal en = e2nArray[0];
    if (en == null) en = e2nArray[0] = e().multiply(e(), mc);
    if (index == 0) return en;

    if (index >= e2nArray.length) {
      BigDecimal prev = e2n(n - 1);
      return prev.multiply(prev, mc);
    }

    for (int i = 1; i <= index; i++) {
      BigDecimal prev = en;
      en = e2nArray[i];
      if (en != null) continue;
      e2nArray[i] = en = prev.multiply(prev, mc);
    }

    return en;
  }

  public BigDecimal exp(BigDecimal a) {
    long n = 1;
    BigInteger down = BigInteger.ONE;
    BigDecimal result = BigDecimal.ONE;

    BigDecimal top = a;

    while (true) {

      BigDecimal adding = top.divide(new BigDecimal(down), mc);

      BigDecimal newResult = result.add(adding, mc);

      if (newResult.compareTo(result) == 0) return result;

      result = newResult;

      top = top.multiply(a, mc);
      n++;
      down = down.multiply(BigInteger.valueOf(n));
    }
  }

  private static final BigDecimal TWO = new BigDecimal("2");

  public BigDecimal sqrt(BigDecimal a) {

    BigDecimal value = BigDecimal.ONE;

    while (true) {

      BigDecimal newValue = value.add(a.divide(value, mc), mc).divide(TWO, mc);

      if (newValue.compareTo(value) == 0) return value;

      value = newValue;
    }

  }

  private BigDecimal pi = null;

  public BigDecimal pi() {
    if (pi != null) return pi;

    int k = 0;

    boolean doAdding = true;
    BigInteger fact_k = BigInteger.ONE;
    BigInteger fact_3k = BigInteger.ONE;
    BigInteger fact_6k = BigInteger.ONE;

    BigInteger rightTop = new BigInteger("13591409");
    final BigInteger rightTopAdder = new BigInteger("545140134");

    BigInteger rightBottom = BigInteger.ONE;
    final BigInteger rightBottomMul
      = new BigInteger("640320")
      .multiply(new BigInteger("640320"))
      .multiply(new BigInteger("640320"));

    BigDecimal result = BigDecimal.ZERO;

    while (true) {

      BigInteger fact_k3 = fact_k.multiply(fact_k).multiply(fact_k);

      BigInteger top = fact_6k.multiply(rightTop);

      BigInteger bottom = fact_k3.multiply(fact_3k).multiply(rightBottom);

      BigDecimal element = new BigDecimal(top).divide(new BigDecimal(bottom), mc);

      BigDecimal newResult = doAdding ? result.add(element, mc) : result.subtract(element, mc);

      if (newResult.compareTo(result) == 0) return pi = finishPi(result);

      result = newResult;

      k++;
      doAdding = !doAdding;

      {
        int _6k = 6 * k;
        fact_6k = fact_6k
          .multiply(BigInteger.valueOf(_6k))
          .multiply(BigInteger.valueOf(_6k - 1))
          .multiply(BigInteger.valueOf(_6k - 2))
          .multiply(BigInteger.valueOf(_6k - 3))
          .multiply(BigInteger.valueOf(_6k - 4))
          .multiply(BigInteger.valueOf(_6k - 5))
        ;
      }

      fact_k = fact_k.multiply(BigInteger.valueOf(k));

      {
        int _3k = 3 * k;
        fact_3k = fact_3k
          .multiply(BigInteger.valueOf(_3k))
          .multiply(BigInteger.valueOf(_3k - 1))
          .multiply(BigInteger.valueOf(_3k - 2))
        ;
      }

      rightTop = rightTop.add(rightTopAdder);
      rightBottom = rightBottom.multiply(rightBottomMul);
    }

  }

  private BigDecimal finishPi(BigDecimal result) {
    BigDecimal bottom = sqrt(new BigDecimal("10005")).multiply(result, mc);

    return new BigDecimal("4270934400").divide(bottom, mc);
  }

  public BigDecimal border(BigDecimal value, BigDecimal from, BigDecimal to) {
    if (to.compareTo(value) < 0) {
      BigDecimal delta = to.subtract(from);
      BigDecimal distance = value.subtract(from, mc);
      return from.add(cut(distance, delta), mc);
    }
    if (from.compareTo(value) > 0) {
      BigDecimal delta = to.subtract(from);
      BigDecimal distance = to.subtract(value, mc);
      return to.subtract(cut(distance, delta), mc);
    }
    return value;
  }

  public BigDecimal cut(BigDecimal distance, BigDecimal delta) {
    if (distance.compareTo(delta) <= 0) return distance;

    int numberPrecision = distance.precision() - distance.scale() - delta.precision() + delta.scale();
    if (numberPrecision <= 0) numberPrecision = 1;
    final BigDecimal number = distance.divide(delta, new MathContext(numberPrecision));

    BigDecimal numberCut = new BigDecimal(number.toBigInteger());

    BigDecimal numberDistance = delta.multiply(numberCut);

    BigDecimal ret = distance.subtract(numberDistance);
    while (ret.compareTo(BigDecimal.ZERO) < 0) ret = ret.add(delta, mc);
    while (ret.compareTo(delta) >= 0) ret = ret.subtract(delta, mc);
    return ret;
  }

  public BigDecimal cos(BigDecimal x) {
    BigDecimal pi = pi();
    BigDecimal pi2 = pi.divide(TWO, mc);

    x = border(x, pi.negate().subtract(pi2), pi2).add(pi2);

    if (x.compareTo(BigDecimal.ZERO) < 0) return doSin(x.negate()).negate();

    return doSin(x);
  }

  public BigDecimal sin(BigDecimal x) {
    BigDecimal pi = pi();

    x = border(x, pi().negate(), pi);

    if (x.compareTo(BigDecimal.ZERO) < 0) return doSin(x.negate()).negate();

    return doSin(x);
  }

  private BigDecimal doSin(BigDecimal x) {
    BigDecimal pi = pi();
    BigDecimal pi2 = pi.divide(TWO, mc);

    if (pi2.compareTo(x) < 0) {
      return calcSin(pi.subtract(x, mc));
    }

    return calcSin(x);
  }

  private BigDecimal calcSin(BigDecimal x) {

    BigInteger fact = new BigInteger("6");
    int n = 3;

    BigDecimal result = x;

    BigDecimal x2 = x.multiply(x, mc);

    BigDecimal top = x.multiply(x2, mc);

    boolean adding = false;

    while (true) {

      BigDecimal element = top.divide(new BigDecimal(fact), mc);

      BigDecimal newResult = adding ? result.add(element, mc) : result.subtract(element, mc);

      if (newResult.compareTo(result) == 0) return result;

      result = newResult;

      n += 2;
      fact = fact.multiply(BigInteger.valueOf(n)).multiply(BigInteger.valueOf(n - 1));

      top = top.multiply(x2, mc);
      adding = !adding;
    }

  }

  public BigDecimal ln(BigDecimal x) {
    if (x == null) throw new NullPointerException("x == null");
    if (x.compareTo(BigDecimal.ZERO) <= 0) throw new IllegalArgumentException("x = " + x);

    {
      int cmp = x.compareTo(BigDecimal.ONE);
      if (cmp == 0) return BigDecimal.ZERO;
      if (cmp < 0) return lnMoreOne(BigDecimal.ONE.divide(x, mc)).negate();
      return lnMoreOne(x);
    }


  }

  private BigDecimal lnMoreOne(BigDecimal x) {
    BigInteger cel = BigInteger.ZERO;

    out:
    while (true) {
      in:
      for (int n = 0; ; n++) {
        BigDecimal tmp = e2n(n);
        if (tmp.compareTo(x) <= 0) continue in;

        if (n == 0) break out;

        {
          cel = cel.add(BigInteger.ONE.shiftLeft(n - 1));
          x = x.divide(e2n(n - 1), mc);
          continue out;
        }
      }
    }

    BigDecimal tmp = lnLessE(x);
    return tmp.add(new BigDecimal(cel), mc);
  }

  private BigDecimal lnLessE(BigDecimal x) {
    BigDecimal y = x.subtract(BigDecimal.ONE, mc).divide(x.add(BigDecimal.ONE, mc), mc);
    BigDecimal y2 = y.multiply(y, mc);

    BigDecimal top = y.multiply(y2, mc);
    long bottom = 3;

    BigDecimal result = y;

    while (true) {

      BigDecimal element = top.divide(new BigDecimal(bottom), mc);
      BigDecimal newResult = result.add(element, mc);

      if (newResult.compareTo(result) == 0) return result.multiply(TWO, mc);

      result = newResult;

      top = top.multiply(y2, mc);
      bottom += 2;
    }

  }

  /**
   * Calculates a^b
   *
   * @param a base
   * @param b exponent
   * @return result
   */
  public BigDecimal pow(BigDecimal a, BigDecimal b) {
    return exp(b.multiply(ln(a), mc));
  }
}
