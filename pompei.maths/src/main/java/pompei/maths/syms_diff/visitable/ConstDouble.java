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
    return value == 0 ? 0 : (value > 0 ? 1 : -1);
  }
  
  @Override
  public String asStr() {
    return "" + value;
  }
  
  @Override
  public double doubleValue() {
    return value;
  }
  
  @Override
  public int hashCode() {
    long tmp = Double.doubleToLongBits(value);
    return 31 + (int)(tmp ^ (tmp >>> 32));
  }
  
  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null) return false;
    if (getClass() != obj.getClass()) return false;
    ConstDouble other = (ConstDouble)obj;
    return value == other.value;
  }
  
  @Override
  public Const minis() {
    return new ConstDouble(-value);
  }
  
  @Override
  public boolean isOne() {
    return value == 1.0;
  }
}
