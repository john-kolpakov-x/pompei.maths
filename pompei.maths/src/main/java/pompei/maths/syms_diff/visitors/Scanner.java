package pompei.maths.syms_diff.visitors;

import pompei.maths.syms_diff.model.Const;
import pompei.maths.syms_diff.model.Form;
import pompei.maths.syms_diff.model.FormVisitor;
import pompei.maths.syms_diff.visitable.*;

public class Scanner implements FormVisitor<Form> {
  @Override
  public Form visitVar(Var constVar) {
    return constVar;
  }

  @Override
  public Form visitFunc(Func func) {
    return func;
  }

  @Override
  public Form visitConst(Const c) {
    return c;
  }

  @Override
  public Form visitDiff(Diff diff) {
    Form form = diff.form.visit(this);
    if (form == diff.form) return diff;
    return new Diff(diff.n, form);
  }

  @Override
  public Form visitPower(Power power) {
    Form form = power.form.visit(this);
    if (form == power.form) return power;
    return new Power(power.n, form);
  }

  @Override
  public Form visitDiv(Div div) {
    Form top = div.top.visit(this);
    Form bottom = div.bottom.visit(this);
    if (top == div.top && bottom == div.bottom) return div;
    return new Div(top, bottom);
  }

  @Override
  public Form visitSkob(Skob skob) {
    Form form = skob.form.visit(this);
    if (form == skob.form) return skob;
    return new Skob(form);
  }

  @Override
  public Form visitMinis(Minis minis) {
    Form form = minis.form.visit(this);
    if (form == minis.form) return minis;
    return new Minis(form);
  }

  @Override
  public Form visitPlus(Plus plus) {
    Form left = plus.left.visit(this);
    Form right = plus.right.visit(this);
    if (left == plus.left && right == plus.right) return plus;
    return new Plus(left, right);
  }

  @Override
  public Form visitMinus(Minus minus) {
    Form left = minus.left.visit(this);
    Form right = minus.right.visit(this);
    if (left == minus.left && right == minus.right) return minus;
    return new Minus(left, right);
  }

  @Override
  public Form visitMul(Mul mul) {
    Form left = mul.left.visit(this);
    Form right = mul.right.visit(this);
    if (left == mul.left && right == mul.right) return mul;
    return new Mul(left, right);
  }
}
