package pompei.maths.diofant;

import pompei.maths.bdmath.Rational;

import java.util.Objects;

public class Dot {
  public final Rational x, y;

  private Dot(Rational x, Rational y) {
    this.x = Objects.requireNonNull(x);
    this.y = Objects.requireNonNull(y);
  }

  public static Dot xy(Rational x, Rational y) {
    return new Dot(x, y);
  }

  public static Dot xy(String strX, String strY) {
    return new Dot(Rational.parse(strX), Rational.parse(strY));
  }

  @Override
  public String toString() {
    return "Dot(" + x + ';' + y + ')';
  }

  public String hexStr() {
    return "Dot[" + x.hexStr() + ';' + y.hexStr() + ']';
  }

  public String toBase64() {
    return "Dot{" + x.toBase64() + ';' + y.toBase64() + '}';
  }
}
