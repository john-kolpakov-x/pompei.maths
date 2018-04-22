package pompei.maths.euler_calculations;

import java.math.BigInteger;

public class EulerCalculations6 {
  public static void main(String[] args) {
    BigInteger r = new BigInteger("1677479490238223823661446513");

    BigInteger a = new BigInteger("1507524066882038472584786800");
    BigInteger b = new BigInteger("1288056982586427591062203384");
    BigInteger c = new BigInteger("169218021322170204480680305");

    EulerCalc.calc(r, a, b, c);
  }

}
