package pompei.maths.syms.visitable;

import pompei.maths.syms.top.Visitor;

public class ConstDouble extends AbstractConst {
  public final double value;

  private ConstDouble(double value) {
    this.value = value;
  }

  @Override
  public <T> T visit(Visitor<T> visitor) {
    return visitor.visitConstDouble(this);
  }

  @Override
  public ConstDouble negate() {
    return get(-value);
  }

  public static ConstDouble get(double value) {
    return new ConstDouble(value);
  }

  @Override
  public boolean isMinisOne() {
    return value == -1.0;
  }

  @Override
  public boolean isOne() {
    return value == 1.0;
  }

  @Override
  public boolean isZero() {
    return value == 0.0;
  }

}
