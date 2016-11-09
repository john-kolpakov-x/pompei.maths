package pompei.maths.syms_diff.visitors;

import pompei.maths.syms.exceptions.DivByZero;
import pompei.maths.syms_diff.model.Const;
import pompei.maths.syms_diff.model.Form;
import pompei.maths.syms_diff.model.FormVisitor;
import pompei.maths.syms_diff.visitable.*;

public class CalcScanner implements FormVisitor<Form> {
  @Override
  public Form visitVar(Var var) {
    return var;
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
    if (form instanceof Const) {
      return diff.n == 0 ? form : ConstInt.ZERO;
    }

    if (form == diff.form) return diff;

    return new Diff(diff.n, form);
  }

  @Override
  public Form visitPower(Power power) {
    Form form = power.form.visit(this);

    if (form instanceof Const) {
      return ConstOp.pow((Const) form, power.n);
    }

    if (form == power.form) return power;
    return new Power(power.n, form);
  }

  @Override
  public Form visitDiv(Div div) {
    Form top = div.top.visit(this);
    Form bottom = div.bottom.visit(this);

    final boolean topIsConst = top instanceof Const;
    final boolean bottomIsConst = bottom instanceof Const;

    if (topIsConst && bottomIsConst) {
      return ConstOp.div((Const) top, (Const) bottom);
    }

    if (topIsConst && ((Const) top).sign() == 0) return ConstInt.ZERO;
    if (bottomIsConst && ((Const) bottom).sign() == 0) throw new DivByZero();

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

    if (form instanceof Const) {
      return ConstOp.minis((Const) form);
    }

    if (form == minis.form) return minis;
    return new Minis(form);
  }

  @Override
  public Form visitPlus(Plus plus) {
    Form left = plus.left.visit(this);
    Form right = plus.right.visit(this);

    boolean leftIsConst = left instanceof Const;
    boolean rightIsConst = right instanceof Const;

    if (leftIsConst && rightIsConst) {
      return ConstOp.plus((Const) left, (Const) right);
    }

    if (leftIsConst && ((Const) left).sign() == 0) return right;
    if (rightIsConst && ((Const) right).sign() == 0) return left;

    if (left == plus.left && right == plus.right) return plus;
    return new Plus(left, right);
  }

  @Override
  public Form visitMinus(Minus minus) {
    Form left = minus.left.visit(this);
    Form right = minus.right.visit(this);

    boolean leftIsConst = left instanceof Const;
    boolean rightIsConst = right instanceof Const;

    if (leftIsConst && rightIsConst) {
      return ConstOp.minus((Const) left, (Const) right);
    }

    if (leftIsConst && ((Const) left).sign() == 0) {
      return new Minis(right);
    }
    if (rightIsConst && ((Const) right).sign() == 0) {
      return left;
    }

    if (left == minus.left && right == minus.right) return minus;
    return new Minus(left, right);
  }

  @Override
  public Form visitMul(Mul mul) {
    Form left = mul.left.visit(this);
    Form right = mul.right.visit(this);

    boolean leftIsConst = left instanceof Const;
    boolean rightIsConst = right instanceof Const;

    if (leftIsConst && rightIsConst) {
      return ConstOp.mul((Const) left, (Const) right);
    }

    if (leftIsConst && ((Const) left).sign() == 0) return ConstInt.ZERO;
    if (rightIsConst && ((Const) right).sign() == 0) return ConstInt.ZERO;

    if (left == mul.left && right == mul.right) return mul;
    return new Mul(left, right);
  }
}
