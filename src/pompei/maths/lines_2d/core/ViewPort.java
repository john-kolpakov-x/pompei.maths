package pompei.maths.lines_2d.core;

import pompei.maths.lines_2d.model.ViewRect2d;
import pompei.maths.lines_2d.model.ViewVec2d;
import pompei.maths.lines_2d.model.WorldRect2d;

import java.awt.Color;

public class ViewPort {

  public final Axes axes = new Axes();
  private final Scene scene;

  public ViewPort(Scene scene) {
    this.scene = scene;
  }

  public void paint(Drawer g, ViewRect2d viewPortRect) {
    axes.rect = viewPortRect;

    paintCoordinateAxes(g, viewPortRect);

    {
      var viewLeftTop = ViewVec2d.of(viewPortRect.x, viewPortRect.y);
      var viewRightBottom = ViewVec2d.of(viewPortRect.x + viewPortRect.width, viewPortRect.y + viewPortRect.height);
      var worldLeftTop = axes.toWorld(viewLeftTop);
      var worldRightBottom = axes.toWorld(viewRightBottom);
      var worldRect = WorldRect2d.diagonal(worldLeftTop, worldRightBottom);

      scene.selectContains(worldRect).forEach(f -> f.drawTo(g, viewPortRect, axes));
    }
  }

  private void paintCoordinateAxes(Drawer g, ViewRect2d viewPortRect) {
    {
      double zeroX = axes.toViewX(0);

      if (viewPortRect.containX(zeroX)) {

        var p1 = ViewVec2d.of(zeroX, viewPortRect.y);
        var p2 = ViewVec2d.of(zeroX, viewPortRect.y + viewPortRect.height);

        g.setColor(new Color(156, 156, 156));
        g.drawLine(p1, p2);
        g.drawLine(p1.plus(ViewVec2d.of(-5, 5)), p1);
        g.drawLine(p1.plus(ViewVec2d.of(5, 5)), p1);

      }
    }

    {
      double zeroY = axes.toViewY(0);

      if (viewPortRect.containY(zeroY)) {

        var p1 = ViewVec2d.of(viewPortRect.x, zeroY);
        var p2 = ViewVec2d.of(viewPortRect.x + viewPortRect.width, zeroY);

        g.setColor(new Color(156, 156, 156));
        g.drawLine(p1, p2);
        g.drawLine(p2.plus(ViewVec2d.of(-5, -5)), p2);
        g.drawLine(p2.plus(ViewVec2d.of(-5, 5)), p2);

      }
    }

  }


}
