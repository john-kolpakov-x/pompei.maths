package pompei.maths.syms2.model.display;

import pompei.maths.utils.WhileNotWorks;

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

  @Override
  public void setPort(DisplayPort port) {
    in.setPort(port);
  }

  @Override
  public void reset() {
    throw new WhileNotWorks();
  }

  @Override
  public void displayTo(int x, int y) {
    throw new WhileNotWorks();
  }

  @Override
  public Size size() {
    throw new WhileNotWorks();
  }
}
