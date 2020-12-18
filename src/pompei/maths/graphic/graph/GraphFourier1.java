package pompei.maths.graphic.graph;

import pompei.maths.lines_2d.file_saver.Savable;

import java.awt.Color;

public class GraphFourier1 implements Graph {

  @Savable
  public int N = 5;

  @Savable
  public double A = 3;

  @Savable
  public double k = 0.1;

  @Override
  public int funcCount() {
    return 2;
  }

  @Override
  public double f(int n, double x) {
    switch (n) {
      case 1:
        return f1(x);
      case 2:
        return f2(x);
      default:
        throw new RuntimeException("qv35nf8WMy");
    }
  }

  @Override
  public Color color(int n) {
    switch (n) {
      case 1:
        return Color.GREEN.darker();

      case 2:
        return Color.BLUE;

      default:
        throw new RuntimeException("5z96tYwWg9");
    }
  }

  private double f2(double x) {

    double S = 0;

    for (int n = 1; n <= N; n++) {

      double a = Math.sin(k * Math.PI * n) * Math.cos(n * x) / n;

      S += a;
    }

    return k * A + 2 * A / Math.PI * S;

  }

  private double f1(double x) {

    while (x > Math.PI) {
      x -= 2 * Math.PI;
    }
    while (x < -Math.PI) {
      x += 2 * Math.PI;
    }

    if (x > k * Math.PI) {
      return 0;
    }
    if (x < -k * Math.PI) {
      return 0;
    }

    return A;

  }
}
