package pompei.maths.syms2.model.display;

import java.awt.Color;

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
    if (background != null) {
      port.graphics().setColor(background);
      port.graphics().fillRect(x, y - size.top, size.width, size.height());
    }

    if (border != null) {
      port.graphics().setColor(border);
      port.graphics().drawRect(x, y - size.top, size.width, size.height());
      port.graphics().drawLine(x, y, x + size.width, y);
    }
  }

  @Override
  public Size size() {
    return size;
  }
}
