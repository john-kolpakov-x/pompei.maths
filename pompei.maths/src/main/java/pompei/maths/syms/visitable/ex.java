package pompei.maths.syms.visitable;

import pompei.maths.syms.top.Expr;

public class ex {
  public static ConstIntExpr fix(int a) {
    return new ConstIntExpr(a);
  }
  
  public static ConstDoubleExpr fix(double a) {
    return new ConstDoubleExpr(a);
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
  
  public static VarExpr var(String name) {
    return new VarExpr(name);
  }
}
