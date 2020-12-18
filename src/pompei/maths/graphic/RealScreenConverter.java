package pompei.maths.graphic;

import pompei.maths.lines_2d.file_saver.Savable;
import pompei.maths.utils.Vec2;

public class RealScreenConverter {

  @Savable
  public double kx = 1;
  @Savable
  public double ky = -1;
  @Savable
  public double deltaX = 0;
  @Savable
  public double deltaY = 0;
  public double screenWidth = 0;
  public double screenHeight = 0;

  public Vec2 toScreen(Vec2 realPoint) {
    return Vec2.xy(
        realPoint.x * kx + deltaX + screenWidth / 2,
        realPoint.y * ky + deltaY + screenHeight / 2
                  );
  }

  public RealScreenConverter copy() {
    var a = new RealScreenConverter();
    a.kx = kx;
    a.ky = ky;
    a.deltaX = deltaX;
    a.deltaY = deltaY;
    a.screenWidth = screenWidth;
    a.screenHeight = screenHeight;
    return a;
  }

  public Vec2 toReal(double x, double y) {
    return toReal(Vec2.xy(x, y));
  }

  public Vec2 toReal(Vec2 screenPoint) {
    return Vec2.xy(
        (screenPoint.x - (deltaX + screenWidth / 2)) / kx,
        (screenPoint.y - (deltaY + screenHeight / 2)) / ky
                  );
  }

  public Vec2 getDelta() {
    return Vec2.xy(deltaX, deltaY);
  }

  public void setDelta(Vec2 point) {
    deltaX = point.x;
    deltaY = point.y;
  }
}
