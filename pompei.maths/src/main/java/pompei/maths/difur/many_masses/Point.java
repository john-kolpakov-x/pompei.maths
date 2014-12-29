package pompei.maths.difur.many_masses;

public class Point extends Uzel {
  
  public double x, y;
  
  public Point(String id) {
    super(id);
  }
  
  @Override
  public String asStr() {
    return "Point " + id + ' ' + x + ' ' + y;
  }
  
  public static Point parse(String split[]) {
    if (!"Point".equals(split[0])) return null;
    Point ret = new Point(split[1]);
    ret.x = Double.parseDouble(split[2]);
    ret.y = Double.parseDouble(split[3]);
    return ret;
  }
}
