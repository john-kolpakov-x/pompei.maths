package pompei.maths.syms.visitors.math;

import pompei.maths.syms.top.Expr;
import pompei.maths.syms.visitable.Minis;
import pompei.maths.syms.visitable.Mul;
import pompei.maths.syms.visitable.Plus;
import pompei.maths.syms.visitors.Scanner;

public class KillMulPlus extends Scanner {
  @Override
  public Expr visitMul(Mul mul) {
    
    Expr leftOrig = mul.left.visit(this);
    Expr rightOrig = mul.right.visit(this);
    
    Expr left = leftOrig, right = rightOrig;
    boolean needMinis = false;
    
    if (left instanceof Minis) {
      left = ((Minis)left).target;
      needMinis = !needMinis;
    }
    if (right instanceof Minis) {
      right = ((Minis)right).target;
      needMinis = !needMinis;
    }
    
    if (left instanceof Plus && right instanceof Plus) {
      Expr a = ((Plus)left).left.visit(this);
      Expr b = ((Plus)left).right.visit(this);
      Expr c = ((Plus)right).left.visit(this);
      Expr d = ((Plus)right).right.visit(this);
      
      Expr ac = new Mul(a, c).visit(this);
      Expr bc = new Mul(b, c).visit(this);
      Expr ad = new Mul(a, d).visit(this);
      Expr bd = new Mul(b, d).visit(this);
      
      Expr ac_bc = new Plus(ac, bc);
      Expr ad_bd = new Plus(ad, bd);
      
      return new Plus(ac_bc, ad_bd);
    }
    
    if (left instanceof Plus) {
      Expr a = ((Plus)left).left.visit(this);
      Expr b = ((Plus)left).right.visit(this);
      Expr c = right;
      
      Expr ac = new Mul(a, c).visit(this);
      Expr bc = new Mul(b, c).visit(this);
      
      return new Plus(ac, bc);
    }
    
    if (right instanceof Plus) {
      Expr a = left;
      Expr b = ((Plus)right).left.visit(this);
      Expr c = ((Plus)right).right.visit(this);
      
      Expr ab = new Mul(a, b).visit(this);
      Expr ac = new Mul(a, c).visit(this);
      
      return new Plus(ab, ac);
    }
    
    if (leftOrig == mul.left && rightOrig == mul.right) return mul;
    return new Mul(leftOrig, rightOrig);
  }
}
