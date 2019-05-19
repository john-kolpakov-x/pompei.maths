package pompei.maths.lines_2d.model;

import pompei.maths.lines_2d.core.Axes;
import pompei.maths.lines_2d.core.Drawer;

public interface Figure {

  void drawTo(Drawer drawer, ViewRect2d viewPortRect, Axes axes);

}
