package pompei.maths.syms2.model.display;

import java.awt.Color;
import java.awt.Graphics2D;

public class DisplayRect implements DisplayExpr {

  private final Color border;
  private final Color background;
  private final Size size;
  private DisplayPort port;

  public DisplayRect(Size size, Color border, Color background) {
    this.size = size;
    this.border = border;
    this.background = background;
  }

  @Override
  public void setPort(DisplayPort port) {
    this.port = port;
  }

  @Override
  public void reset() {
  }

  @Override
  public void displayTo(int x, int y) {
    Graphics2D g = (Graphics2D) port.graphics().create();

    if (background != null) {
      g.setColor(background);
      g.fillRect(x, y - size.top, size.width, size.height());
    }

    if (border != null) {
      g.setColor(border);
      g.drawRect(x, y - size.top, size.width, size.height());
      g.drawLine(x, y, x + size.width, y);
    }

    g.dispose();
  }

  @Override
  public Size size() {
    return size;
  }
}
