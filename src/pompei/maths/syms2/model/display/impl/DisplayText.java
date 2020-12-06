package pompei.maths.syms2.model.display.impl;

import pompei.maths.syms2.model.display.DisplayExpr;
import pompei.maths.syms2.model.display.DisplayPort;
import pompei.maths.syms2.model.display.Size;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;

public class DisplayText implements DisplayExpr {
  public final int level;
  public final String text;
  public final Color color;
  public final boolean bold;
  public final boolean italic;
  private DisplayPort port;

  public DisplayText(int level, String text, Color color, boolean bold, boolean italic) {
    this.level = level;
    this.text = text;
    this.color = color;
    this.bold = bold;
    this.italic = italic;
  }

  @Override
  public void setPort(DisplayPort port) {
    this.port = port;
  }

  @Override
  public void reset() {
    size = null;
  }

  private void prepare(Graphics2D g) {
    g.setColor(color);
    g.setFont(g.getFont()
               .deriveFont(port.getFontSize(level))
               .deriveFont((bold ? Font.BOLD : 0) | (italic ? Font.ITALIC : 0))
             );
  }

  @Override
  public void displayTo(int x, int y) {
    size();
    Graphics2D g = (Graphics2D) port.graphics().create();
    prepare(g);
    g.drawString(text, x, y);
    g.dispose();
  }

  private Size size = null;

  @Override
  public Size size() {
    if (size != null) {
      return size;
    }

    Graphics2D g = (Graphics2D) port.graphics().create();
    prepare(g);

    FontMetrics fm = g.getFontMetrics();
    int top = fm.getAscent();
    int bottom = fm.getDescent();
    int width = fm.stringWidth(text);

    g.dispose();

    return size = new Size(top, bottom, width);
  }
}
