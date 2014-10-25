package pompei.maths.syms.visitable;

import pompei.maths.syms.top.Const;
import pompei.maths.syms.top.Visitor;

public class ConstDouble implements Const {
  public final double value;
  
  public ConstDouble(double value) {
    this.value = value;
  }
  
  @Override
  public <T> T visit(Visitor<T> visitor) {
    return visitor.visitConstDouble(this);
  }
}
