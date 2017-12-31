package pompei.maths.end_element;

import java.awt.*;

public class GraphicsDraw implements DrawDestination {

  private final Graphics2D g;
  private final Conversion conversion;

  public GraphicsDraw(Graphics2D g, Conversion conversion) {
    this.g = g;
    this.conversion = conversion;
  }

  private void innerLine(double x1, double y1, double x2, double y2) {
    g.drawLine(
        (int) Math.round(x1), (int) Math.round(y1),
        (int) Math.round(x2), (int) Math.round(y2)
    );
  }

  @Override
  public void setSideType(SideType sideType) {
    if (sideType == null) {
      g.setColor(Color.WHITE);
      return;
    }
    switch (sideType) {
      case WORKING:
        g.setColor(Color.GRAY);
        return;

      case LEFT_INPUT:
        g.setColor(Color.RED);
        return;

      case LEFT_WALL:
        g.setColor(Color.BLACK);
        return;

      case RIGHT_OUTPUT:
        g.setColor(Color.GREEN);
        return;

      case RIGHT_WALL:
        g.setColor(Color.BLUE);
        return;

      default:
        g.setColor(Color.WHITE);
        return;
    }
  }

  @Override
  public void line(double x1_, double y1_, double x2_, double y2_) {
    double x1 = conversion.convertX(x1_);
    double y1 = conversion.convertY(y1_);
    double x2 = conversion.convertX(x2_);
    double y2 = conversion.convertY(y2_);

    innerLine(x1, y1, x2, y2);
  }

  @Override
  public void vector(double x1_, double y1_, double x2_, double y2_) {
    double x1 = conversion.convertX(x1_);
    double y1 = conversion.convertY(y1_);
    double x2 = conversion.convertX(x2_);
    double y2 = conversion.convertY(y2_);

    innerLine(x1, y1, x2, y2);

    double dx = x2 - x1, dy = y2 - y1;

    double len = Math.sqrt(dx * dx + dy * dy);

    double a = 15, b = 2;

    if (len <= a) return;

    double nx = dx / len, ny = dy / len;

    double p1x = -ny, p1y = +nx;
    double p2x = +ny, p2y = -nx;


    innerLine(x2 - a * nx + b * p1x, y2 - a * ny + b * p1y, x2, y2);
    innerLine(x2 - a * nx + b * p2x, y2 - a * ny + b * p2y, x2, y2);
  }

  @Override
  public void labelPoint(double x_, double y_, String label, LabelPlace place) {
    int x = (int) Math.round(conversion.convertX(x_));
    int y = (int) Math.round(conversion.convertY(y_));

    int w = g.getFontMetrics().stringWidth(label);
    int h = g.getFontMetrics().getHeight();

    g.drawLine(x - 1, y - 1, x + 1, y - 1);
    g.drawLine(x - 1, y, x + 1, y);
    g.drawLine(x - 1, y + 1, x + 1, y + 1);

    int X = x - w / 2, Y = y - 2 + h / 2;

    switch (place) {
      case INSIDE:
      default:
        break;

      case TOP_RIGHT:
        X = x + 2;
        Y = y - 2;
        break;
      case RIGHT:
        X = x + 3;
        Y = y - 2 + h / 2;
        break;
      case LEFT:
        X = x - w - 2;
        Y = y - 2 + h / 2;
        break;
      case TOP:
        X = x - w / 2;
        Y = y - 2;
        break;
      case TOP_LEFT:
        X = x - w - 2;
        Y = y - 2;
        break;
      case BOTTOM:
        X = x - w / 2;
        Y = y + h - 1;
        break;
      case BOTTOM_RIGHT:
        X = x + 2;
        Y = y + h - 1;
        break;
      case BOTTOM_LEFT:
        X = x - w - 2;
        Y = y + h - 1;
        break;

    }

    Color color = g.getColor();
    g.setColor(Color.WHITE);
    g.drawString(label, X - 1, Y);
    g.drawString(label, X + 1, Y);
    g.drawString(label, X, Y - 1);
    g.drawString(label, X, Y + 1);
    g.setColor(color);
    g.drawString(label, X, Y);

  }
}
