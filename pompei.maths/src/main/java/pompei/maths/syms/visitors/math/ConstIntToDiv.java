package pompei.maths.syms.visitors.math;

import java.math.BigInteger;

import pompei.maths.syms.top.Expr;
import pompei.maths.syms.visitable.ConstInt;
import pompei.maths.syms.visitable.Div;
import pompei.maths.syms.visitors.Scanner;

public class ConstIntToDiv extends Scanner {
  @Override
  public Expr visitConstInt(ConstInt x) {
    if (x.bottom.compareTo(BigInteger.ONE) > 0) {
      return new Div(ConstInt.get(x.top), ConstInt.get(x.bottom));
    }
    return x;
  }
}
