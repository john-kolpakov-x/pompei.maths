package pompei.maths.lines_2d.model;

public class WorldVec2d extends Vec2d<WorldVec2d> {

  public WorldVec2d(double x, double y) {
    super(x, y);
  }

  public static WorldVec2d of(double x, double y) {
    return new WorldVec2d(x, y);
  }

  @Override
  public WorldVec2d copy() {
    return of(x, y);
  }
}
