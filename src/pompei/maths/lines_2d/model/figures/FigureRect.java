package pompei.maths.lines_2d.model.figures;

import pompei.maths.lines_2d.core.Axes;
import pompei.maths.lines_2d.core.Drawer;
import pompei.maths.lines_2d.model.Hor;
import pompei.maths.lines_2d.model.Vert;
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

    var vA = axes.toView(source.vertex(Hor.LEFT, Vert.BOTTOM));
    var vB = axes.toView(source.vertex(Hor.RIGHT, Vert.TOP));

    var viewRect = ViewRect2d.diagonal(vA, vB);

    drawer.setColor(Color.BLUE);
    drawer.drawRect(viewRect);

  }

}
