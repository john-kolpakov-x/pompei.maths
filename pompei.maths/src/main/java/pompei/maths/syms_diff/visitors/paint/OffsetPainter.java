package pompei.maths.syms_diff.visitors.paint;

import java.awt.Graphics2D;

public class OffsetPainter implements Painter {
  
  private final Painter p;
  private final int dx, dy;
  
  private OffsetPainter(Painter p, int dx, int dy) {
    this.p = p;
    this.dx = dx;
    this.dy = dy;
  }
  
  public static Painter offset(Painter p, int dx, int dy) {
    
    if (dx == 0 && dy == 0) return p;
    
    if (p instanceof OffsetPainter) {
      OffsetPainter op = (OffsetPainter)p;
      dx = op.dx + dx;
      dy = op.dy + dy;
      p = op.p;
    }
    
    if (dx == 0 && dy == 0) return p;
    
    return new OffsetPainter(p, dx, dy);
  }
  
  @Override
  public void paintTo(Graphics2D g, int x, int y) {
    if (dx < 0) {
      p.paintTo(g, x, y + dy);
    } else {
      p.paintTo(g, x + dx, y + dy);
    }
  }
  
  private Size size = null;
  
  @Override
  public Size getSize() {
    if (size == null) calcSize();
    return size;
  }
  
  private void calcSize() {
    Size psize = p.getSize();
    int top = psize.heightTop;
    int bottom = psize.heightBottom;
    int width = psize.width;
    
    width += dx < 0 ? -dx : dx;
    top += dy > 0 ? dy : 0;
    bottom += dy < 0 ? -dy : 0;
    
    size = new Size(width, top, bottom);
  }
}
