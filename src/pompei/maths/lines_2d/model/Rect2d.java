package pompei.maths.lines_2d.model;

public class Rect2d {

  public double x, y, width, height;

  public static Rect2d of(double x, double y, double width, double height) {
    Rect2d ret = new Rect2d();
    ret.x = x;
    ret.y = y;
    ret.width = width;
    ret.height = height;
    return ret;
  }

  public static Rect2d diagonal(double x1, double y1, double x2, double y2) {
    double xMin = Math.min(x1, x2);
    double xMax = Math.max(x1, x2);
    double yMin = Math.min(y1, y2);
    double yMax = Math.max(y1, y2);
    return of(xMin, yMin, xMax - xMin, yMax - yMin);
  }

  public static Rect2d diagonal(Vec2d p1, Vec2d p2) {
    return diagonal(p1.x, p1.y, p2.x, p2.y);
  }

  public boolean containX(double x) {
    if (x < this.x) {
      return false;
    }
    if (x > this.x + width) {
      return false;
    }
    return true;
  }

  public boolean containY(double y) {
    if (y < this.y) {
      return false;
    }
    if (y > this.y + height) {
      return false;
    }
    return true;
  }

  public Vec2d getPoint(Hor hor, Vert vert) {
    return Vec2d.of(getX(hor), getY(vert));
  }

  private double getY(Vert vert) {
    switch (vert) {
      case TOP:
        return y + height;
      case MIDDLE:
        return y + height / 2;
      case BOTTOM:
        return y;
    }
    throw new IllegalArgumentException("vert = " + vert);
  }

  private double getX(Hor hor) {
    switch (hor) {
      case LEFT:
        return x;
      case CENTER:
        return x + width / 2;
      case RIGHT:
        return x + width;
    }
    throw new IllegalArgumentException("hor = " + hor);
  }

}
