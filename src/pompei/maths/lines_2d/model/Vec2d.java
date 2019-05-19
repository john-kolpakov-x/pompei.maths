package pompei.maths.lines_2d.model;

public abstract class Vec2d<V extends Vec2d> {

  public double x, y;

  public Vec2d() {
    this(0, 0);
  }

  @Override
  public String toString() {
    return getClass().getSimpleName() + "(" + x + "," + y + ")";
  }

  public Vec2d(double x, double y) {
    this.x = x;
    this.y = y;
  }

  public abstract V copy();

  public V plus(V a) {
    V ret = copy();
    ret.x += a.x;
    ret.y += a.y;
    return ret;
  }

  public V minus(V a) {
    V ret = copy();
    ret.x -= a.x;
    ret.y -= a.y;
    return ret;
  }

  public V mul(double a) {
    V ret = copy();
    ret.x *= a;
    ret.y *= a;
    return ret;
  }

}
