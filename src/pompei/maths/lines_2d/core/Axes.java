package pompei.maths.lines_2d.core;

import pompei.maths.lines_2d.model.Rect2d;
import pompei.maths.lines_2d.model.Vec2d;

import java.awt.Point;

public class Axes {

  public Vec2d viewCenter = Vec2d.of(0, 0);
  public double scale = 1;

  public Rect2d rect = Rect2d.of(0, 0, 1, 1);

  public double toViewX(double worldX) {
    double offsetViewX = worldX * scale;
    double viewCenterX = rect.x + rect.width / 2 + viewCenter.x;
    return viewCenterX + offsetViewX;
  }

  public double toWorldX(double viewX) {
    double viewCenterX = rect.x + rect.width / 2 + viewCenter.x;
    double offsetViewX = viewX - viewCenterX;
    return offsetViewX / scale;
  }

  public double toViewY(double worldY) {
    double offsetViewY = worldY * scale;
    double viewCenterY = rect.y + rect.height / 2 - viewCenter.y;
    return viewCenterY - offsetViewY;
  }


  public double toWorldY(double viewY) {
    double viewCenterY = rect.y + rect.height / 2 - viewCenter.y;
    double offsetViewY = viewCenterY - viewY;
    return offsetViewY / scale;
  }

  //
  //
  //
  //

  public int toViewXi(double worldX) {
    return Math.round((float) toViewX(worldX));
  }

  public int toViewYi(double worldY) {
    return Math.round((float) toViewY(worldY));
  }

  public double toWorldX(int viewX) {
    return toWorldX((double) viewX);
  }

  public double toWorldY(int viewY) {
    return toWorldY((double) viewY);
  }

  public Vec2d toWorld(Vec2d viewPoint) {
    return Vec2d.of(toWorldX(viewPoint.x), toWorldY(viewPoint.y));
  }

  public Vec2d toView(Vec2d worldPoint) {
    return Vec2d.of(toViewX(worldPoint.x), toViewY(worldPoint.y));
  }

  public Point toViewPoint(Vec2d worldPoint) {
    return new Point(toViewXi(worldPoint.x), toViewYi(worldPoint.y));
  }

}
