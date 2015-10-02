package pompei.maths.syms_diff.visitors;

import pompei.maths.syms_diff.model.Const;
import pompei.maths.syms_diff.model.Form;
import pompei.maths.syms_diff.visitable.ConstOp;
import pompei.maths.syms_diff.visitable.Diff;
import pompei.maths.syms_diff.visitable.Minus;
import pompei.maths.syms_diff.visitable.Mul;
import pompei.maths.syms_diff.visitable.Plus;

public class MulPlusDiffConvertVisitor extends CalcScanner {
  
  private final DiffVisitor differ = new DiffVisitor();
  
  @Override
  public Form visitMinus(Minus minus) {
    throw new LeftForm();
  }
  
  @Override
  public Form visitMul(Mul mul) {
    Form left = mul.left;
    Form right = mul.right;
    
    if (left instanceof Plus && right instanceof Plus) {
      Plus pleft = (Plus)left;
      Plus pright = (Plus)right;
      
      Form a = pleft.left;
      Form b = pleft.right;
      Form c = pright.left;
      Form d = pright.right;
      
      //     (a + b)(c + d) = ac + ad + bc + bd
      
      Form ac = new Mul(a, c);
      Form ad = new Mul(a, d);
      
      Form bc = new Mul(b, c);
      Form bd = new Mul(b, d);
      
      Form ac_ad = new Plus(ac, ad);
      Form bc_bd = new Plus(bc, bd);
      
      return new Plus(ac_ad, bc_bd).visit(this);
    }
    
    if (left instanceof Plus) {
      Plus pleft = (Plus)left;
      
      Form a = pleft.left;
      Form b = pleft.right;
      Form c = right;
      
      // (a + b)c = ac + bc
      
      Form ac = new Mul(a, c);
      Form bc = new Mul(b, c);
      
      return new Plus(ac, bc).visit(this);
    }
    
    if (right instanceof Plus) {
      Plus pright = (Plus)right;
      
      Form a = left;
      Form b = pright.left;
      Form c = pright.right;
      
      // a(b + c) = ab + ac
      
      Form ab = new Mul(a, b);
      Form ac = new Mul(a, c);
      
      return new Plus(ab, ac).visit(this);
    }
    
    {
      left = left.visit(this);
      right = right.visit(this);
      
      if (left instanceof Const && right instanceof Const) {
        return ConstOp.mul((Const)left, (Const)right);
      }
      
      if (left == mul.left && right == mul.right) return mul;
      return new Mul(left, right);
    }
  }
  
  @Override
  public Form visitDiff(Diff diff) {
    if (diff.n == 0) return diff.form.visit(this);
    
    return new Diff(diff.n - 1, diff.form.visit(differ)).visit(this);
  }
  
}
