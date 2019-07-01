package pompei.maths.lines_2d.math;

import pompei.maths.lines_2d.model.Vec2d;

public class Line<Vec extends Vec2d> {
  public Vec point;
  public Vec direction;

  public Line(Vec point, Vec direction) {
    this.point = point;
    this.direction = direction;
  }

  public Line() {}

  public Line<Vec> on2points(Vec point1, Vec point2) {
    point = point1;
    //noinspection unchecked
    direction = (Vec) point2.minus(point1);
    return this;
  }

  public Vec at(double t) {
    //noinspection unchecked
    return (Vec) direction.mul(t).plus(point);
  }

  public double pointTo(Line crossing) {
    var a = direction.x / crossing.direction.x;
    var b = direction.y / crossing.direction.y;

    var c = (crossing.point.x - point.x) / crossing.direction.x;
    var d = (crossing.point.y - point.y) / crossing.direction.y;

    return (c - d) / (a - b);
  }

}
