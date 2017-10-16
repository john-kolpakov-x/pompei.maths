package pompei.maths.syms.visitable;

import pompei.maths.syms.top.Expr;
import pompei.maths.syms.top.Visitor;

public class Skob implements Expr {
  
  public final Expr target;
  
  public Skob(Expr target) {
    this.target = target;
  }
  
  @Override
  public <T> T visit(Visitor<T> visitor) {
    return visitor.visitSkob(this);
  }
  
  @Override
  public boolean isConst() {
    return target.isConst();
  }
}
