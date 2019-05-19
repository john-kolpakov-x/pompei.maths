package pompei.maths.lines_2d.core;

import pompei.maths.lines_2d.model.ViewRect2d;
import pompei.maths.lines_2d.model.ViewVec2d;

import java.awt.Color;

public interface Drawer {

  void drawRect(ViewRect2d viewRect);

  void setColor(Color color);

  void drawLine(ViewVec2d p1, ViewVec2d p2);
}
