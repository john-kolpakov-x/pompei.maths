package pompei.maths.syms.visitors.math;

import pompei.maths.syms.exceptions.ZeroDivisionException;
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
    if (top instanceof ConstInt && bottom instanceof ConstInt) {
      long topValue = ((ConstInt)top).value;
      long bottomValue = ((ConstInt)bottom).value;
      return constDiv(topValue, bottomValue);
    }
    if (top instanceof Const && bottom instanceof Const) {
      Const topConst = (Const)top;
      Const bottomConst = (Const)bottom;
      return topConst.div(bottomConst);
    }
    
    if (top instanceof ConstInt && bottom instanceof Div) {
      Div bot = (Div)bottom;
      if (bot.top instanceof ConstInt && bot.bottom instanceof ConstInt) {
        long c1 = ((ConstInt)top).value;
        long c2 = ((ConstInt)bot.top).value;
        long c3 = ((ConstInt)bot.bottom).value;
        return constDiv(c1 * c3, c2);
      }
    }
    
    if (top instanceof Div && bottom instanceof ConstInt) {
      Div t = (Div)top;
      if (t.top instanceof ConstInt && t.bottom instanceof ConstInt) {
        long c1 = ((ConstInt)t.top).value;
        long c2 = ((ConstInt)t.bottom).value;
        long c3 = ((ConstInt)bottom).value;
        return constDiv(c1, c2 * c3);
      }
    }
    
    if (top == div.top && bottom == div.bottom) return div;
    return new Div(top, bottom);
  }
  
  private static Expr constDiv(long topValue, long bottomValue) {
    if (bottomValue == 0) throw new ZeroDivisionException();
    long sign = 1;
    if (topValue < 0) {
      topValue = -topValue;
      sign = -sign;
    }
    if (bottomValue < 0) {
      bottomValue = -bottomValue;
      sign = -sign;
    }
    if (topValue % bottomValue == 0) return ConstInt.get(topValue / bottomValue);
    long gcd = UtilMath.gcd(topValue, bottomValue);
    topValue /= gcd;
    bottomValue /= gcd;
    ConstInt topRet = ConstInt.get(sign * topValue);
    if (bottomValue == 1) return topRet;
    return new Div(topRet, ConstInt.get(bottomValue));
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
    if (left == plus.left && right == plus.right) {
      return plus;
    }
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
    if (left == minus.left && right == minus.right) {
      return minus;
    }
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
    if (exp instanceof ConstInt) {
      long value = ((ConstInt)exp).value;
      long sign = 1;
      if (pow < 0) {
        pow = -pow;
        sign = -1;
      }
      long ret = value;
      while (pow-- > 1) {
        ret += value;
      }
      
    }
    // TODO Auto-generated method stub
    return super.visitIntPower(intPower);
  }
}
