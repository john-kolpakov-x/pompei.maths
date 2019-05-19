package pompei.maths.lines_2d.core;

import pompei.maths.lines_2d.model.Rect2d;
import pompei.maths.lines_2d.model.Vec2d;

import java.awt.Color;
import java.awt.Graphics2D;

import static pompei.maths.lines_2d.util.IntUtil.toInt;

public class ViewPort {

  public final Axes axes = new Axes();
  private final Scene scene;

  public ViewPort(Scene scene) {
    this.scene = scene;
  }

  public void paint(Graphics2D g, Rect2d viewPortRect) {
    axes.rect = viewPortRect;

    paintCoordinateAxes(g, viewPortRect);

    {
      var viewLeftTop = Vec2d.of(viewPortRect.x, viewPortRect.y);
      var viewRightBottom = Vec2d.of(viewPortRect.x + viewPortRect.width, viewPortRect.y + viewPortRect.height);
      var worldLeftTop = axes.toWorld(viewLeftTop);
      var worldRightBottom = axes.toWorld(viewRightBottom);
      var worldRect = Rect2d.diagonal(worldLeftTop, worldRightBottom);

      scene.selectContains(worldRect).forEach(f -> f.drawTo(g, viewPortRect, axes));

    }
  }

  private void paintCoordinateAxes(Graphics2D g, Rect2d viewPortRect) {
    {
      double zeroX = axes.toViewX(0);

      if (viewPortRect.containX(zeroX)) {

        int X = toInt(zeroX);
        int Y1 = toInt(viewPortRect.y);
        int Y2 = toInt(viewPortRect.y + viewPortRect.height);

        g.setColor(new Color(156, 156, 156));
        g.drawLine(X, Y1, X, Y2);
        g.drawLine(X - 5, Y1 + 5, X, Y1);
        g.drawLine(X + 5, Y1 + 5, X, Y1);

      }
    }

    {
      double zeroY = axes.toViewY(0);

      if (viewPortRect.containY(zeroY)) {

        int Y = toInt(zeroY);
        int X1 = toInt(viewPortRect.x);
        int X2 = toInt(viewPortRect.x + viewPortRect.width);

        g.setColor(new Color(156, 156, 156));
        g.drawLine(X1, Y, X2, Y);
        g.drawLine(X2, Y, X2 - 5, Y - 5);
        g.drawLine(X2, Y, X2 - 5, Y + 5);

      }
    }

  }


}
