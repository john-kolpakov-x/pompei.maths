package pompei.maths.difur.many_masses;

import java.awt.Color;
import java.awt.Graphics2D;

public class Point extends Node {
  
  public double x, y;
  
  @Override
  public int centerX() {
    return (int)x;
  }
  
  @Override
  public int centerY() {
    return (int)y;
  }
  
  public Point(String id) {
    super(id);
  }
  
  @Override
  public String asStr() {
    return "Point " + id + ' ' + x + ' ' + y;
  }
  
  public static Point parse(String[] split) {
    if (!"Point".equals(split[0])) return null;
    Point ret = new Point(split[1]);
    ret.x = Double.parseDouble(split[2]);
    ret.y = Double.parseDouble(split[3]);
    return ret;
  }
  
  @Override
  public void draw(Graphics2D g) {
    g.setColor(Color.RED);
    int X = (int)x, Y = (int)y;
    g.fillOval(X - 2, Y - 2, 4, 4);
  }
}
