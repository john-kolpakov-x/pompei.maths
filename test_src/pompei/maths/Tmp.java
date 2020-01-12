package pompei.maths;

public class Tmp {
  public static void main(String[] args) {
    // Масса Солнца в кг
    double Mo = 1.9885e30;

    // большая полуось орбиты Марса в метрах
    double a = 2.2794382e8 * 1000.0;

    // эксцентриситет орбиты Марса
    double e = 0.0933941;

    // малая полуось орбиты Марса в метрах
    double b = a * Math.sqrt(1 - e * e);

    System.out.println("b = " + b);

    // Среднее расстояние от Солнца до Марса в метрах
    double L = (a + b) / 2;

    // Марсианский год в секундах
    double T = 686.98 * 24 * 60 * 60;

    System.out.println("L = " + L);
    System.out.println("T = " + T);

    double _1kilogram = 1 / Mo;
    double _1meter = 1 / L;
    double _1second = 2 * Math.PI / T;

    System.out.println("_1kilogram = " + _1kilogram);
    System.out.println("_1meter    = " + _1meter);
    System.out.println("_1second   = " + _1second);

    double G = 6.6743015e-11 * pow(_1meter, 3) * pow(_1second, -2) * pow(_1kilogram, -1);

    System.out.println("G = " + G);
  }

  private static double pow(double x, int n) {
    double ret = 1;
    boolean plus = true;
    if (n < 0) {
      plus = false;
      n = -n;
    }
    for (int i = 0; i < n; i++) {
      ret *= x;
    }
    return plus ? ret : 1 / ret;
  }

}
