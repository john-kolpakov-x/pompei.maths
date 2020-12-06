package pompei.maths.difur.falling_in_cylinder;

import pompei.maths.difur.F;

import static java.lang.Math.tan;

public class FallInCylinderF implements F {

  public double A, g, H0, Omega0;

  @Override
  public void f(double[] res, double t, double[] x) {
    res[0] = x[2];
    res[1] = x[3];
    res[2] = z(x);
    res[3] = fi(x);
  }


  private double z(double[] x) {
    return tan(A) / (1 + tan(A)) * x[0] * x[3] * x[3] - g / (1 + tan(A));
  }

  private double fi(double[] x) {
    return -2.0 * x[2] * x[3] / x[0];
  }
}
