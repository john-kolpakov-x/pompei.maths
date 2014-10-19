package pompei.maths.syms.top;

import pompei.maths.syms.visitable.ConstDoubleExpr;
import pompei.maths.syms.visitable.ConstIntExpr;
import pompei.maths.syms.visitable.Div;
import pompei.maths.syms.visitable.IntPower;
import pompei.maths.syms.visitable.Minus;
import pompei.maths.syms.visitable.Mul;
import pompei.maths.syms.visitable.Plus;
import pompei.maths.syms.visitable.VarExpr;

public interface Visitor<T> {
  T visitConstDouble(ConstDoubleExpr constDoubleExpr);
  
  T visitConstIntExpr(ConstIntExpr constIntExpr);
  
  T visitVarExpr(VarExpr varExpr);
  
  T visitPlus(Plus plus);
  
  T visitMul(Mul mul);
  
  T visitMinus(Minus minus);
  
  T visitDiv(Div div);
  
  T visitIntPower(IntPower intPower);
}
