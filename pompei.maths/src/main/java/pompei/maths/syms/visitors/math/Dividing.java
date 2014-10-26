package pompei.maths.syms.visitors.math;

import pompei.maths.syms.top.Expr;
import pompei.maths.syms.visitable.Div;
import pompei.maths.syms.visitable.Minis;
import pompei.maths.syms.visitable.Mul;
import pompei.maths.syms.visitable.Plus;
import pompei.maths.syms.visitors.Scanner;

public class Dividing extends Scanner {
  
  @Override
  public Expr visitMul(Mul mul) {
    
    Expr left = mul.left.visit(this);
    Expr right = mul.right.visit(this);
    
    if (left instanceof Div && right instanceof Div) {
      Expr a = ((Div)left).top;
      Expr b = ((Div)left).bottom;
      Expr c = ((Div)right).top;
      Expr d = ((Div)right).bottom;
      
      Expr ac = new Mul(a, c);
      Expr bd = new Mul(b, d);
      
      return new Div(ac, bd);
    }
    
    if (left instanceof Div) {
      Expr a = ((Div)left).top;
      Expr b = ((Div)left).bottom;
      Expr c = right;
      
      Expr ac = new Mul(a, c);
      
      return new Div(ac, b);
    }
    
    if (right instanceof Div) {
      Expr a = left;
      Expr b = ((Div)right).top;
      Expr c = ((Div)right).bottom;
      
      Expr ab = new Mul(a, b);
      
      return new Div(ab, c);
    }
    
    if (left == mul.left && right == mul.right) return mul;
    
    return new Mul(left, right);
  }
  
  @Override
  public Expr visitDiv(Div div) {
    Expr top = div.top.visit(this);
    Expr bottom = div.bottom.visit(this);
    
    if (top instanceof Div && bottom instanceof Div) {
      Expr a = ((Div)top).top;
      Expr b = ((Div)top).bottom;
      Expr c = ((Div)bottom).top;
      Expr d = ((Div)bottom).bottom;
      
      Expr ad = new Mul(a, d);
      Expr bc = new Mul(b, c);
      
      return new Div(ad, bc);
    }
    
    if (top instanceof Div) {
      Expr a = ((Div)top).top;
      Expr b = ((Div)top).bottom;
      Expr c = bottom;
      
      Expr bc = new Mul(b, c);
      
      return new Div(a, bc);
    }
    
    if (bottom instanceof Div) {
      Expr a = top;
      Expr b = ((Div)bottom).top;
      Expr c = ((Div)bottom).bottom;
      
      Expr ac = new Mul(a, c);
      
      return new Div(ac, b);
    }
    
    if (top == div.top && bottom == div.bottom) return div;
    return new Mul(top, bottom);
  }
  
  @Override
  public Expr visitPlus(Plus plus) {
    
    Expr left = plus.left.visit(this);
    Expr right = plus.right.visit(this);
    
    if (left instanceof Div && right instanceof Div) {
      Expr a = ((Div)left).top;
      Expr b = ((Div)left).bottom;
      Expr c = ((Div)right).top;
      Expr d = ((Div)right).bottom;
      
      Expr ad = new Mul(a, d);
      Expr cb = new Mul(c, b);
      Expr bd = new Mul(b, d);
      
      return new Div(new Plus(ad, cb), bd);
    }
    
    if (left instanceof Div) {
      Expr a = ((Div)left).top;
      Expr b = ((Div)left).bottom;
      Expr c = right;
      
      Expr cb = new Mul(c, b);
      
      return new Div(new Plus(a, cb), b);
    }
    
    if (right instanceof Div) {
      Expr a = left;
      Expr b = ((Div)right).top;
      Expr c = ((Div)right).bottom;
      
      Expr ac = new Mul(a, c);
      
      return new Div(new Plus(ac, b), c);
    }
    
    if (left == plus.left && right == plus.right) return plus;
    return new Mul(left, right);
  }
  
  @Override
  public Expr visitMinis(Minis minis) {
    Expr target = minis.target.visit(this);
    if (target instanceof Div) {
      Expr a = ((Div)target).top;
      Expr b = ((Div)target).bottom;
      return new Div(new Minis(a), b);
    }
    if (target == minis.target) return minis;
    return new Minis(target);
  }
}
