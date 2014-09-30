package pompei.maths.runge_kutta;

import pompei.maths.DiffUr;
import pompei.maths.F;

public class RungeKuttaDiffUr implements DiffUr {
  
  private final RungeKuttaStepper stepper = new RungeKuttaStepper();
  private int N;
  private F f;
  private double t, h;
  
  @Override
  public void prepare(int N, F f) {
    this.N = N;
    this.f = f;
    stepper.prepare(N);
  }
  
  @Override
  public double[] getX() {
    return stepper.x;
  }
  
  @Override
  public void setT(double t) {
    this.t = t;
  }
  
  @Override
  public double getT() {
    return t;
  }
  
  @Override
  public void setH(double h) {
    this.h = h;
  }
  
  @Override
  public double getH() {
    return h;
  }
  
  @Override
  public void step() {
    stepper.step(f, t, h, N);
    t += h;
  }
}
