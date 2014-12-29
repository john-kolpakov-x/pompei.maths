package pompei.maths.difur.many_masses;

public class Shar extends Uzel {
  public double R, m;
  public double x, y, vx, vy;
  
  public Shar(String id) {
    super(id);
  }
  
  @Override
  public String asStr() {
    return "Shar " + id + " Rm " + R + ' ' + m + " xy " + x + ' ' + y + " vxy " + vx + ' ' + vy;
  }
  
  public static Shar parse(String split[]) {
    if (!"Shar".equals(split[0])) return null;
    Shar ret = new Shar(split[1]);
    ret.R = Double.parseDouble(split[3]);
    ret.m = Double.parseDouble(split[4]);
    ret.x = Double.parseDouble(split[6]);
    ret.y = Double.parseDouble(split[7]);
    ret.vx = Double.parseDouble(split[9]);
    ret.vy = Double.parseDouble(split[10]);
    return ret;
  }
}
