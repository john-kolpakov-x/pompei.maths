package pompei.maths.syms_diff.visitors;

import pompei.maths.syms_diff.model.Const;
import pompei.maths.syms_diff.model.Form;
import pompei.maths.syms_diff.model.FormVisitor;
import pompei.maths.syms_diff.visitable.ConstInt;
import pompei.maths.syms_diff.visitable.ConstOp;
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

public class KillDiffVisitor implements FormVisitor<Form> {

  private final DiffVisitor differ = new DiffVisitor();

  @Override
  public Form visitDiff(Diff diff) {

    Form form = diff.form.visit(this);

    for (int i = 0, C = diff.n; i < C; i++) {
      form = form.visit(differ);
      form = form.visit(this);
    }

    return form;
  }

  @Override
  public Form visitConst(Const c) {
    return c;
  }

  @Override
  public Form visitVar(Var var) {
    return var;
  }

  @Override
  public Form visitFunc(Func func) {
    return func;
  }

  @Override
  public Form visitDiv(Div div) {
    return new Mul(div.top, new Power(-1, div.bottom)).visit(this);
  }

  @Override
  public Form visitSkob(Skob skob) {
    return skob.form.visit(this);
  }

  @Override
  public Form visitMinis(Minis minis) {
    return new Mul(ConstInt.M_ONE, minis.form).visit(this);
  }

  @Override
  public Form visitPlus(Plus plus) {
    return new Plus(plus.left.visit(this), plus.right.visit(this));
  }

  @Override
  public Form visitMinus(Minus minus) {
    return new Plus(minus.left, new Mul(ConstInt.M_ONE, minus.right)).visit(this);
  }

  @SuppressWarnings("UnnecessaryLocalVariable")
  @Override
  public Form visitMul(Mul mul) {
    Form left = mul.left;
    Form right = mul.right;

    if (left instanceof Plus && right instanceof Plus) {
      Plus pLeft = (Plus) left;
      Plus pRight = (Plus) right;

      Form a = pLeft.left;
      Form b = pLeft.right;
      Form c = pRight.left;
      Form d = pRight.right;

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
      Plus pLeft = (Plus) left;

      Form a = pLeft.left;
      Form b = pLeft.right;
      Form c = right;

      // (a + b)c = ac + bc

      Form ac = new Mul(a, c);
      Form bc = new Mul(b, c);

      return new Plus(ac, bc).visit(this);
    }

    if (right instanceof Plus) {
      Plus pRight = (Plus) right;

      Form a = left;
      Form b = pRight.left;
      Form c = pRight.right;

      // a(b + c) = ab + ac

      Form ab = new Mul(a, b);
      Form ac = new Mul(a, c);

      return new Plus(ab, ac).visit(this);
    }

    {
      left = left.visit(this);
      right = right.visit(this);

      boolean leftIsConst = left instanceof Const;
      boolean rightIsConst = right instanceof Const;

      if (leftIsConst && ((Const) left).sign() == 0) return ConstInt.ZERO;
      if (rightIsConst && ((Const) right).sign() == 0) return ConstInt.ZERO;

      if (leftIsConst && rightIsConst) {
        return ConstOp.mul((Const) left, (Const) right);
      }

      if (left == mul.left && right == mul.right) return mul;
      return new Mul(left, right);
    }
  }

  @Override
  public Form visitPower(Power power) {
    Form form = power.form;
    int n = power.power;
    if (n == 0) return ConstInt.ONE;

    if (n > 0) {
      Form ret = form;
      for (int i = 1; i < n; i++) {
        ret = new Mul(ret, form);
      }
      return form.visit(this);
    }

    form = form.visit(this);

    if (form instanceof Const) {
      return ConstOp.pow((Const) form, n);
    }

    if (form instanceof Power) {
      Power subPower = (Power) form;

      int newN = n + subPower.power;
      if (newN == 0) return ConstInt.ONE;
      if (newN == 1) return subPower.form;
      return new Power(newN, subPower.form).visit(this);
    }

    if (form instanceof Mul) {
      Mul mul = (Mul) form;

      Power left = new Power(n, mul.left);
      Power right = new Power(n, mul.right);

      return new Mul(left, right).visit(this);
    }

    if (form instanceof Func || form instanceof Var) {
      if (form == power.form) return power;
      return new Power(power.power, form);
    }

    throw new CannotKillDiffVisitorForPower(power);
  }
}
