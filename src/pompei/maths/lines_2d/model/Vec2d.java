package pompei.maths.lines_2d.model;

public class Vec2d {

  public double x, y;

  public Vec2d() {
    this(0, 0);
  }

  public Vec2d(int x, int y) {
    this.x = x;
    this.y = y;
  }

  public static Vec2d of(int x, int y) {
    return new Vec2d(x, y);
  }
}
