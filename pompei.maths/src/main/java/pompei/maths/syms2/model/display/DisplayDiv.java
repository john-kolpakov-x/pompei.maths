package pompei.maths.syms2.model.display;

public class DisplayDiv implements DisplayExpr {
  public final int level;
  public final DisplayExpr top;
  public final DisplayExpr bottom;

  public DisplayDiv(int level, DisplayExpr top, DisplayExpr bottom) {
    this.level = level;
    this.top = top;
    this.bottom = bottom;
  }
}
