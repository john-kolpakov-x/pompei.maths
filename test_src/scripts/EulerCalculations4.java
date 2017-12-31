package scripts;

import java.math.BigInteger;

public class EulerCalculations4 {
  public static void main(String[] args) {
    BigInteger r = new BigInteger("1871713857");

    BigInteger a = new BigInteger("1593513080");
    BigInteger b = new BigInteger("1553556440");
    BigInteger c = new BigInteger("92622401");

    EulerCalc.calc(r, a, b, c);
  }
}
