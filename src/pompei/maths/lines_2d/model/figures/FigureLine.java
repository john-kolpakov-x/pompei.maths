package pompei.maths.lines_2d.model.figures;

import pompei.maths.lines_2d.core.Axes;
import pompei.maths.lines_2d.core.Drawer;
import pompei.maths.lines_2d.model.ViewRect2d;
import pompei.maths.lines_2d.model.WorldVec2d;

import java.awt.Color;
import java.util.Objects;

public class FigureLine implements Figure {

  private final WorldVec2d point;
  private final WorldVec2d direction;
  private final Double from;
  private final Double to;

  public Color color = Color.BLUE;

  public FigureLine(WorldVec2d point, WorldVec2d direction, Double from, Double to) {
    Objects.requireNonNull(point);
    Objects.requireNonNull(direction);
    this.point = point;
    this.direction = direction;
    this.from = from;
    this.to = to;
  }

  public static FigureLine linePart(WorldVec2d pointFrom, WorldVec2d pointTo) {
    return new FigureLine(pointFrom, pointTo.minus(pointFrom), 0.0, 1.0);
  }

  public static FigureLine lineHalf(WorldVec2d pointFrom, WorldVec2d pointTo) {
    return new FigureLine(pointFrom, pointTo.minus(pointFrom), 0.0, 1.0);
  }

  @Override
  public void drawTo(Drawer drawer, ViewRect2d viewPortRect, Axes axes) {

  }
}
