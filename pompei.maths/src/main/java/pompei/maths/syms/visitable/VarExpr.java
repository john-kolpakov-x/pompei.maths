package pompei.maths.syms.visitable;

import pompei.maths.syms.top.SimpleExpr;
import pompei.maths.syms.top.Visitor;

public class VarExpr implements SimpleExpr {
  public final String name;
  
  public VarExpr(String varName) {
    this.name = varName;
  }
  
  @Override
  public <T> T visit(Visitor<T> visitor) {
    return visitor.visitVarExpr(this);
  }
}
