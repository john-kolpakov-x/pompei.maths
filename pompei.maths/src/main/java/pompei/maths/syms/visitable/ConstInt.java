package pompei.maths.syms.visitable;

import pompei.maths.syms.exceptions.DivByZero;
import pompei.maths.syms.top.Const;
import pompei.maths.syms.top.Visitor;
import pompei.maths.syms.visitors.math.UtilMath;

public class ConstInt extends AbstractConst {
  public final long top, bottom;
  
  public static final ConstInt ZERO = new ConstInt(0);
  
  public static final ConstInt ONE = new ConstInt(1);
  public static final ConstInt TWO = new ConstInt(2);
  public static final ConstInt THREE = new ConstInt(3);
  public static final ConstInt FOUR = new ConstInt(4);
  public static final ConstInt FIVE = new ConstInt(5);
  
  public static final ConstInt MONE = new ConstInt(-1);
  public static final ConstInt MTWO = new ConstInt(-2);
  public static final ConstInt MTHREE = new ConstInt(-3);
  public static final ConstInt MFOUR = new ConstInt(-4);
  public static final ConstInt MFIVE = new ConstInt(-5);
  
  private ConstInt(long value) {
    this(value, 1);
  }
  
  private ConstInt(long top, long bottom) {
    this.top = top;
    this.bottom = bottom;
  }
  
  @Override
  public <T> T visit(Visitor<T> visitor) {
    return visitor.visitConstIntExpr(this);
  }
  
  @Override
  public Const negate() {
    return get(-top, bottom);
  }
  
  public static ConstInt get(long value) {
    return get(value, 1);
  }
  
  public static ConstInt get(long top, long bottom) {
    
    {
      if (bottom == 0) throw new DivByZero();
      if (bottom < 0) {
        top = -top;
        bottom = -bottom;
      }
      long gcd = UtilMath.gcd(top, bottom);
      top /= gcd;
      bottom /= gcd;
    }
    
    if (bottom == 1) {
      
      if (top == 0) return ZERO;
      
      if (top == 1) return ONE;
      if (top == 2) return TWO;
      if (top == 3) return THREE;
      if (top == 4) return FOUR;
      if (top == 5) return FIVE;
      
      if (top == -1) return MONE;
      if (top == -2) return MTWO;
      if (top == -3) return MTHREE;
      if (top == -4) return MFOUR;
      if (top == -5) return MFIVE;
      
    }
    
    return new ConstInt(top, bottom);
  }
  
  public double doubleValue() {
    return (double)top / (double)bottom;
  }
  
  public ConstInt innerSum(ConstInt other) {
    return get(top * other.bottom + bottom * other.top, bottom * other.bottom);
  }
  
  public ConstInt innerSub(ConstInt other) {
    return get(top * other.bottom - bottom * other.top, bottom * other.bottom);
  }
  
  public ConstInt innerMul(ConstInt other) {
    return get(top * other.top, bottom * other.bottom);
  }
  
  public ConstInt innerDiv(ConstInt other) {
    return get(top * other.bottom, bottom * other.top);
  }
  
  public ConstInt innerInvert() {
    return get(bottom, top);
  }
  
  public String displayStr() {
    if (bottom == 1) return "" + top;
    return top + "/" + bottom;
  }
}
