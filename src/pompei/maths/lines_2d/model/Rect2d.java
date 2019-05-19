package pompei.maths.lines_2d.model;

public class Rect2d {

  public double x, y, width, height;


  public static Rect2d of(int x, int y, int width, int height) {
    Rect2d ret = new Rect2d();
    ret.x = x;
    ret.y = y;
    ret.width = width;
    ret.height = height;
    return ret;
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
}
