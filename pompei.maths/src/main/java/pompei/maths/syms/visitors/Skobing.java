package pompei.maths.syms.visitors;

import pompei.maths.syms.top.Expr;
import pompei.maths.syms.top.SimpleExpr;
import pompei.maths.syms.top.Visitor;
import pompei.maths.syms.visitable.ConstDoubleExpr;
import pompei.maths.syms.visitable.ConstIntExpr;
import pompei.maths.syms.visitable.Div;
import pompei.maths.syms.visitable.IntPower;
import pompei.maths.syms.visitable.Minus;
import pompei.maths.syms.visitable.Mul;
import pompei.maths.syms.visitable.Plus;
import pompei.maths.syms.visitable.Skob;
import pompei.maths.syms.visitable.VarExpr;

public class Skobing implements Visitor<Expr> {
  
  public Skobing() {}
  
  public int addedSkobs = 0;
  
  public static Expr add(Expr expr) {
    return expr.visit(new Skobing());
  }
  
  @Override
  public Expr visitConstDouble(ConstDoubleExpr constDoubleExpr) {
    return constDoubleExpr;
  }
  
  @Override
  public Expr visitConstIntExpr(ConstIntExpr constIntExpr) {
    return constIntExpr;
  }
  
  @Override
  public Expr visitVarExpr(VarExpr varExpr) {
    return varExpr;
  }
  
  @Override
  public Expr visitPlus(Plus plus) {
    return plus;
  }
  
  @Override
  public Expr visitMul(Mul mul) {
    Expr left = onMulArg(mul.left);
    Expr right = onMulArg(mul.right);
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
  public Expr visitMinus(Minus minus) {
    return minus;
  }
  
  @Override
  public Expr visitDiv(Div div) {
    return div;
  }
  
  @Override
  public Expr visitIntPower(IntPower intPower) {
    Expr exp = intPower.exp;
    if (exp instanceof SimpleExpr) return intPower;
    return new IntPower(s(exp), intPower.pow);
  }
  
  @Override
  public Expr visitSkob(Skob skob) {
    return skob;
  }
  
}
