package pompei.maths.syms_diff.visitors;

import static pompei.maths.syms_diff.visitable.frm.mul;
import pompei.maths.syms_diff.model.Const;
import pompei.maths.syms_diff.model.Form;
import pompei.maths.syms_diff.model.FormVisitor;
import pompei.maths.syms_diff.visitable.ConstInt;
import pompei.maths.syms_diff.visitable.Diff;
import pompei.maths.syms_diff.visitable.Div;
import pompei.maths.syms_diff.visitable.Func;
import pompei.maths.syms_diff.visitable.Minis;
import pompei.maths.syms_diff.visitable.Minus;
import pompei.maths.syms_diff.visitable.Mul;
import pompei.maths.syms_diff.visitable.Plus;
import pompei.maths.syms_diff.visitable.Power;
import pompei.maths.syms_diff.visitable.Skob;
import pompei.maths.syms_diff.visitable.Var;

public class DiffVisitor implements FormVisitor<Form> {
  
  @Override
  public Form visitVar(Var var) {
    return ConstInt.ZERO;
  }
  
  @Override
  public Form visitFunc(Func func) {
    return new Func(func.name, func.n + 1);
  }
  
  @Override
  public Form visitConst(Const c) {
    return ConstInt.ZERO;
  }
  
  @Override
  public Form visitDiff(Diff diff) {
    Form form = diff.form;
    for (int i = 0, C = diff.n + 1; i < C; i++) {
      form = form.visit(this);
    }
    return form;
  }
  
  @Override
  public Form visitPower(Power p) {
    if (p.n == 0) return ConstInt.ZERO;
    if (p.n == 1) return p.form.visit(this);
    
    // (a^n)' = n a^(n-1) a'
    
    ConstInt n = ConstInt.get(p.n);
    Power pn1 = new Power(p.n - 1, p.form);
    Form pp = p.form.visit(this);
    
    return mul(n, pn1, pp);
  }
  
  @Override
  public Form visitDiv(Div div) {
    Form a = div.top;
    Form b = div.bottom;
    
    //  /   a   \ '     a b'  -  a' b
    //  | ----- |   =  ---------------  =  a b^(-2) b' - a' b^(-1)
    //  \   b   /            b^2
    
    Form a1 = a.visit(this);
    Form b1 = b.visit(this);
    
    Form b_1 = new Power(-1, b);
    Form b_2 = new Power(-2, b);
    
    Form mul1 = mul(a, b_2, b1);
    Form mul2 = mul(a1, b_1);
    
    return new Plus(mul1, new Minis(mul2));
  }
  
  @Override
  public Form visitSkob(Skob skob) {
    return skob.form.visit(this);
  }
  
  @Override
  public Form visitMinis(Minis minis) {
    
    // (-a)' = -a'
    
    return new Minis(minis.form.visit(this));
  }
  
  @Override
  public Form visitPlus(Plus p) {
    
    // (a + b)' = a' + b'
    
    return new Plus(p.left.visit(this), p.right.visit(this));
  }
  
  @Override
  public Form visitMinus(Minus p) {
    
    // (a - b)' = a' - b'
    
    return new Minus(p.left.visit(this), p.right.visit(this));
  }
  
  @Override
  public Form visitMul(Mul m) {
    Form a = m.left;
    Form b = m.right;
    
    // (ab)' = ab' + a'b
    
    Form a1 = a.visit(this);
    Form b1 = b.visit(this);
    
    Form ab1 = new Mul(a, b1);
    Form a1b = new Mul(a1, b);
    
    return new Plus(ab1, a1b);
  }
  
}
