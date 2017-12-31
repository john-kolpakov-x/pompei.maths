package pompei.maths.syms_diff.visitors.similar;

import pompei.maths.syms_diff.model.Const;
import pompei.maths.syms_diff.model.Form;
import pompei.maths.syms_diff.visitable.*;

public abstract class MulElement {

  public abstract MulElement mul(MulElement element);

  public abstract boolean isZeroPower();

  public abstract Form toForm();

  public static class MulElementComparing implements Comparable<MulElementComparing> {
    public final int type;
    public final String name;
    public final int n;

    public MulElementComparing(int type, String name, int n) {
      this.type = type;
      this.name = name;
      this.n = n;
    }

    @Override
    public int compareTo(MulElementComparing o) {
      {
        int cmp = Integer.compare(type, o.type);
        if (cmp != 0) return cmp;
      }
      {
        int cmp = name.compareTo(o.name);
        if (cmp != 0) return cmp;
      }
      {
        int cmp = Integer.compare(n, o.n);
        if (cmp != 0) return cmp;
      }

      return 0;
    }
  }

  private final MulElementComparing comparing;

  public MulElement(MulElementComparing comparing) {
    this.comparing = comparing;
  }

  public MulElementComparing comparing() {
    return comparing;
  }

  public static MulElement from(Form form) {
    if (form == null) throw new NullPointerException("form == null");
    if (form instanceof Plus) throw new IllegalArgumentException("form instanceof Plus");
    if (form instanceof Mul) throw new IllegalArgumentException("form instanceof Mul");

    if (form instanceof Const) {
      return new MulElementConst((Const) form);
    }

    if (form instanceof Func) {
      Func func = (Func) form;
      return new MulElementFunc(func.name, func.n, 1);
    }

    if (form instanceof Var) {
      Var var = (Var) form;
      return new MulElementVar(var.name, 1);
    }

    if (form instanceof Power) {
      Power power = (Power) form;

      if (power.form instanceof Power) throw new IllegalArgumentException("Please kill all power in power");
      if (power.form instanceof Const) {
        Const c = (Const) power.form;
        return new MulElementConst(ConstOp.pow(c, power.power));
      }

      if (power.form instanceof Func) {
        Func func = (Func) power.form;
        return new MulElementFunc(func.name, func.n, power.power);
      }

      if (power.form instanceof Var) {
        Var var = (Var) power.form;
        return new MulElementVar(var.name, power.power);
      }

      return null;
    }

    return null;
  }
}
