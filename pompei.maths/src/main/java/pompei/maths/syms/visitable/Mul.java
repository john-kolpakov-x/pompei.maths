package pompei.maths.syms.visitable;

import pompei.maths.syms.top.Expr;
import pompei.maths.syms.top.Visitor;

public class Mul implements Expr {
  public final Expr left, right;
  
  public Mul(Expr left, Expr right) {
    if (left == null) throw new NullPointerException();
    if (right == null) throw new NullPointerException();
    this.left = left;
    this.right = right;
  }
  
  @Override
  public <T> T visit(Visitor<T> visitor) {
    return visitor.visitMul(this);
  }
  
  @Override
  public boolean isConst() {
    return left.isConst() && right.isConst();
  }
}
