package pompei.maths.syms_diff.visitable;

import java.math.BigInteger;

import pompei.maths.syms.exceptions.DivByZero;
import pompei.maths.syms_diff.model.Const;
import pompei.maths.syms_diff.model.FormVisitor;

public class ConstInt implements Const {
  
  public final BigInteger top;
  public final BigInteger bottom;
  
  public static final ConstInt ZERO = new ConstInt(0);
  
  public static final ConstInt ONE = new ConstInt(1);
  public static final ConstInt M_ONE = new ConstInt(-1);
  public static final ConstInt TEN = new ConstInt(10);
  
  private static final BigInteger BI_M_ONE = BigInteger.ONE.negate();
  
  @Override
  public <T> T visit(FormVisitor<T> v) {
    return v.visitConst(this);
  }
  
  @Override
  public int sign() {
    return top.signum() * bottom.signum();
  }
  
  private ConstInt(BigInteger top, BigInteger bottom) {
    if (top == null) throw new NullPointerException("top == null");
    if (bottom == null) throw new NullPointerException("bottom == null");
    this.top = top;
    this.bottom = bottom;
  }
  
  private ConstInt(long n) {
    top = BigInteger.valueOf(n);
    bottom = BigInteger.ONE;
  }
  
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + bottom.hashCode();
    result = prime * result + top.hashCode();
    return result;
  }
  
  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null) return false;
    if (getClass() != obj.getClass()) return false;
    ConstInt other = (ConstInt)obj;
    return top.equals(other.top) && bottom.equals(other.bottom);
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
  
  public static ConstInt get(long value) {
    return get(value, 1);
  }
  
  public static ConstInt get(long top, long bottom) {
    return get(BigInteger.valueOf(top), BigInteger.valueOf(bottom));
  }
  
  public static ConstInt get(BigInteger value) {
    return get(value, BigInteger.ONE);
  }
  
  public double doubleValue() {
    return top.doubleValue() / bottom.doubleValue();
  }
  
  @Override
  public String asStr() {
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
    return asStr();
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
  
}
