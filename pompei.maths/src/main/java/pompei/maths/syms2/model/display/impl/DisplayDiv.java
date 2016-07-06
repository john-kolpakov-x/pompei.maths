package pompei.maths.syms2.model.display.impl;

import pompei.maths.syms2.model.display.DisplayExpr;
import pompei.maths.syms2.model.display.DisplayPort;
import pompei.maths.syms2.model.display.Size;
import pompei.maths.utils.WhileNotWorks;

public class DisplayDiv implements DisplayExpr {
  public final int level;
  public final DisplayExpr top;
  public final DisplayExpr bottom;

  public DisplayDiv(int level, DisplayExpr top, DisplayExpr bottom) {
    this.level = level;
    this.top = top;
    this.bottom = bottom;
  }

  @Override
  public void setPort(DisplayPort port) {
    top.setPort(port);
    bottom.setPort(port);
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
