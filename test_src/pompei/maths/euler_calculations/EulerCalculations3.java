package pompei.maths.euler_calculations;

import java.math.BigInteger;

public class EulerCalculations3 {
  public static void main(String[] args) {
    BigInteger r = new BigInteger("1787882337");

    BigInteger a = new BigInteger("1662997663");
    BigInteger b = new BigInteger("1237796960");
    BigInteger c = new BigInteger("686398000");

    EulerCalc.calc(r, a, b, c);
  }
}
