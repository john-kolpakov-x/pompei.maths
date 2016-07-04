package pompei.maths.syms2.model.universe;

public class Brackets extends Expr {
  public final Expr in;

  public Brackets(Expr in) {
    this.in = in;
  }

  @Override
  public <T> T visit(ExprVisitor<T> visitor) {
    return visitor.visitBrackets(this);
  }
}
