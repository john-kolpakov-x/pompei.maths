package pompei.maths.syms.visitors.math;

import pompei.maths.syms.top.Expr;
import pompei.maths.syms.visitable.Mul;
import pompei.maths.syms.visitable.Plus;
import pompei.maths.syms.visitors.Scanner;

public class KillMulPlus extends Scanner {
  @Override
  public Expr visitMul(Mul mul) {
    
    Expr left = mul.left;
    Expr right = mul.right;
    
    if (left instanceof Plus && right instanceof Plus) {
      Expr a = ((Plus)left).left.visit(this);
      Expr b = ((Plus)left).right.visit(this);
      Expr c = ((Plus)right).left.visit(this);
      Expr d = ((Plus)right).right.visit(this);
      
      Expr ac = new Mul(a, c);
      Expr bc = new Mul(b, c);
      Expr ad = new Mul(a, d);
      Expr bd = new Mul(b, d);
      
      Expr ac_bc = new Plus(ac, bc);
      Expr ad_bd = new Plus(ad, bd);
      
      return new Plus(ac_bc, ad_bd);
    }
    
    if (left instanceof Plus) {
      Expr a = ((Plus)left).left.visit(this);
      Expr b = ((Plus)left).right.visit(this);
      Expr c = right;
      
      Expr ac = new Mul(a, c);
      Expr bc = new Mul(b, c);
      
      return new Plus(ac, bc);
    }
    
    if (right instanceof Plus) {
      Expr a = left;
      Expr b = ((Plus)right).left.visit(this);
      Expr c = ((Plus)right).right.visit(this);
      
      Expr ab = new Mul(a, b);
      Expr ac = new Mul(a, c);
      
      return new Plus(ab, ac);
    }
    
    return super.visitMul(mul);
  }
}
