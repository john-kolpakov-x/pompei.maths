package pompei.maths.grav_masses;

import pompei.maths.difur.DiffUr;
import pompei.maths.difur.DiffUrDefault;
import pompei.maths.difur.F;
import pompei.maths.difur.Stepper_H5_RungeKutta;

public class GravitySystem {
  public final int masCount;
  protected final F f;

  public GravitySystem(int masCount) {
    this.masCount = masCount;
    f = (double[] res, double t, double[] x) -> {
      for (int n = 0; n < masCount; n++) {
        putX(res, n, extractVelocityX(x, n));
        putY(res, n, extractVelocityY(x, n));
        putZ(res, n, extractVelocityZ(x, n));

        double xn = extractX(x, n), yn = extractY(x, n), zn = extractZ(x, n);

        double Fnx = 0, Fny = 0, Fnz = 0;
        for (int m = 0; m < masCount; m++) {
          if (m == n) continue;
          double xm = extractX(x, m), ym = extractY(x, m), zm = extractZ(x, m);
          double dx = xm - xn, dy = ym - yn, dz = zm - zn;
          double rnm2 = dx * dx + dy * dy + dz * dz;
          double rnm = Math.sqrt(rnm2);
          double mm_rnm3 = masses[m] / (rnm2 * rnm);
          Fnx += mm_rnm3 * dx;
          Fny += mm_rnm3 * dy;
          Fnz += mm_rnm3 * dz;
        }

        double Mn = masses[n];

        Fnx *= Mn * G;
        Fny *= Mn * G;
        Fnz *= Mn * G;

        double vx = Fnx / Mn, vy = Fny / Mn, vz = Fnz / Mn;
        putVelocityX(res, n, vx);
        putVelocityY(res, n, vy);
        putVelocityZ(res, n, vz);
      }
    };
  }

  public double G = 1;
  protected double masses[];
  protected DiffUr diffUr;

  protected static double extractX(double x[], int index) {
    return x[6 * index + 0];
  }

  protected static double extractY(double x[], int index) {
    return x[6 * index + 1];
  }

  protected static double extractZ(double x[], int index) {
    return x[6 * index + 2];
  }

  protected static double extractVelocityX(double x[], int index) {
    return x[6 * index + 3];
  }

  protected static double extractVelocityY(double x[], int index) {
    return x[6 * index + 4];
  }

  protected static double extractVelocityZ(double x[], int index) {
    return x[6 * index + 5];
  }

  public double getPlaceX(int index) {
    return extractX(diffUr.getX(), index);
  }

  public Vec3 getPlace(int index) {
    return new Vec3(getPlaceX(index), getPlaceY(index), getPlaceZ(index));
  }

  public Vec3 getVelocity(int index) {
    return new Vec3(getVelocityX(index), getVelocityY(index), getVelocityZ(index));
  }

  public double getPlaceY(int index) {
    return extractY(diffUr.getX(), index);
  }

  public double getPlaceZ(int index) {
    return extractZ(diffUr.getX(), index);
  }

  public double getVelocityX(int index) {
    return extractVelocityX(diffUr.getX(), index);
  }

  public double getVelocityY(int index) {
    return extractVelocityY(diffUr.getX(), index);
  }

  public double getVelocityZ(int index) {
    return extractVelocityZ(diffUr.getX(), index);
  }

  public void reset(double t0, double h) {
    masses = new double[masCount];
    diffUr = new DiffUrDefault(new Stepper_H5_RungeKutta());
    diffUr.prepare(masCount * 6, f);
    diffUr.setT(t0);
    diffUr.setH(h);
  }

  protected static void putX(double x[], int index, double value) {
    x[6 * index + 0] = value;
  }

  protected static void putY(double x[], int index, double value) {
    x[6 * index + 1] = value;
  }

  protected static void putZ(double x[], int index, double value) {
    x[6 * index + 2] = value;
  }

  protected static void putVelocityX(double x[], int index, double value) {
    x[6 * index + 3] = value;
  }

  protected static void putVelocityY(double x[], int index, double value) {
    x[6 * index + 4] = value;
  }

  protected static void putVelocityZ(double x[], int index, double value) {
    x[6 * index + 5] = value;
  }

  public void setPlace(int index, double x, double y, double z) {
    setPlaceX(index, x);
    setPlaceY(index, y);
    setPlaceZ(index, z);
  }

  public void setPlace(int index, Vec3 p) {
    setPlaceX(index, p.x);
    setPlaceY(index, p.y);
    setPlaceZ(index, p.z);
  }

  public void setVelocity(int index, double vx, double vy, double vz) {
    setVelocityX(index, vx);
    setVelocityY(index, vy);
    setVelocityZ(index, vz);
  }

  public void setVelocity(int index, Vec3 v) {
    setVelocityX(index, v.x);
    setVelocityY(index, v.y);
    setVelocityZ(index, v.z);
  }

  public void setPlaceX(int index, double value) {
    putX(diffUr.getX(), index, value);
  }

  public void setPlaceY(int index, double value) {
    putY(diffUr.getX(), index, value);
  }

  public void setPlaceZ(int index, double value) {
    putZ(diffUr.getX(), index, value);
  }

  public void setVelocityX(int index, double value) {
    putVelocityX(diffUr.getX(), index, value);
  }

  public void setVelocityY(int index, double value) {
    putVelocityY(diffUr.getX(), index, value);
  }

  public void setVelocityZ(int index, double value) {
    putVelocityZ(diffUr.getX(), index, value);
  }

  public void stopGlobalMoving() {
    double px = 0, py = 0, pz = 0, totalMass = 0;
    for (int i = 0; i < masCount; i++) {
      double m = masses[i];
      totalMass += m;
      px += m * getVelocityX(i);
      py += m * getVelocityY(i);
      pz += m * getVelocityZ(i);
    }

    double vx = px / totalMass, vy = py / totalMass, vz = pz / totalMass;

    for (int i = 0; i < masCount; i++) {
      setVelocityX(i, getVelocityX(i) - vx);
      setVelocityY(i, getVelocityY(i) - vy);
      setVelocityZ(i, getVelocityZ(i) - vz);
    }
  }
}
