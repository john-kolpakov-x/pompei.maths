package pompei.maths.syms.visitable;

import pompei.maths.syms.top.Const;
import pompei.maths.syms.top.Visitor;

public class ConstIntExpr implements Const {
  public final int value;
  
  public ConstIntExpr(int value) {
    this.value = value;
  }
  
  @Override
  public <T> T visit(Visitor<T> visitor) {
    return visitor.visitConstIntExpr(this);
  }
  
}
