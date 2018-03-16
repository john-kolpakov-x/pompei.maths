package pompei.maths.grav_masses;

public class Vec3 {
  public double x, y, z;

  public Vec3() {}

  public Vec3(double x, double y, double z) {
    this.x = x;
    this.y = y;
    this.z = z;
  }

  public Vec3(Vec3 a) {
    this(a.x, a.y, a.z);
  }

  @Override
  public String toString() {
    return "{" + x + ", " + y + ", " + z + "}";
  }
}
