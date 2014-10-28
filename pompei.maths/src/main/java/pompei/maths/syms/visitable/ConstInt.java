package pompei.maths.syms.visitable;

import java.math.BigInteger;

import pompei.maths.syms.exceptions.DivByZero;
import pompei.maths.syms.top.Const;
import pompei.maths.syms.top.Visitor;

public class ConstInt extends AbstractConst {
  public final BigInteger top, bottom;
  
  public static final ConstInt ZERO = new ConstInt(0);
  
  public static final ConstInt ONE = new ConstInt(1);
  public static final ConstInt M_ONE = new ConstInt(-1);
  public static final ConstInt TEN = new ConstInt(10);
  
  private ConstInt(long value) {
    this(value, 1);
  }
  
  private ConstInt(long top, long bottom) {
    this(BigInteger.valueOf(top), BigInteger.valueOf(bottom));
  }
  
  private ConstInt(String top, String bottom) {
    this(new BigInteger(top), new BigInteger(bottom));
  }
  
  private ConstInt(BigInteger top, BigInteger bottom) {
    this.top = top;
    this.bottom = bottom;
  }
  
  @Override
  public <T> T visit(Visitor<T> visitor) {
    return visitor.visitConstIntExpr(this);
  }
  
  @Override
  public Const negate() {
    return get(top.negate(), bottom);
  }
  
  public static ConstInt get(long value) {
    return get(value, 1);
  }
  
  public static ConstInt get(long top, long bottom) {
    return get(new BigInteger("" + top), new BigInteger("" + bottom));
  }
  
  public static ConstInt get(BigInteger value) {
    return get(value, BigInteger.ONE);
  }
  
  public static ConstInt get(BigInteger top, BigInteger bottom) {
    
    {
      if (bottom.compareTo(BigInteger.ZERO) == 0) throw new DivByZero();
      if (bottom.compareTo(BigInteger.ZERO) < 0) {
        top = top.negate();
        bottom = bottom.negate();
      }
      BigInteger gcd = top.abs().gcd(bottom);
      top = top.divide(gcd);
      bottom = bottom.divide(gcd);
    }
    
    if (bottom.compareTo(BigInteger.ONE) == 0) {
      
      if (top.compareTo(BigInteger.ZERO) == 0) return ZERO;
      if (top.compareTo(BigInteger.ONE) == 0) return ONE;
      if (top.compareTo(BigInteger.TEN) == 0) return TEN;
      if (top.compareTo(BI_M_ONE) == 0) return M_ONE;
      
    }
    
    return new ConstInt(top, bottom);
  }
  
  private static final BigInteger BI_M_ONE = BigInteger.ONE.negate();
  
  public double doubleValue() {
    return top.doubleValue() / bottom.doubleValue();
  }
  
  public ConstInt innerSum(ConstInt other) {
    BigInteger topValue = top.multiply(other.bottom).add(bottom.multiply(other.top));
    BigInteger bottomValue = bottom.multiply(other.bottom);
    
    return get(topValue, bottomValue);
  }
  
  public ConstInt innerSub(ConstInt other) {
    BigInteger topValue = top.multiply(other.bottom).subtract(bottom.multiply(other.top));
    BigInteger bottomValue = bottom.multiply(other.bottom);
    
    return get(topValue, bottomValue);
  }
  
  public ConstInt innerMul(ConstInt other) {
    return get(top.multiply(other.top), bottom.multiply(other.bottom));
  }
  
  public ConstInt innerDiv(ConstInt other) {
    return get(top.multiply(other.bottom), bottom.multiply(other.top));
  }
  
  public ConstInt innerInvert() {
    return get(bottom, top);
  }
  
  public String displayStr() {
    if (bottom.compareTo(BigInteger.ONE) == 0) return top.toString();
    return top + "/" + bottom;
  }
  
  public boolean isOne() {
    return top.compareTo(BigInteger.ONE) == 0 && bottom.compareTo(BigInteger.ONE) == 0;
  }
  
  public boolean isMinisOne() {
    return top.compareTo(BI_M_ONE) == 0 && bottom.compareTo(BigInteger.ONE) == 0;
  }
  
  public boolean isZero() {
    return top.compareTo(BigInteger.ZERO) == 0;
  }
  
  @Override
  public String toString() {
    return displayStr();
  }
}
