package pompei.maths.syms2.model.universe;

public class ExprFunc1 extends Expr {
  public final Func1 func1;
  public final Expr arg;

  public ExprFunc1(Func1 func1, Expr arg) {
    this.func1 = func1;
    this.arg = arg;
  }

  @Override
  public <T> T visit(ExprVisitor<T> visitor) {
    return visitor.visitExprFunc1(this);
  }
}
