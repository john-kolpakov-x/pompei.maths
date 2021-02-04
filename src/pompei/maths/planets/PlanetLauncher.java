package pompei.maths.planets;

import java.util.List;

public class PlanetLauncher {
  public static void main(String[] args) {
    List<Planet> all = PlanetList.all();
    for (Planet planet : all) {
      System.out.println("B0fJgGN7XW :: " + planet.name + " "
                             + (planet.mass_kg / planet.radius_m / planet.radius_m / 4 / Math.PI / 1e9));
    }
  }
}
