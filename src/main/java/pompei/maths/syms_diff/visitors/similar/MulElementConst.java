package pompei.maths.syms_diff.visitors.similar;

import pompei.maths.syms_diff.model.Const;
import pompei.maths.syms_diff.model.Form;
import pompei.maths.syms_diff.visitable.ConstOp;

public class MulElementConst extends MulElement {
  public final Const value;

  public MulElementConst(Const value) {
    super(new MulElementComparing(0, "", 0));
    this.value = value;
  }

  @Override
  public MulElement mul(MulElement element) {
    if (!(element instanceof MulElementConst)) return null;
    return new MulElementConst(ConstOp.mul(value, ((MulElementConst) element).value));
  }

  @Override
  public boolean isZeroPower() {
    throw new RuntimeException("You cannot call isZeroPower for Const");
  }

  @Override
  public Form toForm() {
    return value;
  }

  @Override
  public String toString() {
    return "C{" + value.asStr() + "}";
  }
}
