package pompei.maths;

public class Stepper_H3 implements Stepper {
  double x[];
  
  double k1[], k2[], tmp[];
  
  @Override
  public void prepare(int N) {
    x = new double[N];
    k1 = new double[N];
    k2 = new double[N];
    tmp = new double[N];
  }
  
  @Override
  public void step(F f, double t, double h, int N) {
    
    double k1[] = this.k1;
    double x[] = this.x;
    
    f.f(k1, t, x);
    
    double tmp[] = this.tmp;
    
    for (int i = 0; i < N; i++) {
      tmp[i] = x[i] + h * k1[i];
    }
    
    double t_h = t + h;
    double k2[] = this.k2;
    f.f(k2, t_h, tmp);
    
    double h2 = h / 2;
    
    for (int i = 0; i < N; i++) {
      x[i] += (k1[i] + k2[i]) * h2;
    }
    
  }
  
  @Override
  public double[] getX() {
    return x;
  }
  
  @Override
  public int poryadok() {
    return 3;
  }
}
