package pompei.maths.circles_with_lines;

public class Vec2 {
  public double x, y;

  public Vec2() {
    this(0, 0);
  }

  public static Vec2 xy(double x, double y) {
    return new Vec2(x, y);
  }

  public Vec2(double x, double y) {
    this.x = x;
    this.y = y;
  }

  public int X() {
    return (int) Math.round(x);
  }

  public int Y() {
    return (int) Math.round(y);
  }
}
