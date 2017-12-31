package pompei.maths.syms.visitors.math;

import pompei.maths.syms.top.Expr;
import pompei.maths.syms.visitable.Minus;
import pompei.maths.syms.visitable.Plus;
import pompei.maths.syms.visitors.Scanner;

public class ReorganizeMinuses extends Scanner {
  
  @Override
  public Expr visitMinus(Minus minus) {
    if (minus.right instanceof Plus) {
      Expr a = minus.left;
      Expr b = ((Plus)minus.right).left;
      Expr c = ((Plus)minus.right).right;
      
      Expr ab = new Minus(a, b);
      return new Minus(ab, c);
    }
    if (minus.right instanceof Minus) {
      Expr a = minus.left;
      Expr b = ((Minus)minus.right).left;
      Expr c = ((Minus)minus.right).right;
      
      Expr ab = new Minus(a, b);
      return new Plus(ab, c);
    }
    return super.visitMinus(minus);
  }
  
}
