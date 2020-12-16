package pompei.maths.graphic.pen;

import pompei.maths.graphic.RealScreenConverter;
import pompei.maths.utils.Vec2;

import java.awt.Color;
import java.awt.Graphics2D;

public class GraphicsPen implements Pen {

  private final Graphics2D g;
  private final RealScreenConverter converter;
  private boolean converting = true;

  public GraphicsPen(Graphics2D g, RealScreenConverter converter) {
    this.g = g;
    this.converter = converter;
  }

  @Override
  public void close() {
    g.dispose();
  }

  @Override
  public Pen copy() {
    var ret = new GraphicsPen((Graphics2D) g.create(), converter.copy());
    ret.converting = converting;
    return ret;
  }

  @Override
  public RealScreenConverter converter() {
    return converter;
  }

  @Override
  public Pen setConverting(boolean converting) {
    this.converting = converting;
    return this;
  }

  @Override
  public Line line(Vec2 startPoint) {
    return new Line() {
      Vec2 p = startPoint;

      @Override
      public Line moveTo(Vec2 point) {
        p = point;
        return this;
      }

      @Override
      public Line to(Vec2 point) {
        drawLine(p, point);
        return moveTo(point);
      }

      @Override
      public Line moveDelta(Vec2 delta) {
        p = p.plus(delta);
        return this;
      }

      @Override
      public Line delta(Vec2 delta) {
        drawLine(p, p.plus(delta));
        return moveDelta(delta);
      }

      @Override
      public Vec2 current() {
        return p;
      }
    };
  }

  private void drawLine(Vec2 from, Vec2 to) {
    if (converting) {
      var screenFrom = converter.toScreen(from);
      var screenTo = converter.toScreen(to);
      g.drawLine(screenFrom.X(), screenFrom.Y(), screenTo.X(), screenTo.Y());
    } else {
      g.drawLine(from.X(), from.Y(), to.X(), to.Y());
    }
  }

  @Override
  public GraphicsPen setColor(Color color) {
    g.setColor(color);
    return this;
  }

  @Override
  public Pen pin(Vec2 a) {
    if (converting) {
      a = converter.toScreen(a);
    }
    if (0 <= a.x && a.x <= converter.screenWidth) {

      if (0 <= a.y && a.y <= converter.screenHeight) {

        int X = a.X();
        int Y = a.Y();

        g.drawLine(X, Y, X, Y);

      }

    }
    return this;
  }
}
