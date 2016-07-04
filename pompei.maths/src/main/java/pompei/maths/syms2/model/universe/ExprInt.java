package pompei.maths.syms2.model.universe;

import pompei.maths.syms2.model.universe.Expr;

import java.math.BigInteger;

public class ExprInt extends Expr {
  public final BigInteger value;

  public ExprInt(BigInteger value) {
    this.value = value;
  }

  @Override
  public <T> T visit(ExprVisitor<T> visitor) {
    return visitor.visitExprInt(this);
  }
}
