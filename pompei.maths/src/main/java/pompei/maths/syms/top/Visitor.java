package pompei.maths.syms.top;

import pompei.maths.syms.visitable.*;

public interface Visitor<T> {
  T visitConstDouble(ConstDouble constDoubleExpr);

  T visitConstInt(ConstInt constIntExpr);

  T visitVar(Var varExpr);

  T visitPlus(Plus plus);

  T visitMul(Mul mul);

  T visitMinus(Minus minus);

  T visitDiv(Div div);

  T visitIntPower(IntPower intPower);

  T visitSkob(Skob skob);

  T visitMinis(Minis minis);
}
