package pompei.maths.lines_2d.model;

public class Vec2d {

  public double x, y;

  public Vec2d() {
    this(0, 0);
  }

  public Vec2d(double x, double y) {
    this.x = x;
    this.y = y;
  }

  public static Vec2d of(double x, double y) {
    return new Vec2d(x, y);
  }
}
