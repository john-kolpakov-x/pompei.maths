package pompei.maths.runge_kutta;

import pompei.maths.F;

public class RungeKuttaStepper {
  double x[];
  
  double k1[], k2[], k3[], k4[], tmp[];
  
  void prepare(int N) {
    x = new double[N];
    k1 = new double[N];
    k2 = new double[N];
    k3 = new double[N];
    k4 = new double[N];
    tmp = new double[N];
  }
  
  public void step(F f, double t, double h, int N) {
    
    double k1[] = this.k1;
    double x[] = this.x;
    
    f.f(k1, t, x);
    
    double h2 = h / 2;
    double tmp[] = this.tmp;
    
    for (int i = 0; i < N; i++) {
      tmp[i] = x[i] + h2 * k1[i];
    }
    
    double t_h2 = t + h2;
    double k2[] = this.k2;
    f.f(k2, t_h2, tmp);
    
    for (int i = 0; i < N; i++) {
      tmp[i] = x[i] + h2 * k2[i];
    }
    
    double k3[] = this.k3;
    f.f(k3, t_h2, tmp);
    
    for (int i = 0; i < N; i++) {
      tmp[i] = x[i] + h * k3[i];
    }
    
    double k4[] = this.k4;
    f.f(k4, t + h, tmp);
    
    double h6 = h / 6;
    
    for (int i = 0; i < N; i++) {
      x[i] += (k1[i] + k4[i] + 2 * (k2[i] + k3[i])) * h6;
    }
    
  }
  
}
