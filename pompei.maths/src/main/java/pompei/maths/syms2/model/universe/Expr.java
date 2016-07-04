package pompei.maths.syms2.model.universe;

public abstract class Expr {
  public abstract <T> T visit(ExprVisitor<T> visitor);
}
