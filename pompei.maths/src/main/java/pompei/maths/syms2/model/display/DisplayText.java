package pompei.maths.syms2.model.display;

import java.awt.*;

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
}
