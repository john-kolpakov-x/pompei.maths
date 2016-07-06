package pompei.maths.syms2.model.display;

import java.awt.Graphics2D;
import java.awt.RenderingHints;

public class DisplayPortImpl implements DisplayPort {
  private Graphics2D graphics;
  public int levelOffset;

  @Override
  public Graphics2D graphics() {
    return graphics;
  }

  public void setGraphics(Graphics2D graphics) {
    prepareHints(graphics);
    this.graphics = graphics;
  }

  protected void prepareHints(Graphics2D graphics) {
    RenderingHints rh = new RenderingHints(
        RenderingHints.KEY_TEXT_ANTIALIASING,
        RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

    graphics.setRenderingHints(rh);
  }

  private static final float SIZES_PLUS[] = new float[]{70, 75, 80, 85, 90, 95, 100, 105};
  private static final float SIZES_MINUS[] = new float[]{65, 60, 55, 45, 40, 35, 30, 25, 20, 15, 10, 5};

  @Override
  public float getFontSize(int level) {
    int realLevel = level + levelOffset;

    if (realLevel >= 0) {
      if (realLevel >= SIZES_PLUS.length) realLevel = SIZES_PLUS.length - 1;
      return SIZES_PLUS[realLevel];
    }

    {
      realLevel = -1 - realLevel;
      if (realLevel >= SIZES_MINUS.length) realLevel = SIZES_MINUS.length - 1;
      return SIZES_MINUS[realLevel];
    }
  }
}
