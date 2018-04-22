package pompei.maths.euler_calculations;

import java.math.BigInteger;

public class EulerCalculations5 {
  public static void main(String[] args) {
    BigInteger r = new BigInteger("422481");

    BigInteger a = new BigInteger("414560");
    BigInteger b = new BigInteger("217519");
    BigInteger c = new BigInteger("95800");

    EulerCalc.calc(r, a, b, c);
  }
}
