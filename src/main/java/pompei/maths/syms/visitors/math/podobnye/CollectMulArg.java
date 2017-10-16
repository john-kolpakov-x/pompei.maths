package pompei.maths.syms.visitors.math.podobnye;

import pompei.maths.syms.top.Expr;
import pompei.maths.syms.top.Visitor;
import pompei.maths.syms.visitable.ConstDouble;
import pompei.maths.syms.visitable.ConstInt;
import pompei.maths.syms.visitable.Div;
import pompei.maths.syms.visitable.IntPower;
import pompei.maths.syms.visitable.Minis;
import pompei.maths.syms.visitable.Minus;
import pompei.maths.syms.visitable.Mul;
import pompei.maths.syms.visitable.Plus;
import pompei.maths.syms.visitable.Skob;
import pompei.maths.syms.visitable.Var;

class CollectMulArg implements Visitor<Void> {
  final VarMul varMul = new VarMul();
  final ConstCollect constCollect = new ConstCollect();
  boolean skip = false;
  int factor = 1;
  
  public Expr createExpr(boolean withDiv) {
    return constCollect.mulTo(varMul.createExpr(withDiv));
  }
  
  @Override
  public Void visitConstDouble(ConstDouble constDoubleExpr) {
    if (skip) return null;
    constCollect.addConst(constDoubleExpr, factor);
    return null;
  }
  
  @Override
  public Void visitConstInt(ConstInt constIntExpr) {
    if (skip) return null;
    constCollect.addConst(constIntExpr, factor);
    return null;
  }
  
  @Override
  public Void visitVar(Var varExpr) {
    if (skip) return null;
    varMul.addVar(varExpr.name, factor);
    return null;
  }
  
  @Override
  public Void visitPlus(Plus plus) {
    if (skip) return null;
    
    if (plus.isConst()) {
      constCollect.addConst(plus, factor);
      return null;
    }
    
    skip = true;
    
    return null;
  }
  
  @Override
  public Void visitMul(Mul mul) {
    if (skip) return null;
    
    if (mul.isConst()) {
      constCollect.addConst(mul, factor);
      return null;
    }
    
    mul.left.visit(this);
    mul.right.visit(this);
    
    return null;
  }
  
  @Override
  public Void visitMinus(Minus minus) {
    if (skip) return null;
    
    if (minus.isConst()) {
      constCollect.addConst(minus, factor);
      return null;
    }
    
    skip = true;
    
    return null;
  }
  
  @Override
  public Void visitDiv(Div div) {
    if (skip) return null;
    
    if (div.isConst()) {
      constCollect.addConst(div, factor);
      return null;
    }
    
    new Mul(div.top, new IntPower(div.bottom, -1)).visit(this);// a/b == a * b^(-1)
    
    return null;
  }
  
  @Override
  public Void visitIntPower(IntPower intPower) {
    if (skip) return null;
    
    if (intPower.isConst()) {
      constCollect.addConst(intPower, factor);
      return null;
    }
    
    if (intPower.pow == 0) return null;//a^0 == 1, а при умножении на 1 ничего не происходит
    
    factor *= intPower.pow;
    intPower.exp.visit(this);
    factor /= intPower.pow;
    
    return null;
  }
  
  @Override
  public Void visitSkob(Skob skob) {
    if (skip) return null;
    
    skob.target.visit(this);
    
    return null;
  }
  
  @Override
  public Void visitMinis(Minis minis) {
    if (skip) return null;
    
    constCollect.minis();
    minis.target.visit(this);
    
    return null;
  }
  
}