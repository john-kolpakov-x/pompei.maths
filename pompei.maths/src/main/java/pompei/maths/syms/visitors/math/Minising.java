package pompei.maths.syms.visitors.math;

import pompei.maths.syms.top.Expr;
import pompei.maths.syms.visitable.Minis;
import pompei.maths.syms.visitable.Minus;
import pompei.maths.syms.visitable.Mul;
import pompei.maths.syms.visitable.Plus;
import pompei.maths.syms.visitors.Scanner;

public class Minising extends Scanner {
  
  private final boolean killMinus;
  
  public Minising(boolean killMinus) {
    this.killMinus = killMinus;
  }
  
  @Override
  public Expr visitMinus(Minus minus) {
    Expr left = minus.left.visit(this);
    Expr right = minus.right.visit(this);
    
    if (killMinus) return new Plus(left, minis(right));
    
    if (right instanceof Minis) {
      return new Plus(left, ((Minis)right).target);
    }
    
    if (left == minus.left && right == minus.right) return minus;
    return new Minus(left, right);
  }
  
  private static Expr minis(Expr expr) {
    if (expr instanceof Minis) return ((Minis)expr).target;
    return new Minis(expr);
  }
  
  @Override
  public Expr visitPlus(Plus plus) {
    if (killMinus) return super.visitPlus(plus);
    
    Expr left = plus.left.visit(this);
    Expr right = plus.right.visit(this);
    
    if (right instanceof Minis) {
      return new Minus(left, ((Minis)right).target);
    }
    
    if (left == plus.left && right == plus.right) return plus;
    return new Plus(left, right);
  }
  
  @Override
  public Expr visitMul(Mul mul) {
    Expr left = mul.left.visit(this);
    Expr right = mul.right.visit(this);
    
    boolean needMinis = false;
    
    if (left instanceof Minis) {
      left = ((Minis)left).target;
      needMinis = !needMinis;
    }
    if (right instanceof Minis) {
      right = ((Minis)right).target;
      needMinis = !needMinis;
    }
    
    if (needMinis) return new Minis(new Mul(left, right));
    
    if (left == mul.left && right == mul.right) return mul;
    return new Mul(left, right);
  }
  
  @Override
  public Expr visitMinis(Minis minis) {
    Expr target = minis.target.visit(this);
    if (target instanceof Minis) {
      return ((Minis)target).target;//минус на минус даёт плюс (а плюс - это ничего не делать)
    }
    if (target == minis.target) return minis;
    return new Minis(target);
  }
}
