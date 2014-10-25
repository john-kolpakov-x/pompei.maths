package pompei.maths.syms.top;

public interface Expr {
  <T> T visit(Visitor<T> visitor);
  
  boolean isConst();
}
