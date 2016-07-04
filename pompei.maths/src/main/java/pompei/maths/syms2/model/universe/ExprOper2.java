package pompei.maths.syms2.model.universe;

public class ExprOper2 extends Expr {
  public final Oper2 oper2;
  public final Expr left, right;

  public ExprOper2(Oper2 oper2, Expr left, Expr right) {
    this.oper2 = oper2;
    this.left = left;
    this.right = right;
  }

  @Override
  public <T> T visit(ExprVisitor<T> visitor) {
    return visitor.visitExprOper2(this);
  }
}
