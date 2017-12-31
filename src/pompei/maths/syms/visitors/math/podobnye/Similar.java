package pompei.maths.syms.visitors.math.podobnye;

import pompei.maths.syms.top.Expr;
import pompei.maths.syms.visitable.IntPower;
import pompei.maths.syms.visitable.Mul;
import pompei.maths.syms.visitable.Plus;
import pompei.maths.syms.visitors.Scanner;

public class Similar extends Scanner {
  
  public final boolean withDiv;
  
  public Similar(boolean withDiv) {
    this.withDiv = withDiv;
  }
  
  @Override
  public Expr visitMul(Mul mul) {
    if (mul.isConst()) return mul;
    
    CollectMulArg vars = new CollectMulArg();
    mul.visit(vars);
    if (vars.skip) return super.visitMul(mul);
    
    return vars.createExpr(withDiv);
  }
  
  @Override
  public Expr visitIntPower(IntPower intPower) {
    if (intPower.isConst()) return intPower;
    
    CollectMulArg cma = new CollectMulArg();
    intPower.visit(cma);
    if (cma.skip) return super.visitIntPower(intPower);
    
    return cma.createExpr(withDiv);
  }
  
  @Override
  public Expr visitPlus(Plus plus) {
    if (plus.isConst()) return plus;
    
    CollectPlusArgs cpa = new CollectPlusArgs();
    plus.visit(cpa);
    
    if (cpa.skip) return super.visitPlus(plus);
    
    return cpa.createExpr(withDiv);
  }
  
}
