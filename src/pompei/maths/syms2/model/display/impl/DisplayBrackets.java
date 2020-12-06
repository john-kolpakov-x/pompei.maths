package pompei.maths.syms2.model.display.impl;

import pompei.maths.syms2.model.display.BracketsType;
import pompei.maths.syms2.model.display.DisplayExpr;
import pompei.maths.syms2.model.display.DisplayPort;
import pompei.maths.syms2.model.display.Size;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

public class DisplayBrackets implements DisplayExpr {
  public final DisplayExpr in;
  public final BracketsType type;
  public final Color color;
  private DisplayPort port;

  public DisplayBrackets(DisplayExpr in, BracketsType type, Color color) {
    this.in = in;
    this.type = type;
    this.color = color;
  }

  @Override
  public void setPort(DisplayPort port) {
    this.port = port;
    in.setPort(port);
  }

  private Size size = null;

  @Override
  public void reset() {
    size = null;
    in.reset();
  }

  private int top, bottom, leftWidth, leftSpace, centerWidth, rightSpace, rightWidth;
  private float lineWidth;

  @Override
  public void displayTo(int x, int y) {
    size();

    drawBracket(type, x, y - top, leftWidth, top + bottom);
    drawBracket(type, x + leftWidth + leftSpace + centerWidth + rightSpace + rightWidth, y - top, -rightWidth,
                top + bottom);

    in.displayTo(x + leftWidth + leftSpace, y);
  }


  @Override
  public Size size() {
    if (size != null) {
      return size;
    }

    Size inSize = in.size();
    top = inSize.top;
    bottom = inSize.bottom;
    centerWidth = inSize.width;

    int height = top + bottom;

    leftWidth = (int) ((float) height * 0.2f);
    leftSpace = (int) ((float) height * 0.04f);

    lineWidth = (float) height * 0.03f;
    if (lineWidth < 1f) {
      lineWidth = 1f;
    }

    rightWidth = leftWidth;
    rightSpace = leftSpace;

    return size = new Size(top, bottom, leftWidth + leftSpace + centerWidth + rightSpace + rightWidth);
  }

  private void drawBracket(BracketsType type, int x, int y, int width, int height) {
    switch (type) {
      case SQUARE:
        drawBracket_SQUARE(x, y, width, height);
        return;
      case ROUND:
        drawBracket_ROUND(x, y, width, height);
        return;
    }
    throw new RuntimeException("Cannot drawSides bracket with type = " + type);
  }

  private void drawBracket_ROUND(int x, int y, int width, int height) {
    if (width == 0) {
      return;
    }

    Graphics2D g = (Graphics2D) port.graphics().create();
    if (width > 0) {
      g.clipRect(x, y, width + 1, height + 1);
    } else {
      g.clipRect(x + width, y, -width + 1, height + 1);
    }

    g.setColor(color);
    g.setStroke(new BasicStroke(lineWidth));

    Rectangle2D.Float rect = new Rectangle2D.Float();
    if (width > 0) {
      rect.setFrame(x, y, width * 2, height);
    } else {
      rect.setFrame(x + width * 2, y, -width * 2, height);
    }

    Ellipse2D.Float ellipse = new Ellipse2D.Float();
    ellipse.setFrame(rect);
    g.draw(ellipse);

    g.dispose();
  }

  private void drawBracket_SQUARE(int x, int y, int width, int height) {
    if (width == 0) {
      return;
    }

    Graphics2D g = (Graphics2D) port.graphics().create();

    if (width > 0) {
      g.clipRect(x, y, width + 1, height + 1);
    } else {
      g.clipRect(x + width, y, -width + 1, height + 1);
    }

    g.setColor(color);
    g.setStroke(new BasicStroke(lineWidth * 2f));

    g.drawLine(x, y, x + width, y);
    g.drawLine(x, y, x, y + height);
    g.drawLine(x, y + height, x + width, y + height);

    g.dispose();
  }
}
