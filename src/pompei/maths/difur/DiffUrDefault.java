package pompei.maths.difur;

public class DiffUrDefault implements DiffUr {

  private final Stepper stepper;
  private int N;
  private F f;
  private double t, h;

  public DiffUrDefault(Stepper stepper) {
    this.stepper = stepper;
  }

  @Override
  public int level() {
    return stepper.level();
  }

  @Override
  public void prepare(int N, F f) {
    this.N = N;
    this.f = f;
    stepper.prepare(N);
  }

  @Override
  public double[] getX() {
    return stepper.getX();
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
