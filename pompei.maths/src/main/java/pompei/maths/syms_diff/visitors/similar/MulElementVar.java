package pompei.maths.syms_diff.visitors.similar;

import java.util.Objects;
import pompei.maths.syms_diff.model.Form;
import pompei.maths.syms_diff.visitable.ConstInt;
import pompei.maths.syms_diff.visitable.Power;
import pompei.maths.syms_diff.visitable.Var;

public class MulElementVar extends MulElement {
  public final String name;
  public final int power;

  public MulElementVar(String name, int power) {
    super(new MulElementComparing(1, name, 0));
    this.name = name;
    this.power = power;
  }

  @Override
  public MulElement mul(MulElement element) {
    if (!(element instanceof MulElementVar)) return null;
    MulElementVar another = (MulElementVar) element;
    if (!Objects.equals(name, another.name)) return null;
    return new MulElementVar(name, power + another.power);
  }

  @Override
  public boolean isZeroPower() {
    return power == 0;
  }

  @Override
  public Form toForm() {
    if (power == 0) return ConstInt.ONE;
    if (power == 1) return new Var(name);
    return new Power(power, new Var(name));
  }

  @Override
  public String toString() {
    return "V{" + name + pow() + "}";
  }

  private String pow() {
    return power == 1 ? "" : "^" + power;
  }
}
