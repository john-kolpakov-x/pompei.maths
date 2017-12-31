package pompei.maths.syms.visitors;

import pompei.maths.syms.top.VarSource;
import pompei.maths.syms.top.Visitor;
import pompei.maths.syms.visitable.*;

@SuppressWarnings("unused")
public class ExpEvaluator implements Visitor<Double> {
  public final VarSource varSource;

  public ExpEvaluator(VarSource varSource) {
    this.varSource = varSource;
  }

  @Override
  public Double visitConstDouble(ConstDouble constDoubleExpr) {
    return constDoubleExpr.value;
  }

  @Override
  public Double visitConstInt(ConstInt constIntExpr) {
    return constIntExpr.doubleValue();
  }

  @Override
  public Double visitVar(Var varExpr) {
    return varSource.getValue(varExpr.name);
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

  @Override
  public Double visitSkob(Skob skob) {
    return skob.target.visit(this);
  }

  @Override
  public Double visitMinis(Minis minis) {
    return -minis.target.visit(this);
  }
}
