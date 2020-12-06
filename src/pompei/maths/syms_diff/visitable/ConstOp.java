package pompei.maths.syms_diff.visitable;

import pompei.maths.syms_diff.model.Const;

public class ConstOp {

  public static Const pow(Const form, int n) {
    if (form instanceof ConstInt) {
      ConstInt ci = (ConstInt) form;
      return ci.innerPow(n);
    }

    return new ConstDouble(Math.pow(form.doubleValue(), n));
  }

  public static Const div(Const top, Const bottom) {
    if (top instanceof ConstInt && bottom instanceof ConstInt) {
      return ((ConstInt) top).innerDiv((ConstInt) bottom);
    }

    return new ConstDouble(top.doubleValue() / bottom.doubleValue());
  }

  public static Const minis(Const form) {
    if (form instanceof ConstInt) {
      return ((ConstInt) form).innerMinis();
    }
    return new ConstDouble(-form.doubleValue());
  }

  public static Const plus(Const left, Const right) {
    if (left instanceof ConstInt && right instanceof ConstInt) {
      return ((ConstInt) left).innerPlus((ConstInt) right);
    }

    return new ConstDouble(left.doubleValue() + right.doubleValue());
  }

  public static Const minus(Const left, Const right) {
    if (left instanceof ConstInt && right instanceof ConstInt) {
      return ((ConstInt) left).innerMinus((ConstInt) right);
    }

    return new ConstDouble(left.doubleValue() - right.doubleValue());
  }

  public static Const mul(Const left, Const right) {
    if (left instanceof ConstInt && right instanceof ConstInt) {
      return ((ConstInt) left).innerMul((ConstInt) right);
    }

    return new ConstDouble(left.doubleValue() * right.doubleValue());
  }
}
