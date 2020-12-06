package pompei.maths.syms2.model.display.impl;

import pompei.maths.syms2.model.display.DisplayExpr;
import pompei.maths.syms2.model.display.DisplayPort;
import pompei.maths.syms2.model.display.Size;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

import static java.lang.Math.round;

public class DisplayDiv implements DisplayExpr {
  public final int level;
  public final DisplayExpr top;
  public final DisplayExpr bottom;
  public final float spaceFactor;
  private final Color lineColor;
  private DisplayPort port;

  public DisplayDiv(int level, DisplayExpr top, DisplayExpr bottom, float spaceFactor, Color lineColor) {
    this.level = level;
    this.top = top;
    this.bottom = bottom;
    this.spaceFactor = spaceFactor;
    this.lineColor = lineColor;
  }

  @Override
  public void setPort(DisplayPort port) {
    this.port = port;
    top.setPort(port);
    bottom.setPort(port);
  }

  @Override
  public void reset() {
    size = null;
    top.reset();
    bottom.reset();
  }

  private Size size = null;
  private int topDeltaX, topDeltaY;
  private int bottomDeltaX, bottomDeltaY;

  private int lineDeltaX, lineDeltaY, lineLength;
  private float lineWidth;

  @Override
  public void displayTo(int x, int y) {
    size();
    displayLine(x, y);
    top.displayTo(x + topDeltaX, y - topDeltaY);
    bottom.displayTo(x + bottomDeltaX, y - bottomDeltaY);
  }

  private void displayLine(int x, int y) {
    if (lineColor == null) {
      return;
    }
    Graphics2D g = (Graphics2D) port.graphics().create();

    g.setColor(lineColor);
    g.setStroke(new BasicStroke(lineWidth));
    g.drawLine(x + lineDeltaX, y - lineDeltaY, x + lineDeltaX + lineLength, y - lineDeltaY);

    g.dispose();
  }


  @Override
  public Size size() {
    if (size != null) {
      return size;
    }

    Size topSize = top.size();
    Size bottomSize = bottom.size();

    Graphics2D g = (Graphics2D) port.graphics().create();
    g.setFont(g.getFont().deriveFont(port.getFontSize(level)));
    float ascent = g.getFontMetrics().getAscent();
    float descent = g.getFontMetrics().getDescent();
    g.dispose();

    float space = ascent * spaceFactor;

    float width2 = Math.max(topSize.width, bottomSize.width) / 2f;

    lineWidth = space / 2;
    lineDeltaX = round(space);
    lineDeltaY = round(ascent / 2);
    lineLength = round(2 * width2 + 2 * space);

    topDeltaX = round(space * 2 + width2 - topSize.width / 2f);
    bottomDeltaX = round(space * 2 + width2 - bottomSize.width / 2f);

    topDeltaY = round(ascent / 2 + space + topSize.bottom);
    bottomDeltaY = round(ascent / 2 - space - bottomSize.top);

    float width = 4 * space + 2 * width2;
    float top = space + topSize.height() + ascent / 2;
    float bottom = space + bottomSize.height() - ascent / 2;

    if (top < ascent) {
      top = ascent;
    }
    if (bottom < descent) {
      bottom = descent;
    }

    return size = new Size(round(top), round(bottom), round(width));
  }
}
