package pompei.maths.syms_diff.visitors.similar;

import pompei.maths.syms_diff.model.Form;
import pompei.maths.syms_diff.visitable.ConstInt;
import pompei.maths.syms_diff.visitable.Func;
import pompei.maths.syms_diff.visitable.Power;

import java.util.Objects;

public class MulElementFunc extends MulElement {
  public final String name;
  public final int n;
  public final int power;

  public MulElementFunc(String name, int n, int power) {
    super(new MulElementComparing(2, name, n));
    this.name = name;
    this.n = n;
    this.power = power;
  }

  @Override
  public MulElement mul(MulElement element) {
    if (!(element instanceof MulElementFunc)) return null;
    MulElementFunc another = (MulElementFunc) element;
    if (!Objects.equals(name, another.name)) return null;
    if (n != another.n) return null;
    return new MulElementFunc(name, n, this.power + another.power);
  }

  @Override
  public boolean isZeroPower() {
    return power == 0;
  }

  @Override
  public Form toForm() {
    if (power == 0) return ConstInt.ONE;
    if (power == 1) return new Func(name, n);
    return new Power(power, new Func(name, n));
  }

  @Override
  public String toString() {
    return "F{" + name + diff() + pow() + "}";
  }

  private String diff() {
    return n == 0 ? "" : "|" + n;
  }

  private String pow() {
    return power == 1 ? "" : "^" + power;
  }
}
