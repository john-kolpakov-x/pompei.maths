package pompei.maths.syms.visitable;

import pompei.maths.syms.top.Expr;
import pompei.maths.syms.top.Visitor;

public class Div implements Expr {
  public final Expr top, bottom;
  
  public Div(Expr top, Expr bottom) {
    this.top = top;
    this.bottom = bottom;
  }
  
  @Override
  public <T> T visit(Visitor<T> visitor) {
    return visitor.visitDiv(this);
  }
  
  @Override
  public boolean isConst() {
    return top.isConst() && bottom.isConst();
  }
}
