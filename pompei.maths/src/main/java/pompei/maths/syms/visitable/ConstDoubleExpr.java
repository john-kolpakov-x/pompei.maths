package pompei.maths.syms.visitable;

import pompei.maths.syms.top.Const;
import pompei.maths.syms.top.Visitor;

public class ConstDoubleExpr implements Const {
  public final double value;
  
  public ConstDoubleExpr(double value) {
    this.value = value;
  }
  
  @Override
  public <T> T visit(Visitor<T> visitor) {
    return visitor.visitConstDouble(this);
  }
}
