package pompei.maths.lines_2d.model.figures;

import pompei.maths.lines_2d.core.Axes;
import pompei.maths.lines_2d.core.Drawer;
import pompei.maths.lines_2d.model.ViewRect2d;
import pompei.maths.lines_2d.model.WorldRect2d;

import java.awt.Color;

public class FigureRect implements Figure {

  public WorldRect2d source;

  public FigureRect() {}

  public FigureRect(WorldRect2d source) {
    this.source = source;
  }

  @Override
  public void drawTo(Drawer drawer, ViewRect2d viewPortRect, Axes axes) {

    var viewRect = axes.toViewRect(source);

    drawer.setColor(Color.BLUE);
    drawer.drawRect(viewRect);

  }

}
