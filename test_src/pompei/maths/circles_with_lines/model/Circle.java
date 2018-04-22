package pompei.maths.circles_with_lines.model;

import pompei.maths.circles_with_lines.Vec2;

public class Circle {
  public double x, y, m, r;

  public Circle() {}

  public Circle(double x, double y, double r, double m) {
    this.x = x;
    this.y = y;
    this.r = r;
    this.m = m;
  }

  public Vec2 center() {
    return Vec2.xy(x, y);
  }
}
