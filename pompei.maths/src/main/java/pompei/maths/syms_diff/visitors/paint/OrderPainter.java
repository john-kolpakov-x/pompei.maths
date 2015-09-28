package pompei.maths.syms_diff.visitors.paint;

public class OrderPainter {
  private OrderPainter() {}
  
  static Painter order2(Painter left, Painter right) {
    return UnionPainter.union(left, OffsetPainter.offset(right, left.getSize().width, 0));
  }
  
  public static Painter order(Painter first, Painter second, Painter... other) {
    Painter ret = order2(first, second);
    for (Painter painter : other) {
      ret = order2(ret, painter);
    }
    return ret;
  }
}
