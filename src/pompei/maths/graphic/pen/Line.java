package pompei.maths.graphic.pen;

import pompei.maths.utils.Vec2;

public interface Line {

  Line moveTo(Vec2 point);

  Line moveDelta(Vec2 delta);

  default Line moveDelta(double x, double y) {
    return moveDelta(Vec2.xy(x, y));
  }

  Line to(Vec2 point);

  default Line to(double x, double y) {
    return to(Vec2.xy(x, y));
  }

  Line delta(Vec2 delta);

  default Line delta(double x, double y) {
    return delta(Vec2.xy(x, y));
  }

  Vec2 current();

}
