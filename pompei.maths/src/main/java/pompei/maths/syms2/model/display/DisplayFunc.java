package pompei.maths.syms2.model.display;

public class DisplayFunc implements DisplayExpr {
  public final String name;
  public final DisplayExpr[] args;

  public DisplayFunc(String name, DisplayExpr... args) {
    this.name = name;
    this.args = args;
  }
}
