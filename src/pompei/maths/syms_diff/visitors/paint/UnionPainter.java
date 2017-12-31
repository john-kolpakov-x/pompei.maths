package pompei.maths.syms_diff.visitors.paint;

import java.awt.Graphics2D;

import static java.lang.Math.max;

public class UnionPainter implements Painter {

  private final Painter p1;
  private final Painter p2;

  private UnionPainter(Painter p1, Painter p2) {
    this.p1 = p1;
    this.p2 = p2;
  }

  public static UnionPainter union(Painter p1, Painter p2) {
    if (p1 == null) throw new NullPointerException("p1 == null");
    if (p2 == null) throw new NullPointerException("p2 == null");
    return new UnionPainter(p1, p2);
  }

  @Override
  public void paintTo(Graphics2D g, int x, int y) {
    p1.paintTo(g, x, y);
    p2.paintTo(g, x, y);
  }
  
  private Size size = null;

  @Override
  public Size getSize() {
    if (size == null) calcSize();
    return size;
  }

  private void calcSize() {
    Size s1 = p1.getSize();
    Size s2 = p2.getSize();

    int heightTop = max(s1.heightTop, s2.heightTop);
    int heightBottom = max(s1.heightBottom, s2.heightBottom);
    int width = max(s1.width, s2.width);

    size = new Size(width, heightTop, heightBottom);
  }
}
