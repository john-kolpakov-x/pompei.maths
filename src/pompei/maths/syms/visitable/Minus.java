package pompei.maths.syms.visitable;

import pompei.maths.syms.top.Expr;
import pompei.maths.syms.top.Visitor;

public class Minus implements Expr {
  public final Expr left, right;

  public Minus(Expr left, Expr right) {
    this.left = left;
    this.right = right;
  }

  @Override
  public <T> T visit(Visitor<T> visitor) {
    return visitor.visitMinus(this);
  }

  @Override
  public boolean isConst() {
    return left.isConst() && right.isConst();
  }
}
