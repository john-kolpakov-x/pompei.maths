package pompei.maths;

public interface Stepper {
  void prepare(int N);
  
  double[] getX();
  
  void step(F f, double t, double h, int N);
  
  int poryadok();
  
}
