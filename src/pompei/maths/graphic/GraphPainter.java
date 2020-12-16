package pompei.maths.graphic;

import pompei.maths.graphic.pen.Pen;
import pompei.maths.graphic.styles.Styles;
import pompei.maths.utils.Vec2;

import java.awt.Color;

public class GraphPainter {

  private final Styles styles;

  public GraphPainter(Styles styles) {
    this.styles = styles;
  }

  public void paint(Pen pen) {

    try (var pen1 = pen.copy()) {
      pen1.setColor(styles.coordinatesColor());
      drawCoordinates(pen1);
    }

    pen.setColor(Color.BLACK);
    pen.line(5, 5).to(100, 100);

    pen.line(-100, -100).delta(80, 0).delta(0, 80).delta(-80, 0).delta(0, -80);

  }

  private void drawCoordinates(Pen pen) {
    pen.setConverting(false);
    var converter = pen.converter();

    var center = converter.toScreen(Vec2.xy(0, 0));
    {
      var x = center.x;
      if (0 <= x && x <= converter.screenWidth) {
        pen.line(x, 0).to(x, converter.screenHeight);
      }
    }
    {
      var y = center.y;
      if (0 <= y && y <= converter.screenHeight) {
        pen.line(0, y).to(converter.screenWidth, y);
      }
    }
  }

}
