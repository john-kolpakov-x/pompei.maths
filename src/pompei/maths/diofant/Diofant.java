package pompei.maths.diofant;

import pompei.maths.bdmath.Rational;

public class Diofant {

  /**
   * y^2 = x^3 +ax + b
   */
  public final Rational a, b;
  public final Dot P;

  public Diofant(Rational a, Rational b, Dot P) {
    this.a = a;
    this.b = b;
    this.P = P;
    check();
  }

  @Override
  public String toString() {
    return "Diofant{" +
               "a=" + a +
               ", b=" + b +
               ", P=" + P +
               '}';
  }

  private void check() {

    var x_3 = P.x.mul(P.x).mul(P.x);
    var ax = a.mul(P.x);
    var x3axb = x_3.plus(ax).plus(b);

    var y2 = P.y.mul(P.y);

    var delta = y2.minus(x3axb);

    if (delta.compareTo(Rational.ZERO) != 0) {
      throw new RuntimeException("ckTAPkgViP :: Incorrect diofant");
    }

  }

  public Dot plus(Dot T1, Dot T2) {

    if (T1.equals(T2)) {
      return doubling(T1);
    }

    throw new RuntimeException("298NevA7yA :: need to implement");
  }

  private Dot doubling(Dot T) {

    var kTop = (T.x).mul(T.x).mul(3).plus(a);
    var kBottom = T.y.mul(2);

    var k = kTop.divide(kBottom);

    var d = T.y.minus(k.mul(T.x));

//    System.out.println("ZLXpSJhnPY :: k = " + k);
//    System.out.println("ZLXpSJhnPY :: b = " + b);

    //@formatter:off

    var r0 = k.mul(k).negate();
    var r1 = a.minus(  k.mul(d).mul(2)  );
    var r2 = b.minus(  d.mul(d)  );

    var q1 = (T.x).plus(  r0  );
    var p1 = r1.plus(  (T.x).mul(   (T.x).plus(  r0  )    ));

    var mod1 = r2.plus( T.x.mul( p1 ) );

    var p2 = q1.plus(T.x);

    var mod2 = p1.plus(  p2.mul(T.x)  );

    //@formatter:on

    if (mod2.compareTo(Rational.ZERO) != 0) {
      throw new RuntimeException("zVKfVYC2g5");
    }
    if (mod1.compareTo(Rational.ZERO) != 0) {
      throw new RuntimeException("w8tZ3eMXQt");
    }

//    System.out.println("xx1Z9q5W7q :: r0 = " + r0);
//    System.out.println("xx1Z9q5W7q :: r1 = " + r1);
//    System.out.println("xx1Z9q5W7q :: r2 = " + r2);
//    System.out.println("xx1Z9q5W7q :: q1 = " + q1);
//    System.out.println("xx1Z9q5W7q :: p1 = " + p1);
//    System.out.println("xx1Z9q5W7q :: mod1 = " + mod1);

//    System.out.println("39m0C1ssNC :: p2 = " + p2);
//    System.out.println("39m0C1ssNC :: mod2 = " + mod2);

    var resultX = p2.negate();
    var resultY = k.mul(resultX).plus(d);

    return Dot.xy(resultX, resultY.negate());
  }
}
