package pompei.maths.syms.visitable;

import pompei.maths.syms.top.Const;
import pompei.maths.syms.top.Visitor;

public class ConstInt implements Const {
  public final int value;
  
  public static final ConstInt ZERO = new ConstInt(0);
  public static final ConstInt ONE = new ConstInt(1);
  
  public ConstInt(int value) {
    this.value = value;
  }
  
  @Override
  public <T> T visit(Visitor<T> visitor) {
    return visitor.visitConstIntExpr(this);
  }
  
}
