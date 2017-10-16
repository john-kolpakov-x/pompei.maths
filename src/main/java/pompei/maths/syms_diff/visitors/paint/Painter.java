package pompei.maths.syms_diff.visitors.paint;

import java.awt.Graphics2D;

public interface Painter {
  void paintTo(Graphics2D g, int x, int y);

  Size getSize();
}
