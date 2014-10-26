package pompei.maths.syms.visitable;

import pompei.maths.syms.top.Expr;

public class ex {
  public static ConstInt fix(long a) {
    return ConstInt.get(a);
  }
  
  public static ConstInt fix(long top, long bottom) {
    return ConstInt.get(top, bottom);
  }
  
  public static ConstDouble fix(double a) {
    return ConstDouble.get(a);
  }
  
  public static IntPower power(Expr exp, int pow) {
    return new IntPower(exp, pow);
  }
  
  public static Div div(Expr top, Expr bottom) {
    return new Div(top, bottom);
  }
  
  public static Plus plus(Expr left, Expr right) {
    return new Plus(left, right);
  }
  
  public static Minus minus(Expr left, Expr right) {
    return new Minus(left, right);
  }
  
  public static Mul mul(Expr left, Expr right) {
    return new Mul(left, right);
  }
  
  public static Var var(String name) {
    return new Var(name);
  }
  
  public static Expr s(Expr target) {
    return new Skob(target);
  }
}
