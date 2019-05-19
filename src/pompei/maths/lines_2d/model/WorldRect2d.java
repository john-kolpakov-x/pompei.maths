package pompei.maths.lines_2d.model;

public class WorldRect2d extends Rect2d<WorldRect2d, WorldVec2d> {

  public static WorldRect2d of(double x, double y, double width, double height) {
    var ret = new WorldRect2d();
    ret.assign(x, y, width, height);
    return ret;
  }

  public static WorldRect2d diagonal(double x1, double y1, double x2, double y2) {
    var ret = new WorldRect2d();
    ret.assignDiagonal(x1, y1, x2, y2);
    return ret;
  }

  public static WorldRect2d diagonal(WorldVec2d p1, WorldVec2d p2) {
    return diagonal(p1.x, p1.y, p2.x, p2.y);
  }

  public WorldVec2d vertex(Hor hor, Vert vert) {
    return WorldVec2d.of(getX(hor), getY(vert));
  }

  @Override
  protected WorldVec2d newVector(double x, double y) {
    return WorldVec2d.of(x, y);
  }

}
