package pompei.maths.lines_2d.model;

import pompei.maths.lines_2d.core.Axes;

import java.awt.Graphics2D;

public class FigureRect implements Figure {

  public double x1, y1, x2, y2;

  @Override
  public void drawTo(Graphics2D g, Rect2d viewPortRect, Axes axes) {

    var X1 = axes.toViewX(x1);
    var X2 = axes.toViewX(x2);
    var Y1 = axes.toViewY(y1);
    var Y2 = axes.toViewY(y2);

    Rect2d viewRect = Rect2d.diagonal(X1, Y1, X2, Y2);

  }

}
