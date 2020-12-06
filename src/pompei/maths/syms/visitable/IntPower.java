package pompei.maths.syms.visitable;

import pompei.maths.syms.top.Expr;
import pompei.maths.syms.top.Visitor;

public class IntPower implements Expr {
  public final Expr exp;
  public final int pow;

  public IntPower(Expr exp, int pow) {
    this.exp = exp;
    this.pow = pow;
  }

  @Override
  public <T> T visit(Visitor<T> visitor) {
    return visitor.visitIntPower(this);
  }

  @Override
  public boolean isConst() {
    return exp.isConst();
  }
}
