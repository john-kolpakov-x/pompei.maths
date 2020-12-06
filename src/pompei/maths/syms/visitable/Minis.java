package pompei.maths.syms.visitable;

import pompei.maths.syms.top.Expr;
import pompei.maths.syms.top.Visitor;

public class Minis implements Expr {

  public final Expr target;

  public Minis(Expr target) {
    this.target = target;
  }

  @Override
  public <T> T visit(Visitor<T> visitor) {
    return visitor.visitMinis(this);
  }

  @Override
  public boolean isConst() {
    return target.isConst();
  }
}
