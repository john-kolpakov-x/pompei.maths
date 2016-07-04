package pompei.maths.syms2.model.display;

public interface DisplayExpr {

  void setPort(DisplayPort port);

  void reset();

  void display();

  Size size();
}
