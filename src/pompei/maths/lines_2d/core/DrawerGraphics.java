package pompei.maths.lines_2d.core;

import pompei.maths.lines_2d.model.ViewRect2d;
import pompei.maths.lines_2d.model.ViewVec2d;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class DrawerGraphics implements Drawer {
  private final Graphics2D graphics;

  public DrawerGraphics(Graphics graphics) {
    this.graphics = (Graphics2D) graphics;
  }

  @Override
  public void drawRect(ViewRect2d viewRect) {
    graphics.drawRect(viewRect.X(), viewRect.Y(), viewRect.W(), viewRect.H());
  }

  @Override
  public void setColor(Color color) {
    graphics.setColor(color);
  }

  @Override
  public void drawLine(ViewVec2d p1, ViewVec2d p2) {
    graphics.drawLine(p1.X(), p1.Y(), p2.X(), p2.Y());
  }
}
