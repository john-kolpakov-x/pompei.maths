package pompei.maths.syms_diff.visitors.paint;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

public class StrPainter implements Painter {
  
  private final Font font;
  private final String str;
  private Size size;
  private final Color color;
  
  public StrPainter(Graphics2D g, Font font, String str, Color color) {
    this.font = font;
    this.str = str;
    this.color = color;
    
    g.setFont(font);
    int nameWidth = g.getFontMetrics().stringWidth(str);
    int ascent = g.getFontMetrics().getMaxAscent();
    int descent = g.getFontMetrics().getMaxDescent();
    
    size = new Size(nameWidth, ascent, descent);
  }
  
  @Override
  public void paintTo(Graphics2D g, int x, int y) {
    g.setFont(font);
    g.setColor(color);
    g.drawString(str, x, y);
  }
  
  @Override
  public Size getSize() {
    return size;
  }
}
