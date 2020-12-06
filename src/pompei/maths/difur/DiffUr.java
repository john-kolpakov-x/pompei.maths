package pompei.maths.difur;

public interface DiffUr {
  int level();

  void prepare(int N, F f);

  double[] getX();

  void setT(double t);

  double getT();

  void setH(double h);

  double getH();

  void step();
}
