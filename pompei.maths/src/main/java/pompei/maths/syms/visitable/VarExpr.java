package pompei.maths.syms.visitable;

import pompei.maths.syms.top.Expr;
import pompei.maths.syms.top.Visitor;

public class VarExpr implements Expr {
  public final String varName;
  
  public VarExpr(String varName) {
    this.varName = varName;
  }
  
  @Override
  public <T> T visit(Visitor<T> visitor) {
    return visitor.visitVarExpr(this);
  }
}
