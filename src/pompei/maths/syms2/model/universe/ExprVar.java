package pompei.maths.syms2.model.universe;

public class ExprVar extends Expr {
  public final String name;

  public ExprVar(String name) {
    this.name = name;
  }

  @Override
  public <T> T visit(ExprVisitor<T> visitor) {
    return visitor.visitExprVar(this);
  }
}
