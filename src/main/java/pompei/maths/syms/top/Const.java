package pompei.maths.syms.top;

public interface Const extends SimpleExpr {
  Const negate();
  
  Const sum(Const other);
  
  Const sub(Const other);
  
  Const mul(Const other);
  
  Const div(Const other);
  
  boolean isZero();
  
  boolean isOne();
  
  boolean isMinisOne();
}
