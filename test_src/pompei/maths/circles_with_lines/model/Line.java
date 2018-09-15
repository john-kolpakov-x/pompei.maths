package pompei.maths.circles_with_lines.model;

public class Line {
  public final Circle c1, c2;

  public double L1 = 1, L2 = 0.1, k = 2;

  public Line(Circle c1, Circle c2) {
    this.c1 = c1;
    this.c2 = c2;
  }

  public double force() {
    double dx = c1.x - c2.x;
    double dy = c1.y - c2.y;
    double l = Math.sqrt(dx * dx + dy * dy);
    return (L1 - l) * k;
  }
}
