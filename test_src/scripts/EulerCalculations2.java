package scripts;

import java.math.BigInteger;

public class EulerCalculations2 {
  public static void main(String[] args) {
    BigInteger r = new BigInteger("1259768473");

    BigInteger a = new BigInteger("1166705840");
    BigInteger b = new BigInteger("859396455");
    BigInteger c = new BigInteger("588903336");

    EulerCalc.calc(r, a, b, c);
  }
}
