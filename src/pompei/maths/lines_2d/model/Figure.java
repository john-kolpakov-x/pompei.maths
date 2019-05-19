package pompei.maths.lines_2d.model;

import pompei.maths.lines_2d.core.Axes;

import java.awt.Graphics2D;

public interface Figure {

  void drawTo(Graphics2D g, Rect2d viewPortRect, Axes axes);

}
