package pompei.maths.syms.visitable;

import pompei.maths.syms.top.SimpleExpr;
import pompei.maths.syms.top.Visitor;

public class Var implements SimpleExpr {
  public final String name;

  public Var(String name) {
    this.name = name;
  }

  @Override
  public <T> T visit(Visitor<T> visitor) {
    return visitor.visitVar(this);
  }

  @Override
  public boolean isConst() {
    return false;
  }
}
