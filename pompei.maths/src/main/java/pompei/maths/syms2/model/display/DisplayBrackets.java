package pompei.maths.syms2.model.display;

import java.awt.Color;

public class DisplayBrackets implements DisplayExpr {
  public final DisplayExpr in;
  public final BracketsType type;
  public final Color color;

  public DisplayBrackets(DisplayExpr in, BracketsType type, Color color) {
    this.in = in;
    this.type = type;
    this.color = color;
  }
}
