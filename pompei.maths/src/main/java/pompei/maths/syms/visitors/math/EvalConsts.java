package pompei.maths.syms.visitors.math;

import pompei.maths.syms.top.Const;
import pompei.maths.syms.top.Expr;
import pompei.maths.syms.visitable.ConstInt;
import pompei.maths.syms.visitable.Div;
import pompei.maths.syms.visitable.IntPower;
import pompei.maths.syms.visitable.Minis;
import pompei.maths.syms.visitable.Minus;
import pompei.maths.syms.visitable.Mul;
import pompei.maths.syms.visitable.Plus;
import pompei.maths.syms.visitors.Scanner;

public class EvalConsts extends Scanner {
  @Override
  public Expr visitDiv(Div div) {
    Expr top = div.top.visit(this);
    Expr bottom = div.bottom.visit(this);
    if (top instanceof Const && bottom instanceof Const) {
      Const topConst = (Const)top;
      Const bottomConst = (Const)bottom;
      return topConst.div(bottomConst);
    }
    
    if (top == div.top && bottom == div.bottom) return div;
    return new Div(top, bottom);
  }
  
  @Override
  public Expr visitMul(Mul mul) {
    Expr left = mul.left.visit(this);
    Expr right = mul.right.visit(this);
    if (left instanceof Const && right instanceof Const) {
      Const leftC = (Const)left;
      Const rightC = (Const)right;
      return leftC.mul(rightC);
    }
    if (left == mul.left && right == mul.right) {
      return mul;
    }
    return new Mul(left, right);
  }
  
  @Override
  public Expr visitPlus(Plus plus) {
    Expr left = plus.left.visit(this);
    Expr right = plus.right.visit(this);
    if (left instanceof Const && right instanceof Const) {
      Const leftC = (Const)left;
      Const rightC = (Const)right;
      return leftC.sum(rightC);
    }
    if (left == plus.left && right == plus.right) return plus;
    return new Plus(left, right);
  }
  
  @Override
  public Expr visitMinus(Minus minus) {
    Expr left = minus.left.visit(this);
    Expr right = minus.right.visit(this);
    if (left instanceof Const && right instanceof Const) {
      Const leftC = (Const)left;
      Const rightC = (Const)right;
      return leftC.sub(rightC);
    }
    if (left == minus.left && right == minus.right) return minus;
    return new Minus(left, right);
  }
  
  @Override
  public Expr visitMinis(Minis minis) {
    Expr target = minis.target.visit(this);
    if (target instanceof Const) {
      return ((Const)target).negate();
    }
    if (target == minis.target) return minis;
    return new Minis(target);
  }
  
  @Override
  public Expr visitIntPower(IntPower intPower) {
    int pow = intPower.pow;
    
    if (pow == 0) return ConstInt.ONE;
    
    Expr exp = intPower.exp.visit(this);
    if (pow == 1) return exp;
    
    if (exp instanceof ConstInt) {
      long top = ((ConstInt)exp).top;
      long bottom = ((ConstInt)exp).bottom;
      
      long sign = 1;
      if (pow < 0) {
        pow = -pow;
        sign = -1;
      }
      
      long retTop = top;
      long retBottom = bottom;
      while (pow-- > 1) {
        retTop *= top;
        retBottom *= bottom;
      }
      
      if (sign < 0) {
        long tmp = retTop;
        retTop = retBottom;
        retBottom = tmp;
      }
      
      return ConstInt.get(retTop, retBottom);
    }
    
    if (exp == intPower.exp) return intPower;
    return new IntPower(exp, pow);
  }
}
