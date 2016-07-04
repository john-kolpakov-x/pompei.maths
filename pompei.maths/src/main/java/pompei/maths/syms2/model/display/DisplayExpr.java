package pompei.maths.syms2.model.display;

public interface DisplayExpr {

  void setPort(DisplayPort port);

  void reset();

  void displayTo(int x, int y);

  Size size();
}
