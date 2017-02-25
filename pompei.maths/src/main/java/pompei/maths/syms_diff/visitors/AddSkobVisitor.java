package pompei.maths.syms_diff.visitors;

import pompei.maths.syms_diff.model.Const;
import pompei.maths.syms_diff.model.Form;
import pompei.maths.syms_diff.visitable.*;

public class AddSkobVisitor extends Scanner {
  @Override
  public Form visitMul(Mul mul) {
    Form left = mul.left.visit(this);
    Form right = mul.right.visit(this);

    left = skobForMul(left);
    right = skobForMul(right);

    if (left == mul.left && right == mul.right) return mul;

    return new Mul(left, right);
  }

  private Form skobForMul(Form form) {
    if (form instanceof Plus) return new Skob(form);
    if (form instanceof Minus) return new Skob(form);
    return form;
  }

  @Override
  public Form visitPower(Power power) {
    Form form = power.form.visit(this);

    if (isLessThenPower(form)) return new Power(power.power, new Skob(form));

    return power;
  }

  @Override
  public Form visitDiff(Diff diff) {
    Form form = diff.form.visit(this);
    if (needSkobInDiffFor(form)) {
      return new Diff(diff.n, new Skob(form));
    }
    if (form == diff.form) return diff;
    return new Diff(diff.n, form);
  }

  private boolean needSkobInDiffFor(Form form) {
    if (form instanceof Skob) return false;
    if (form instanceof Var) return false;
    if (form instanceof Const) return ((Const) form).sign() < 0;
    return true;
  }

  private boolean isLessThenPower(Form form) {
    //noinspection PointlessBooleanExpression
    return false
        || form instanceof Plus
        || form instanceof Minus
        || form instanceof Div
        || form instanceof Mul
        || (form instanceof Const && ((Const) form).sign() < 0)
        || form instanceof Minis
        || form instanceof Power
        ;
  }
}
