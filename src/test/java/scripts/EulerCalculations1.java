package scripts;

import java.math.BigInteger;

public class EulerCalculations1 {
  public static void main(String[] args) {
    BigInteger r = new BigInteger("873822121");

    BigInteger a = new BigInteger("769321280");
    BigInteger b = new BigInteger("606710871");
    BigInteger c = new BigInteger("558424440");

    EulerCalc.calc(r, a, b, c);
  }
}
