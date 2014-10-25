package pompei.maths.syms.visitable;

import pompei.maths.syms.top.Const;

public abstract class AbstractConst implements Const {
  
  @Override
  public Const sum(Const other) {
    if (this instanceof ConstInt && other instanceof ConstInt) {
      ConstInt a = (ConstInt)this;
      ConstInt b = (ConstInt)other;
      return ConstInt.get(a.value + b.value);
    }
    
    return ConstDouble.get(d(this) + d(other));
  }
  
  private static double d(Const x) {
    if (x instanceof ConstInt) return ((ConstInt)x).value;
    return ((ConstDouble)x).value;
  }
  
  @Override
  public Const sub(Const other) {
    if (this instanceof ConstInt && other instanceof ConstInt) {
      ConstInt a = (ConstInt)this;
      ConstInt b = (ConstInt)other;
      return ConstInt.get(a.value - b.value);
    }
    
    return ConstDouble.get(d(this) - d(other));
  }
  
  @Override
  public Const mul(Const other) {
    if (this instanceof ConstInt && other instanceof ConstInt) {
      ConstInt a = (ConstInt)this;
      ConstInt b = (ConstInt)other;
      return ConstInt.get(a.value * b.value);
    }
    
    return ConstDouble.get(d(this) * d(other));
  }
  
  @Override
  public Const div(Const other) {
    return ConstDouble.get(d(this) / d(other));
  }
  
  @Override
  public boolean isConst() {
    return true;
  }
}
