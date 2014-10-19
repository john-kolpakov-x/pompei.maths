package pompei.maths.syms.visitors;

import pompei.maths.syms.top.VarSource;
import pompei.maths.syms.top.Visitor;
import pompei.maths.syms.visitable.ConstDoubleExpr;
import pompei.maths.syms.visitable.ConstIntExpr;
import pompei.maths.syms.visitable.Div;
import pompei.maths.syms.visitable.IntPower;
import pompei.maths.syms.visitable.Minus;
import pompei.maths.syms.visitable.Mul;
import pompei.maths.syms.visitable.Plus;
import pompei.maths.syms.visitable.VarExpr;

public class ExpEvaluator implements Visitor<Double> {
  public final VarSource varSource;
  
  public ExpEvaluator(VarSource varSource) {
    this.varSource = varSource;
  }
  
  @Override
  public Double visitConstDouble(ConstDoubleExpr constDoubleExpr) {
    return constDoubleExpr.value;
  }
  
  @Override
  public Double visitConstIntExpr(ConstIntExpr constIntExpr) {
    return (double)constIntExpr.value;
  }
  
  @Override
  public Double visitVarExpr(VarExpr varExpr) {
    return varSource.getValue(varExpr.varName);
  }
  
  @Override
  public Double visitPlus(Plus plus) {
    return plus.left.visit(this) + plus.right.visit(this);
  }
  
  @Override
  public Double visitMul(Mul mul) {
    return mul.left.visit(this) * mul.right.visit(this);
  }
  
  @Override
  public Double visitMinus(Minus minus) {
    return minus.left.visit(this) - minus.right.visit(this);
  }
  
  @Override
  public Double visitDiv(Div div) {
    return div.top.visit(this) / div.bottom.visit(this);
  }
  
  @Override
  public Double visitIntPower(IntPower intPower) {
    int pow = intPower.pow;
    if (pow == 0) return 1.0;
    
    double value = intPower.exp.visit(this);
    double ret = 1;
    
    if (pow < 0) {
      while (pow++ < 0) {
        ret /= value;
      }
      return ret;
    }
    
    {
      while (pow-- > 0) {
        ret *= value;
      }
      return ret;
    }
  }
}
