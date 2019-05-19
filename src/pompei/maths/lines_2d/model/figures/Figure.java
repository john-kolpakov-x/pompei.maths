package pompei.maths.lines_2d.model.figures;

import pompei.maths.lines_2d.core.Axes;
import pompei.maths.lines_2d.core.Drawer;
import pompei.maths.lines_2d.model.ViewRect2d;

public interface Figure {

  void drawTo(Drawer drawer, ViewRect2d viewPortRect, Axes axes);

}
