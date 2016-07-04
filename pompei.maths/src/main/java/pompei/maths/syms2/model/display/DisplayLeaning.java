package pompei.maths.syms2.model.display;

import java.util.Collection;

public class DisplayLeaning implements DisplayExpr {
  public final DisplayExpr base;
  public final DisplayExpr leaning;
  public final boolean right;
  public final double upFactor;

  public DisplayLeaning(DisplayExpr base, DisplayExpr leaning, boolean right, double upFactor) {
    this.base = base;
    this.leaning = leaning;
    this.right = right;
    this.upFactor = upFactor;
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
    if (len == 2) return new DisplayLeaning(order[offset], order[offset + 1], true, 0);
    return new DisplayLeaning(order[offset], displayOrder(order, offset + 1, len - 1), true, 0);
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
}
