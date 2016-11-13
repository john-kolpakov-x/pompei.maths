package pompei.maths.difur.many_masses;

public class Vec2d {
  public final double x, y;

  public Vec2d(double x, double y) {
    this.x = x;
    this.y = y;
  }

  @SuppressWarnings("unused")
  public Vec2d negate() {
    return new Vec2d(-x, -y);
  }

  public Vec2d plus(Vec2d a) {
    return new Vec2d(x + a.x, y + a.y);
  }

  public Vec2d minus(Vec2d a) {
    return new Vec2d(x - a.x, y - a.y);
  }
}
