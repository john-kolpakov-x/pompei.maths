package pompei.maths.syms_diff.visitors;

import pompei.maths.syms_diff.model.Form;
import pompei.maths.syms_diff.visitable.Mul;
import pompei.maths.syms_diff.visitable.Plus;

public class KillMulPlusVisitor extends Scanner {

  private int count = 0;

  public void reset() {
    count = 0;
  }

  public boolean wasOperations() {
    return count > 0;
  }

  private final boolean deep;

  public KillMulPlusVisitor(boolean deep) {
    this.deep = deep;
  }

  @Override
  public Form visitMul(Mul mul) {
    if (mul.left instanceof Plus) {

      if (mul.right instanceof Plus) {

        Plus L = (Plus) mul.left;
        Plus R = (Plus) mul.right;

        Form m1 = new Mul(L.left, R.left);
        Form m2 = new Mul(L.right, R.left);
        Form m3 = new Mul(L.left, R.right);
        Form m4 = new Mul(L.right, R.right);

        if (deep) {
          m1 = m1.visit(this);
          m2 = m2.visit(this);
          m3 = m3.visit(this);
          m4 = m4.visit(this);
        }

        Plus plus1 = new Plus(m1, m2);
        Plus plus2 = new Plus(m3, m4);

        count++;
        return new Plus(plus1, plus2);
      }

      {
        Plus L = (Plus) mul.left;
        Form X = mul.right;

        Form m1 = new Mul(L.left, X);
        Form m2 = new Mul(L.right, X);

        if (deep) {
          m1 = m1.visit(this);
          m2 = m2.visit(this);
        }

        count++;
        return new Plus(m1, m2);
      }
    }

    if (mul.right instanceof Plus) {
      Form X = mul.left;
      Plus R = (Plus) mul.right;

      Form m1 = new Mul(X, R.left);
      Form m2 = new Mul(X, R.right);

      if (deep) {
        m1 = m1.visit(this);
        m2 = m2.visit(this);
      }

      count++;
      return new Plus(m1, m2);
    }

    return super.visitMul(mul);
  }

}
