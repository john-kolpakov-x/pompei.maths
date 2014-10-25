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
}
