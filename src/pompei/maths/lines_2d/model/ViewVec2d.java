package pompei.maths.lines_2d.model;

import java.awt.Point;

import static pompei.maths.lines_2d.util.IntUtil.toInt;

public class ViewVec2d extends Vec2d<ViewVec2d> {

  public ViewVec2d(double x, double y) {
    super(x, y);
  }

  public static ViewVec2d of(double x, double y) {
    return new ViewVec2d(x, y);
  }

  public Point asPoint() {
    return new Point(toInt(x), toInt(y));
  }

  @Override
  public ViewVec2d copy() {
    return of(x, y);
  }

  public int X() {
    return toInt(x);
  }

  public int Y() {
    return toInt(y);
  }
}
