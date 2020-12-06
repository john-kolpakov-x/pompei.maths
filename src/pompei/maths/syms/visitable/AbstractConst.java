package pompei.maths.syms.visitable;

import pompei.maths.syms.top.Const;

public abstract class AbstractConst implements Const {

  @Override
  public Const sum(Const other) {
    if (this instanceof ConstInt && other instanceof ConstInt) {
      ConstInt a = (ConstInt) this;
      ConstInt b = (ConstInt) other;
      return a.innerSum(b);
    }

    return ConstDouble.get(d(this) + d(other));
  }

  private static double d(Const x) {
    if (x instanceof ConstInt) {
      return ((ConstInt) x).doubleValue();
    }
    if (x instanceof ConstDouble) {
      return ((ConstDouble) x).value;
    }
    throw new IllegalArgumentException("Unknown " + x.getClass());
  }

  @Override
  public Const sub(Const other) {
    if (this instanceof ConstInt && other instanceof ConstInt) {
      ConstInt a = (ConstInt) this;
      ConstInt b = (ConstInt) other;
      return a.innerSub(b);
    }

    return ConstDouble.get(d(this) - d(other));
  }

  @Override
  public Const mul(Const other) {
    if (this instanceof ConstInt && other instanceof ConstInt) {
      ConstInt a = (ConstInt) this;
      ConstInt b = (ConstInt) other;
      return a.innerMul(b);
    }

    return ConstDouble.get(d(this) * d(other));
  }

  @Override
  public Const div(Const other) {
    if (this instanceof ConstInt && other instanceof ConstInt) {
      ConstInt a = (ConstInt) this;
      ConstInt b = (ConstInt) other;
      return a.innerDiv(b);
    }

    return ConstDouble.get(d(this) * d(other));
  }

  @Override
  public boolean isConst() {
    return true;
  }
}
