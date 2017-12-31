package pompei.maths.syms2.model.universe;

public class ExprFunc2 extends Expr {
  public final Func2 func2;
  public final Expr arg1, arg2;

  public ExprFunc2(Func2 func2, Expr arg1, Expr arg2) {
    this.func2 = func2;
    this.arg1 = arg1;
    this.arg2 = arg2;
  }

  @Override
  public <T> T visit(ExprVisitor<T> visitor) {
    return visitor.visitExprFunc2(this);
  }
}
