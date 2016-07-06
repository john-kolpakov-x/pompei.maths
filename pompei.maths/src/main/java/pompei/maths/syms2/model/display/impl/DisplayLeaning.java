package pompei.maths.syms2.model.display.impl;

import pompei.maths.syms2.model.display.DisplayExpr;
import pompei.maths.syms2.model.display.DisplayPort;
import pompei.maths.syms2.model.display.Size;

import java.util.Collection;

public class DisplayLeaning implements DisplayExpr {
  public final DisplayExpr base;
  public final DisplayExpr leaned;
  public final boolean right;
  public final double upFactor;
  public final double spaceFactor;

  public DisplayLeaning(DisplayExpr base, DisplayExpr leaned, boolean right, double upFactor, double spaceFactor) {
    this.base = base;
    this.leaned = leaned;
    this.right = right;
    this.upFactor = upFactor;
    this.spaceFactor = spaceFactor;
  }

  public static DisplayExpr displayOrder(DisplayExpr... order) {
    return displayOrder(order, 0, order.length);
  }

  public static DisplayExpr displayOrder(DisplayExpr[] order, int offset, int len) {
    if (len == 0 || order == null) {
      //noinspection ImplicitArrayToString
      throw new IllegalArgumentException("len = " + len + ", order = " + order);
    }

    if (len == 1) return order[offset];
    if (len == 2) return new DisplayLeaning(order[offset], order[offset + 1], true, 0, 0);
    return new DisplayLeaning(order[offset], displayOrder(order, offset + 1, len - 1), true, 0, 0);
  }

  public static DisplayExpr displayOrder(Collection<DisplayExpr> collection) {
    int size = collection.size();
    if (size == 1) return collection.iterator().next();
    DisplayExpr[] order = new DisplayExpr[size];
    int index = 0;
    for (DisplayExpr displayExpr : collection) {
      order[index++] = displayExpr;
    }
    return displayOrder(order, 0, size);
  }

  @Override
  public void setPort(DisplayPort port) {
    base.setPort(port);
    leaned.setPort(port);
  }

  @Override
  public void reset() {
    size = null;
    base.reset();
    leaned.reset();
  }

  @Override
  public void displayTo(int x, int y) {
    size();
    base.displayTo(x + baseDeltaX, y);
    leaned.displayTo(x + leanedDeltaX, y - leanedDeltaY);
  }

  private int leanedDeltaX, leanedDeltaY, baseDeltaX;
  private Size size = null;

  @Override
  public Size size() {
    if (size != null) return size;

    Size baseSize = base.size();
    Size leanedSize = leaned.size();

    if (upFactor >= 0) {
      int fullOne = baseSize.top + leanedSize.bottom;
      leanedDeltaY = (int) (fullOne * upFactor + 0.5);
    } else {
      int fullOne = baseSize.bottom + leanedSize.top;
      leanedDeltaY = (int) (fullOne * upFactor - 0.5);
    }

    int space = (int) (spaceFactor * baseSize.height() + 0.5);

    if (right) {
      leanedDeltaX = baseSize.width + space;
      baseDeltaX = 0;
    } else {
      leanedDeltaX = 0;
      baseDeltaX = leanedSize.width + space;
    }

    int top = Math.max(baseSize.top, leanedSize.top + leanedDeltaY);
    int bottom = Math.max(baseSize.bottom, leanedSize.bottom - leanedDeltaY);

    return size = new Size(top, bottom, baseSize.width + leanedSize.width + space);
  }
}
