package pompei.maths.graphic.graph;

import pompei.maths.graphic.pen.Pen;
import pompei.maths.graphic.styles.Styles;
import pompei.maths.utils.Vec2;

import java.awt.Color;

public class GraphPainter {

  private final Styles styles;
  private final GraphParams params;

  public GraphPainter(Styles styles, GraphParams params) {
    this.styles = styles;
    this.params = params;
  }

  public void paint(Pen pen) {

    try (var pen1 = pen.copy()) {
      pen1.setColor(styles.coordinatesColor());
      drawCoordinates(pen1);
    }

    try (var pen1 = pen.copy()) {
      drawGraph(pen1);
    }

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

  private double x1(Pen pen) {
    var converter = pen.converter();
    return converter.toReal(0, 0).x;
  }

  private int countX(Pen pen) {
    var converter = pen.converter();
    double dx = converter.toReal(1, 0).x - converter.toReal(0, 0).x;
    double length = x2(pen) - x1(pen);
    return (int) Math.round(length / dx);
  }

  private double x2(Pen pen) {
    var converter = pen.converter();
    return converter.toReal(converter.screenWidth, 0).x;
  }

  private void drawGraph(Pen pen) {

    var x1 = x1(pen);
    var x2 = x2(pen);
    var countX = countX(pen);
    var dx = (x2 - x1) / (double) countX;

    pen.setColor(Color.GREEN.darker());
    for (int i = 0; i < countX; i++) {

      double x = x1 + dx * i;
      double y = f1(x);

      pen.pin(Vec2.xy(x, y));

    }
    pen.setColor(Color.BLUE);
    for (int i = 0; i < countX; i++) {

      double x = x1 + dx * i;
      double y = f2(x);

      pen.pin(Vec2.xy(x, y));

    }

  }

  private double f2(double x) {

    double S = 0;

    for (int n = 1, N = params.N; n <= N; n++) {

      double a = Math.sin(params.k * Math.PI * n) * Math.cos(n * x) / n;

      S += a;
    }

    return params.k * params.A + 2 * params.A / Math.PI * S;

  }

  private double f1(double x) {

    while (x > Math.PI) {
      x -= 2 * Math.PI;
    }
    while (x < -Math.PI) {
      x += 2 * Math.PI;
    }

    if (x > params.k * Math.PI) {
      return 0;
    }
    if (x < -params.k * Math.PI) {
      return 0;
    }

    return params.A;

  }

}
