package pompei.maths.syms_diff.visitable;

import pompei.maths.syms_diff.model.Const;
import pompei.maths.syms_diff.model.FormVisitor;

public class ConstDouble implements Const {
  
  public final double value;
  
  @Override
  public <T> T visit(FormVisitor<T> v) {
    return v.visitConst(this);
  }
  
  public ConstDouble(double value) {
    this.value = value;
  }
  
  @Override
  public int sign() {
    if (value == 0) return 0;
    return value < 0 ? -1 : 1;
  }
  
  @Override
  public String asStr() {
    return "" + value;
  }
}
