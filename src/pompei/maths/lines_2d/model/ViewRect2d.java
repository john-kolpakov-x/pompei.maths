package pompei.maths.lines_2d.model;

import static pompei.maths.lines_2d.util.IntUtil.toInt;

public class ViewRect2d extends Rect2d<ViewRect2d, ViewVec2d> {

  public static ViewRect2d of(double x, double y, double width, double height) {
    var ret = new ViewRect2d();
    ret.assign(x, y, width, height);
    return ret;
  }

  public static ViewRect2d diagonal(double x1, double y1, double x2, double y2) {
    var ret = new ViewRect2d();
    ret.assignDiagonal(x1, y1, x2, y2);
    return ret;
  }

  @Override
  public double getY(Vert vert) {
    return super.getY(vert.invert());
  }

  public static ViewRect2d diagonal(ViewVec2d p1, ViewVec2d p2) {
    return diagonal(p1.x, p1.y, p2.x, p2.y);
  }

  public ViewVec2d getPoint(Hor hor, Vert vert) {
    return ViewVec2d.of(getX(hor), getY(vert));
  }

  public int X() {
    return toInt(x);
  }

  public int Y() {
    return toInt(y);
  }

  public int W() {
    return toInt(width);
  }

  public int H() {
    return toInt(height);
  }

  @Override
  protected ViewVec2d newVector(double x, double y) {
    return ViewVec2d.of(x, y);
  }

}
