package pompei.maths.difur.many_masses;

import java.awt.Color;
import java.awt.Graphics2D;

public class Rezinka extends Svjaz {
  public double K;
  
  public Rezinka(String id, Uzel from, Uzel to) {
    super(id, from, to);
  }
  
  @Override
  public String asStr() {
    return "Rezinka " + id + " K " + K + " from " + from.id + " to " + to.id;
  }
  
  public static Rezinka parse(String[] split, UzelSource uzelSource) {
    if (!"Rezinka".equals(split[0])) return null;
    String id = split[1];
    Uzel from = uzelSource.getUzelById(split[5]);
    Uzel to = uzelSource.getUzelById(split[7]);
    Rezinka ret = new Rezinka(id, from, to);
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
