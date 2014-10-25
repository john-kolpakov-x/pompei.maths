package pompei.maths.syms.visitable;

import pompei.maths.syms.top.Const;
import pompei.maths.syms.top.Visitor;

public class ConstInt extends AbstractConst {
  public final long value;Надо этот класс сделать не целым, а рациональным
  
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
    this.value = value;
  }
  
  @Override
  public <T> T visit(Visitor<T> visitor) {
    return visitor.visitConstIntExpr(this);
  }
  
  @Override
  public Const negate() {
    return get(-value);
  }
  
  public static ConstInt get(long value) {
    if (value == 0) return ZERO;
    
    if (value == 1) return ONE;
    if (value == 2) return TWO;
    if (value == 3) return THREE;
    if (value == 4) return FOUR;
    if (value == 5) return FIVE;
    
    if (value == -1) return MONE;
    if (value == -2) return MTWO;
    if (value == -3) return MTHREE;
    if (value == -4) return MFOUR;
    if (value == -5) return MFIVE;
    
    return new ConstInt(value);
  }
  
}
