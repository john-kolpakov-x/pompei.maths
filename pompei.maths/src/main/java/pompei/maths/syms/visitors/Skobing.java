package pompei.maths.syms.visitors;

import pompei.maths.syms.top.Expr;
import pompei.maths.syms.top.SimpleExpr;
import pompei.maths.syms.visitable.IntPower;
import pompei.maths.syms.visitable.Minus;
import pompei.maths.syms.visitable.Mul;
import pompei.maths.syms.visitable.Plus;
import pompei.maths.syms.visitable.Skob;

public class Skobing extends Scanner {
  
  public Skobing() {}
  
  public int addedSkobs = 0;
  
  public static Expr add(Expr expr) {
    return expr.visit(new Skobing());
  }
  
  @Override
  public Expr visitMul(Mul mul) {
    Expr left = onMulArg(mul.left.visit(this));
    Expr right = onMulArg(mul.right.visit(this));
    if (left == mul.left && right == mul.right) return mul;
    return new Mul(left, right);
  }
  
  private Expr onMulArg(Expr arg) {
    if (arg instanceof Plus) return s(arg);
    if (arg instanceof Minus) return s(arg);
    return arg;
  }
  
  private Expr s(Expr target) {
    addedSkobs++;
    return new Skob(target);
  }
  
  @Override
  public Expr visitIntPower(IntPower intPower) {
    Expr exp = intPower.exp.visit(this);
    if (exp instanceof SimpleExpr || intPower.exp instanceof Skob) {
      if (exp == intPower.exp) return intPower;
      return new IntPower(exp, intPower.pow);
    }
    return new IntPower(s(exp), intPower.pow);
  }
  
}
