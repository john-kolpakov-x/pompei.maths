package pompei.maths.syms2.model.display;

import pompei.maths.utils.WhileNotWorks;

import java.awt.Color;

public class DisplayText implements DisplayExpr {
  public final int level;
  public final String text;
  public final Color color;
  public final boolean bold;
  public final boolean italic;

  public DisplayText(int level, String text, Color color, boolean bold, boolean italic) {
    this.level = level;
    this.text = text;
    this.color = color;
    this.bold = bold;
    this.italic = italic;
  }

  @Override
  public void setPort(DisplayPort port) {
    
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
