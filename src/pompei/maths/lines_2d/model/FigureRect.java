package pompei.maths.lines_2d.model;

import pompei.maths.lines_2d.core.Axes;
import pompei.maths.lines_2d.core.Drawer;

import java.awt.Color;

public class FigureRect implements Figure {

  public WorldVec2d A, B;

  @Override
  public void drawTo(Drawer drawer, ViewRect2d viewPortRect, Axes axes) {

    var vA = axes.toView(A);
    var vB = axes.toView(B);

    var viewRect = ViewRect2d.diagonal(vA, vB);

    drawer.setColor(Color.BLUE);
    drawer.drawRect(viewRect);

  }

}
