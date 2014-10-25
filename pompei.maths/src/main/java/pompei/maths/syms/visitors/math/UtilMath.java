package pompei.maths.syms.visitors.math;

public class UtilMath {
  public static long gcd(long a, long b) {
    if (a > b) return gcdInner(a, b);
    return gcdInner(b, a);
  }
  
  private static long gcdInner(long a, long b) {
    if (b == 0) return a;
    return gcdInner(b, a % b);
  }
}
