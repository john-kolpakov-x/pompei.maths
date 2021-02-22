package pompei.maths.planets;

public class Planet {
  public String name;
  public double mass_kg;
  public double radius_m;

  public double surfaceArea() {
    return radius_m * radius_m * Math.PI * 4;
  }
}
