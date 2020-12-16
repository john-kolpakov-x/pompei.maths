package pompei.maths.utils;

import java.awt.Point;

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

  public static Vec2 from(Point point) {
    return xy(point.x, point.y);
  }

  public int X() {
    return (int) Math.round(x);
  }

  public int Y() {
    return (int) Math.round(y);
  }

  public Vec2 plus(Vec2 point) {
    return Vec2.xy(x + point.x, y + point.y);
  }

  public Vec2 minus(Vec2 point) {
    return Vec2.xy(x - point.x, y - point.y);
  }

  public double abs() {
    return Math.sqrt(sqr());
  }

  private double sqr() {
    return mul(this);
  }

  private double mul(Vec2 a) {
    return x * a.x + y * a.y;
  }

  @Override
  public String toString() {
    return "Vec2{" + x + ", " + y + '}';
  }

}
