package pompei.maths.syms.visitors.math.podobnye;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

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

class CollectPlusArgs implements Visitor<Void> {
  
  private static final VarMul EMPTY = new VarMul();
  
  final Map<VarMul, List<ConstCollect>> map = new HashMap<>();
  boolean skip = false;
  
  public Expr createExpr(boolean withDiv) {
    Expr ret = null;
    
    for (Entry<VarMul, List<ConstCollect>> e : map.entrySet()) {
      if (ret == null) {
        ret = createExprFor(e, withDiv);
      } else {
        ret = new Plus(createExprFor(e, withDiv), ret);
      }
    }
    
    return ret;
  }
  
  private Expr createExprFor(Entry<VarMul, List<ConstCollect>> e, boolean withDiv) {
    Expr varMulExpr = e.getKey().createExpr(withDiv);
    List<ConstCollect> constCollectList = e.getValue();
    
    int constCollectSize = constCollectList.size();
    
    if (constCollectSize == 0) return varMulExpr;
    
    ConstCollect firstConstCollect = constCollectList.get(0);
    
    if (constCollectSize == 1) return firstConstCollect.mulTo(varMulExpr);
    
    Expr constExpr = firstConstCollect.createExpr();
    
    for (int i = 1; i < constCollectSize; i++) {
      constExpr = new Plus(constExpr, constCollectList.get(i).createExpr());
    }
    
    return new Mul(constExpr, varMulExpr);
  }
  
  private void addConst(VarMul varMul, Expr aConst) {
    ConstCollect cc = new ConstCollect();
    cc.addConst(aConst);
    addConstCollect(varMul, cc);
  }
  
  private void addConstCollect(VarMul varMul, ConstCollect cc) {
    List<ConstCollect> list = map.get(varMul);
    if (list == null) {
      list = new ArrayList<>();
      map.put(varMul, list);
    }
    
    list.add(cc);
  }
  
  @Override
  public Void visitConstDouble(ConstDouble constDoubleExpr) {
    if (skip) return null;
    
    addConst(EMPTY, constDoubleExpr);
    
    return null;
  }
  
  @Override
  public Void visitConstInt(ConstInt constIntExpr) {
    if (skip) return null;
    
    addConst(EMPTY, constIntExpr);
    return null;
  }
  
  @Override
  public Void visitVar(Var varExpr) {
    if (skip) return null;
    
    addConst(VarMul.alone(varExpr.name), ConstInt.ONE);
    
    return null;
  }
  
  @Override
  public Void visitPlus(Plus plus) {
    if (skip) return null;
    if (plus.isConst()) {
      addConst(EMPTY, plus);
      return null;
    }
    
    plus.left.visit(this);
    
    if (skip) return null;
    
    plus.right.visit(this);
    
    return null;
  }
  
  @Override
  public Void visitMul(Mul mul) {
    if (skip) return null;
    if (mul.isConst()) {
      addConst(EMPTY, mul);
      return null;
    }
    
    collectMul(mul);
    
    return null;
  }
  
  private void collectMul(Expr expr) {
    CollectMulArg cma = new CollectMulArg();
    expr.visit(cma);
    
    if (cma.skip) {
      skip = true;
      return;
    }
    
    addConstCollect(cma.varMul, cma.constCollect);
  }
  
  @Override
  public Void visitMinus(Minus minus) {
    if (skip) return null;
    if (minus.isConst()) {
      addConst(EMPTY, minus);
      return null;
    }
    
    skip = true;
    
    return null;
  }
  
  @Override
  public Void visitDiv(Div div) {
    if (skip) return null;
    if (div.isConst()) {
      addConst(EMPTY, div);
      return null;
    }
    
    collectMul(div);
    
    return null;
  }
  
  @Override
  public Void visitIntPower(IntPower intPower) {
    if (skip) return null;
    if (intPower.isConst()) {
      addConst(EMPTY, intPower);
      return null;
    }
    
    collectMul(intPower);
    
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
    if (minis.isConst()) {
      addConst(EMPTY, minis);
      return null;
    }
    
    collectMul(minis);
    
    return null;
  }
  
}
