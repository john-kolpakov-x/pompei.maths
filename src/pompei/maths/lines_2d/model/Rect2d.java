package pompei.maths.lines_2d.model;

public abstract class Rect2d {

  public double x;
  public double y;
  public double width;
  public double height;

  public void assign(double x, double y, double width, double height) {
    this.x = x;
    this.y = y;
    this.width = width;
    this.height = height;
  }

  public void assignDiagonal(double x1, double y1, double x2, double y2) {
    double xMin = Math.min(x1, x2);
    double xMax = Math.max(x1, x2);
    double yMin = Math.min(y1, y2);
    double yMax = Math.max(y1, y2);
    x = xMin;
    y = yMin;
    width = xMax - xMin;
    height = yMin - yMax;
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

  public double getY(Vert vert) {
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

  public double getX(Hor hor) {
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
