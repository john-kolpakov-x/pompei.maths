package pompei.maths.syms_diff.visitors;

import pompei.maths.syms_diff.model.Const;
import pompei.maths.syms_diff.model.Form;
import pompei.maths.syms_diff.visitable.*;

public class KillDivVisitor extends CalcScanner {
  @Override
  public Form visitDiv(Div div) {
    return new Mul(div.top, new Power(-1, div.bottom)).visit(this);
  }

  @Override
  public Form visitPower(Power power) {
    if (power.power == 0) return ConstInt.ONE;
    Form form = power.form.visit(this);
    if (power.power == 1) return form;

    if (form instanceof Const) {
      return ConstOp.pow((Const) form, power.power);
    }

    if (form instanceof Power) {
      Power sub = (Power) form;
      Form subForm = sub.form;
      int n = power.power * sub.power;
      if (n == 0) return ConstInt.ONE;
      if (n == 1) return subForm.visit(this);
      return new Power(n, subForm).visit(this);
    }

    if (form instanceof Mul) {
      Mul mul = (Mul) form;
      Form left = new Power(power.power, mul.left);
      Form right = new Power(power.power, mul.right);
      return new Mul(left, right).visit(this);
    }

    if (form instanceof Minis) {
      Minis minis = (Minis) form;
      Form subForm = minis.form;
      int n = power.power;
      if (n < 0) n = -n;
      if (n % 2 == 0) return subForm.visit(this);
      return new Minis(new Power(power.power, subForm)).visit(this);
    }

    return power;
  }
}
