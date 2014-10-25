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
    Expr left = plus.left.visit(this);
    Expr right = plus.right.visit(this);
    if (left == plus.left && right == plus.right) return plus;
    return new Plus(left, right);
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
  public Expr visitMinus(Minus minus) {
    Expr left = minus.left.visit(this);
    Expr right = minus.right.visit(this);
    if (left == minus.left && right == minus.right) return minus;
    return new Minus(left, right);
  }
  
  @Override
  public Expr visitDiv(Div div) {
    Expr left = div.top.visit(this);
    Expr right = div.bottom.visit(this);
    if (left == div.top && right == div.bottom) return div;
    return new Div(left, right);
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
  
  @Override
  public Expr visitSkob(Skob skob) {
    Expr target = skob.target.visit(this);
    if (target == skob.target) return skob;
    return new Skob(target);
  }
  
}
