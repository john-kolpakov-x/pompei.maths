package pompei.maths.difur.many_masses;

import java.awt.Color;
import java.awt.Graphics2D;

public class Ball extends Node {
  public double R, m;
  public double x, y, vx, vy;
  
  @Override
  public int centerX() {
    return (int)x;
  }
  
  @Override
  public int centerY() {
    return (int)y;
  }
  
  public Ball(String id) {
    super(id);
  }
  
  @Override
  public String asStr() {
    return "Shar " + id + " Rm " + R + ' ' + m + " xy " + x + ' ' + y + " vxy " + vx + ' ' + vy;
  }
  
  public static Ball parse(String[] split) {
    if (!"Shar".equals(split[0])) return null;
    Ball ret = new Ball(split[1]);
    ret.R = Double.parseDouble(split[3]);
    ret.m = Double.parseDouble(split[4]);
    ret.x = Double.parseDouble(split[6]);
    ret.y = Double.parseDouble(split[7]);
    ret.vx = Double.parseDouble(split[9]);
    ret.vy = Double.parseDouble(split[10]);
    return ret;
  }
  
  @Override
  public void draw(Graphics2D g) {
    g.setColor(Color.BLACK);
    
    int X = (int)x, Y = (int)y, RR = (int)R;
    g.drawOval(X - RR, Y - RR, 2 * RR, 2 * RR);
  }
}
