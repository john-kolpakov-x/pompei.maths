package pompei.maths.syms.visitors.math;

import pompei.maths.syms.top.Expr;
import pompei.maths.syms.visitable.ConstInt;
import pompei.maths.syms.visitable.Minis;
import pompei.maths.syms.visitable.Mul;
import pompei.maths.syms.visitable.Plus;
import pompei.maths.syms.visitors.Scanner;

public class KillMulOne extends Scanner {
  @Override
  public Expr visitMul(Mul mul) {
    
    if (isOne(mul.left)) return mul.right.visit(this);
    if (isOne(mul.right)) return mul.left.visit(this);
    
    if (isMinisOne(mul.left)) return minis(mul.right.visit(this));
    if (isMinisOne(mul.right)) return minis(mul.left.visit(this));
    
    if (isZero(mul.left)) return ConstInt.ZERO;
    if (isZero(mul.right)) return ConstInt.ZERO;
    
    return super.visitMul(mul);
  }
  
  private Expr minis(Expr expr) {
    if (expr instanceof Minis) return ((Minis)expr).target;
    return new Minis(expr);
  }
  
  private boolean isOne(Expr expr) {
    if (expr instanceof Minis) return isMinisOne(((Minis)expr).target);
    if (expr instanceof ConstInt) return ((ConstInt)expr).isOne();
    return false;
  }
  
  private boolean isMinisOne(Expr expr) {
    if (expr instanceof Minis) return isOne(((Minis)expr).target);
    if (expr instanceof ConstInt) return ((ConstInt)expr).isMinisOne();
    return false;
  }
  
  @Override
  public Expr visitPlus(Plus plus) {
    Expr left = plus.left.visit(this);
    Expr right = plus.right.visit(this);
    
    if (isZero(left)) return right;
    if (isZero(right)) return left;
    
    if (left == plus.left && right == plus.right) return plus;
    return new Plus(left, right);
  }
  
  private boolean isZero(Expr expr) {
    if (expr instanceof Minis) return isZero(((Minis)expr).target);
    if (expr instanceof ConstInt) {
      return ((ConstInt)expr).isZero();
    }
    return false;
  }
}
