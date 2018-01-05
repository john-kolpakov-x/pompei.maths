package pompei.maths.end_element;

public class Context {
  public double dt;//временной квант

  //вязкость
  public double mu(double p/*давление*/) {
    return 0.001;
  }

  //трение о стенку
  public double muWall(double p/*давление*/) {
    return 0.001;
  }
}
