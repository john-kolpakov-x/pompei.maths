package pompei.maths.syms.visitors;

import pompei.maths.syms.top.Const;
import pompei.maths.syms.top.Expr;
import pompei.maths.syms.top.SimpleExpr;
import pompei.maths.syms.visitable.*;

public class Skobing extends Scanner {

  public Skobing() {
  }

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

  @Override
  public Expr visitSkob(Skob skob) {
    Expr target = skob.target.visit(this);
    if (target == skob.target) return skob;
    return new Skob(target);
  }

  @Override
  public Expr visitMinis(Minis minis) {
    Expr target = minis.target.visit(this);
    if (!(target instanceof Const) && !(target instanceof Var) && !(target instanceof Skob)) {
      return new Minis(s(target));
    }
    if (target == minis.target) return minis;
    return new Minis(target);
  }

  @Override
  public Expr visitMinus(Minus minus) {
    if (minus.right instanceof Minus) {
      return new Minus(minus.left, s(minus.right));
    }
    return super.visitMinus(minus);
  }

}
