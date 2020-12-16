package pompei.maths.graphic;

import pompei.maths.utils.Vec2;

public class RealScreenConverter {

  public double kx = 1;
  public double ky = -1;
  public double deltaX = 0;
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
