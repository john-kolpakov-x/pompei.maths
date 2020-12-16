package pompei.maths.graphic.pen;

import pompei.maths.graphic.RealScreenConverter;
import pompei.maths.utils.Vec2;

import java.awt.Color;

public interface Pen extends AutoCloseable {

  @Override
  void close();

  Pen copy();

  RealScreenConverter converter();

  Pen setConverting(boolean converting);

  Line line(Vec2 startPoint);

  default Line line(double x, double y) {
    return line(Vec2.xy(x, y));
  }

  Pen setColor(Color color);

  Pen pin(Vec2 a);

}
