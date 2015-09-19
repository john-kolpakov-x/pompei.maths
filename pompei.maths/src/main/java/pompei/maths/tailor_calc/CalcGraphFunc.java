package pompei.maths.tailor_calc;

public class CalcGraphFunc {
  public static void main(String[] args) {
    for (double x = 0; x < 4.0005; x += 0.01) {
      double X = x * 10000;
      X = ((int)(X + 0.5)) / 10000.0;
      //System.out.println((X + " " + f(x)).replace('.', ','));
      System.out.println((x + " " + f(x) + " " + A(x) + " " + B(x) + " " + C(x) + " " + D(x))
          .replace('.', ','));
    }
  }
  
  private static double f(double x) {
    double res = 0;
    
    int sign = 1;
    double _X_ = x;
    double fact = 1;
    int _2n_p_1 = 3;
    int _2n = 2;
    
    final double x2 = x * x;
    
    for (int n = 1;;) {
      
      double add = _2n * _X_ / (_2n_p_1 * fact);
      
      double newRes = res + sign * add;
      
      if (newRes == res) return res;
      
      res = newRes;
      
      n++;
      
      _X_ *= x2;
      fact *= n;
      _2n_p_1 += 2;
      _2n += 2;
      sign = -sign;
      
    }
    
  }
  
  private static double A(double x) {
    double res = 0;
    
    double x2 = x * x;
    int sign = 1;
    
    double top = 1;
    double fact = 1;
    
    for (int n = 1;;) {
      double add = top / fact;
      
      double newRes = res + sign * add;
      
      if (newRes == res) return res;
      
      res = newRes;
      
      n++;
      
      sign = -sign;
      top *= x2;
      fact *= n;
      
    }
    
  }
  
  private static double B(double r) {
    double res = 0;
    
    double r2 = r * r;
    int sign = 1;
    
    double top = 1;
    double fact = 1;
    
    for (int n = 1;;) {
      double add = top / fact / (2 * n + 1);
      
      double newRes = res + sign * add;
      
      if (newRes == res) return res;
      
      res = newRes;
      
      n++;
      
      sign = -sign;
      top *= r2;
      fact *= n;
      
    }
    
  }
  
  private static double C(double r) {
    double res = 0;
    
    double r2 = r * r;
    int sign = 1;
    
    double top = r;
    double fact = 1;
    
    for (int n = 1;;) {
      double add = (2 * n) * top / fact / (2 * n + 1);
      
      double newRes = res + sign * add;
      
      if (newRes == res) return res;
      
      res = newRes;
      
      n++;
      
      sign = -sign;
      top *= r2;
      fact *= n;
      
    }
    
  }
  
  private static double D(double r) {
    double res = 0;
    
    double r2 = r * r;
    int sign = 1;
    
    double top = r * r2;
    double fact = 1;
    
    for (int n = 1;;) {
      double add = (2 * n) * top / fact / (2 * n + 1);
      
      double newRes = res + sign * add;
      
      if (newRes == res) return res / Math.sqrt(Math.PI) * 2;
      
      res = newRes;
      
      n++;
      
      sign = -sign;
      top *= r2;
      fact *= n;
      
    }
    
  }
}
