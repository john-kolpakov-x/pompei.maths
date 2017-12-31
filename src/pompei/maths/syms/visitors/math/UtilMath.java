package pompei.maths.syms.visitors.math;

import java.math.BigInteger;

public class UtilMath {
  @SuppressWarnings("unused")
  public static long gcd(long a, long b) {
    if (a > b) return gcdInner(a, b);
    return gcdInner(b, a);
  }

  private static long gcdInner(long a, long b) {
    if (b == 0) return a;
    return gcdInner(b, a % b);
  }

  /**
   * Расширенный алгоритм Евклида <br/>
   * d = НОД(a,b), ax + by = d
   *
   * @param a входной параметр
   * @param b входной параметр
   * @return [d, x, y]
   */
  public static BigInteger[] gcdExt(BigInteger a, BigInteger b) {
    if (a == null) a = BigInteger.ZERO;
    if (b == null) b = BigInteger.ZERO;
    if (a.compareTo(b) < 0) return gcdExt(b, a);


    if (b.compareTo(BigInteger.ZERO) == 0) {
      BigInteger[] ret = new BigInteger[3];
      ret[0] = a;
      ret[1] = BigInteger.ONE;
      ret[2] = BigInteger.ZERO;
      return ret;
    }

    BigInteger x2 = BigInteger.ONE;
    BigInteger x1 = BigInteger.ZERO;

    BigInteger y2 = BigInteger.ZERO;
    BigInteger y1 = BigInteger.ONE;

    while (b.compareTo(BigInteger.ZERO) > 0) {
      BigInteger q = a.divide(b);

      BigInteger r = a.subtract(q.multiply(b));

      BigInteger x = x2.subtract(q.multiply(x1));
      BigInteger y = y2.subtract(q.multiply(y1));

      a = b;
      b = r;
      x2 = x1;
      x1 = x;
      y2 = y1;
      y1 = y;
    }

    {
      BigInteger[] ret = new BigInteger[3];
      ret[0] = a;
      ret[1] = x2;
      ret[2] = y2;
      return ret;
    }
  }
}
