package pompei.maths.syms.visitors;

import java.awt.Graphics2D;
import java.awt.geom.Path2D;

public class SkobPainter {
  
  public static void paint(Graphics2D g, ConfSkob conf, int x, int y, int w, int h, boolean isRight) {
    double w2 = w / 2.0;
    double h2 = h / 2.0;
    double h8 = h / 8.0;
    
    double x0 = x, y0 = y;
    double x1 = x + w2, y1 = y + h8;
    double x2 = x + w2, y2 = y + h2;
    double x3 = x + w2, y3 = y + h - h8;
    double x4 = x, y4 = y + h;
    double x5 = x + w, y5 = y + h - h8;
    double x6 = x + w, y6 = y + h2;
    double x7 = x + w, y7 = y + h8;
    
    if (!isRight) {
      x0 += w;
      x7 -= w;
      x6 -= w;
      x5 -= w;
      x4 += w;
    }
    
    Path2D.Double p = new Path2D.Double();
    p.moveTo(x0, y0);
    p.curveTo(x1, y1, x1, y1, x2, y2);
    p.curveTo(x3, y3, x3, y3, x4, y4);
    p.curveTo(x5, y5, x5, y5, x6, y6);
    p.curveTo(x7, y7, x7, y7, x0, y0);
    p.closePath();
    
    g.fill(p);
  }
  
}
