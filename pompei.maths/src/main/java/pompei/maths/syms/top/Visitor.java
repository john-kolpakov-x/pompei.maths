package pompei.maths.syms.top;

import pompei.maths.syms.visitable.ConstDouble;
import pompei.maths.syms.visitable.ConstInt;
import pompei.maths.syms.visitable.Div;
import pompei.maths.syms.visitable.IntPower;
import pompei.maths.syms.visitable.Minus;
import pompei.maths.syms.visitable.Mul;
import pompei.maths.syms.visitable.Plus;
import pompei.maths.syms.visitable.Skob;
import pompei.maths.syms.visitable.Var;

public interface Visitor<T> {
  T visitConstDouble(ConstDouble constDoubleExpr);
  
  T visitConstIntExpr(ConstInt constIntExpr);
  
  T visitVarExpr(Var varExpr);
  
  T visitPlus(Plus plus);
  
  T visitMul(Mul mul);
  
  T visitMinus(Minus minus);
  
  T visitDiv(Div div);
  
  T visitIntPower(IntPower intPower);
  
  T visitSkob(Skob skob);
}
