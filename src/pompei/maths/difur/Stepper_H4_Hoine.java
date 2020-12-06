package pompei.maths.difur;

public class Stepper_H4_Hoine implements Stepper {
  double[] x;
  
  double[] k1, k2, k3, tmp;
  
  @Override
  public void prepare(int N) {
    x = new double[N];
    k1 = new double[N];
    k2 = new double[N];
    k3 = new double[N];
    tmp = new double[N];
  }
  
  @Override
  public void step(F f, double t, double h, int N) {
    
    double[] k1 = this.k1;
    double[] x = this.x;
    
    f.f(k1, t, x);
    
    double h3 = h / 3;
    double[] tmp = this.tmp;
    
    for (int i = 0; i < N; i++) {
      tmp[i] = x[i] + h3 * k1[i];
    }
    
    double t_h3 = t + h3;
    double[] k2 = this.k2;
    f.f(k2, t_h3, tmp);
    
    double _2h3 = 2 * h3;
    
    for (int i = 0; i < N; i++) {
      tmp[i] = x[i] + _2h3 * k2[i];
    }
    
    double[] k3 = this.k3;
    double t_2h3 = t + _2h3;
    f.f(k3, t_2h3, tmp);
    
    for (int i = 0; i < N; i++) {
      x[i] += (0.25 * k1[i] + 0.75 * k3[i]) * h;
    }
    
  }
  
  @Override
  public double[] getX() {
    return x;
  }
  
  @Override
  public int level() {
    return 4;
  }
}
