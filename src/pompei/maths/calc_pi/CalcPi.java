package pompei.maths.calc_pi;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public class CalcPi {
  final static MathContext mc = new MathContext(3000 + 1 + 7 * 8, RoundingMode.HALF_EVEN);
  final static BigDecimal delta = new BigDecimal("1E-" + mc.getPrecision());

  public static BigDecimal f(BigDecimal y) {
    var y2 = y.multiply(y);
    var y4 = y2.multiply(y2);
    return BigDecimal.ONE.subtract(y4).sqrt(mc).sqrt(mc);
  }

  public static void main(String[] args) {

    System.out.println("delta = " + delta);

    var _2 = BigDecimal.valueOf(2);
    var _4 = BigDecimal.valueOf(4);

    var a = BigDecimal.ONE;
    var b = BigDecimal.ONE.divide(_2.sqrt(mc), mc);
    var t = new BigDecimal("0.25");
    var p = BigDecimal.ONE;

    int count = 0;

    while (true) {

      var a1 = a.add(b).divide(_2, mc);

      var b1 = a.multiply(b).sqrt(mc);

      var deltaA = a1.subtract(a, mc);

      var t1 = t.subtract(p.multiply(deltaA.multiply(deltaA, mc), mc), mc);

      var p1 = p.multiply(_2, mc);

      a = a1;
      b = b1;
      t = t1;
      p = p1;

      count++;
      // System.out.println("ZyNd1a1GBL :: count = " + count);

      if (a.subtract(b, mc).abs(mc).compareTo(delta) <= 0) {
        break;
      }

      if (count >= 100) {
        break;
      }
    }

    var ab = a.add(b);
    var pi = ab.multiply(ab).divide(_4.multiply(t), mc);
    System.out.println("count = [ " + count + " ]");

    System.out.println("PI =");
    new BdPrint(System.out).print(pi);

  }

}
