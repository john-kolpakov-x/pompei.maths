package pompei.maths.difur.many_masses;

import java.awt.Color;
import java.awt.Graphics2D;

public class Elastic extends Connection {
  public double K;

  public Elastic(String id, Node from, Node to) {
    super(id, from, to);
  }

  @Override
  public String asStr() {
    return "Rezinka " + id + " K " + K + " from " + from.id + " to " + to.id;
  }

  public static Elastic parse(String[] split, NodeSource nodeSource) {
    if (!"Rezinka".equals(split[0])) {
      return null;
    }
    String id = split[1];
    Node from = nodeSource.getNodeById(split[5]);
    Node to = nodeSource.getNodeById(split[7]);
    Elastic ret = new Elastic(id, from, to);
    ret.K = Double.parseDouble(split[3]);
    return ret;
  }

  @Override
  public void draw(Graphics2D g) {
    g.setColor(Color.GREEN);
    int x1 = from.centerX();
    int y1 = from.centerY();
    int x2 = to.centerX();
    int y2 = to.centerY();
    g.drawLine(x1, y1, x2, y2);
  }

  @Override
  public Vec2d getFromForse() {
    return getToForse();
  }

  @Override
  public Vec2d getToForse() {
    double dx = from.centerX() - to.centerX();
    double dy = from.centerY() - to.centerY();
    double dist = Math.sqrt(dx * dx + dy * dy);
    double forse = dist * K;
    dx /= dist;
    dy /= dist;
    return new Vec2d(forse * dx, forse * dy);
  }
}
