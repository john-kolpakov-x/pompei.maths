package pompei.maths.circles_with_lines;


import static java.lang.Math.tan;

@SuppressWarnings({"NonAsciiCharacters", "UnnecessaryLocalVariable"})
public class Shoveller {

  private static final double _m = 1.;
  private static final double _dm = _m / 10.;
  private static final double _sm = _dm / 10.;
  private static final double _mm = _sm / 10.;

  private static final double _deg = Math.PI / 180.;

  public static void main(String[] args) {
    final double h = 50 * _mm;
    final double b = 32 * _mm;
    final double s = 4.4 * _mm;
    final double t = 7.0 * _mm;
    final double R = 6.0 * _mm;
    final double r = 2.5 * _mm;

    final double ϕ = 10 * _deg;

    final double Qx = b - (b - s) / 2;
    final double Qy = h - t;
    showPoint("Q", Qx, Qy);

    final double Ux = b;
    final double Uy = Qy + ((b - s) / 2.) * tan(ϕ);
    showPoint("U", Ux, Uy);

    final double Zx = s;
    final double Zy = Qy - ((b - s) / 2.) * tan(ϕ);
    showPoint("Z", Zx, Zy);
  }

  private static void showPoint(String name, double x, double y) {
    System.out.println(name + " = (" + x / _mm + "mm, " + y / _mm + "mm)");
  }
}
