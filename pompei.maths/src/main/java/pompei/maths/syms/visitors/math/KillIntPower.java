package pompei.maths.syms.visitors.math;

import pompei.maths.syms.top.Expr;
import pompei.maths.syms.visitable.ConstInt;
import pompei.maths.syms.visitable.Div;
import pompei.maths.syms.visitable.IntPower;
import pompei.maths.syms.visitable.Mul;
import pompei.maths.syms.visitors.Scanner;

public class KillIntPower extends Scanner {
  @Override
  public Expr visitIntPower(IntPower intPower) {
    int pow = intPower.pow;
    if (pow == 0) return ConstInt.ONE;
    Expr exp = intPower.exp;
    if (pow == 1) return exp;
    
    int sign = 1;
    if (pow < 0) {
      pow = -pow;
      sign = -1;
    }
    
    Expr ret = exp;
    
    while (pow-- > 0) {
      ret = new Mul(ret, exp);
    }
    
    if (sign < 0) return new Div(ConstInt.ONE, ret);
    
    return ret;
  }
}
