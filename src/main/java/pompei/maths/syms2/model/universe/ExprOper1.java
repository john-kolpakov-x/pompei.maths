package pompei.maths.syms2.model.universe;

public class ExprOper1 extends Expr {
  public final Oper1 oper1;
  public final Expr operand;

  public ExprOper1(Oper1 oper1, Expr operand) {
    this.oper1 = oper1;
    this.operand = operand;
  }

  @Override
  public <T> T visit(ExprVisitor<T> visitor) {
    return visitor.visitExprOper1(this);
  }
}
