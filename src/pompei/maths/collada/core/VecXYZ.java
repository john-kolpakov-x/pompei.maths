package pompei.maths.collada.core;

public class VecXYZ {
  public double x, y, z;

  public VecXYZ() {}

  public VecXYZ(double x, double y, double z) {
    this.x = x;
    this.y = y;
    this.z = z;
  }

  public VecXYZ plus(VecXYZ a) {
    return new VecXYZ(x + a.x, y + a.y, z + a.z);
  }

  public double square() {
    return x * x + y * y + z * z;
  }

  public double length() {
    return Math.sqrt(square());
  }

  public VecXYZ mul(double a) {
    return new VecXYZ(x * a, y * a, z * a);
  }

  public VecXYZ div(double a) {
    return mul(1 / a);
  }
}
