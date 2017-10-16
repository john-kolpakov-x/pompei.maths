package pompei.maths.syms.visitors.math;

import pompei.maths.syms.top.Expr;
import pompei.maths.syms.visitable.ConstInt;
import pompei.maths.syms.visitable.Div;
import pompei.maths.syms.visitable.IntPower;
import pompei.maths.syms.visitable.Mul;
import pompei.maths.syms.visitable.Var;
import pompei.maths.syms.visitors.Scanner;

public class KillIntPower extends Scanner {
  @Override
  public Expr visitIntPower(IntPower intPower) {
    
    Expr exp = intPower.exp;
    int pow = intPower.pow;
    
    while (exp instanceof IntPower) {
      IntPower exp_ = (IntPower)exp;
      pow += exp_.pow;
      exp = exp_.exp;
    }
    
    if (pow == 0) return ConstInt.ONE;
    
    if (pow == 1) return exp.visit(this);
    
    if (exp instanceof ConstInt || exp instanceof Var) {
      if (exp == intPower.exp) return intPower;
      return new IntPower(exp, pow);
    }
    
    int sign = 1;
    if (pow < 0) {
      pow = -pow;
      sign = -1;
    }
    
    Expr ret = exp;
    
    while (pow-- > 1) {
      ret = new Mul(ret, exp);
    }
    
    if (sign < 0) return new Div(ConstInt.ONE, ret);
    
    return ret;
  }
}
