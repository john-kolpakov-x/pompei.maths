package pompei.maths.planets;

import java.util.ArrayList;
import java.util.List;

public class PlanetList {

  public static Planet Меркурий() {
    Planet ret = new Planet();
    ret.name = "Меркурий";
    ret.mass_kg = 3.33022e23;
    ret.radius_m = 2439.7e3;
    return ret;
  }

  public static Planet Венера() {
    Planet ret = new Planet();
    ret.name = "Венера";
    ret.mass_kg = 4.8675e24;
    ret.radius_m = 6051.8e3;
    return ret;
  }

  public static Planet Земля() {
    Planet ret = new Planet();
    ret.name = "Земля";
    ret.mass_kg = 5.9726e24;
    ret.radius_m = 6371e3;
    return ret;
  }

  public static Planet Марс() {
    Planet ret = new Planet();
    ret.name = "Марс";
    ret.mass_kg = 6.4171e23;
    ret.radius_m = 3389.5e3;
    return ret;
  }

  public static Planet Юпитер() {
    Planet ret = new Planet();
    ret.name = "Юпитер";
    ret.mass_kg = 1.8986e27;
    ret.radius_m = 69911e3;
    return ret;
  }

  public static Planet Сатурн() {
    Planet ret = new Planet();
    ret.name = "Сатурн";
    ret.mass_kg = 5.6846e26;
    ret.radius_m = 58232e3;
    return ret;
  }

  public static List<Planet> all() {
    List<Planet> ret = new ArrayList<>();
    ret.add(Меркурий());
    ret.add(Венера());
    ret.add(Земля());
    ret.add(Марс());
    ret.add(Юпитер());
    ret.add(Сатурн());
    ret.add(Уран());
    ret.add(Нептун());
    ret.add(Земля_Луна());
    ret.add(Юпитер_Ио());
    ret.add(Юпитер_Европа());
    ret.add(Юпитер_Ганимед());
    ret.add(Юпитер_Каллисто());
    return ret;
  }

  public static Planet Уран() {
    Planet ret = new Planet();
    ret.name = "Уран";
    ret.mass_kg = 8.6813e25;
    ret.radius_m = 25360e3;
    return ret;
  }

  public static Planet Нептун() {
    Planet ret = new Planet();
    ret.name = "Нептун";
    ret.mass_kg = 1.0243e26;
    ret.radius_m = 24620e3;
    return ret;
  }

  public static Planet Земля_Луна() {
    Planet ret = new Planet();
    ret.name = "Земля_Луна";
    ret.mass_kg = 7.3477e22;
    ret.radius_m = 1737.1e3;
    return ret;
  }

  public static Planet Юпитер_Ио() {
    Planet ret = new Planet();
    ret.name = "Юпитер_Ио";
    ret.mass_kg = 8.9319e22;
    ret.radius_m = 3643e3 / 2;
    return ret;
  }

  public static Planet Юпитер_Европа() {
    Planet ret = new Planet();
    ret.name = "Юпитер_Европа";
    ret.mass_kg = 4.8017e22;
    ret.radius_m = 3122e3 / 2;
    return ret;
  }

  public static Planet Юпитер_Ганимед() {
    Planet ret = new Planet();
    ret.name = "Юпитер_Ганимед";
    ret.mass_kg = 1.4819e23;
    ret.radius_m = 5262e3 / 2;
    return ret;
  }

  public static Planet Юпитер_Каллисто() {
    Planet ret = new Planet();
    ret.name = "Юпитер_Каллисто";
    ret.mass_kg = 1.076e23;
    ret.radius_m = 4821e3 / 2;
    return ret;
  }
}
