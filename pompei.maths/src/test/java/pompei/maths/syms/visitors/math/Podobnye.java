package pompei.maths.syms.visitors.math;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

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
import pompei.maths.syms.visitors.Scanner;

public class Podobnye extends Scanner {
  private static class CollectVars implements Visitor<Void> {
    final SortedMap<String, Integer> vars = new TreeMap<String, Integer>();
    final List<Expr> consts = new ArrayList<Expr>();
    boolean ok = true;
    boolean minised = false;
    int factor = 1;
    
    void addConst(Expr expr) {
      if (factor == 0) return;
      if (factor == 1) {
        consts.add(expr);
        return;
      }
      consts.add(new IntPower(expr, factor));
    }
    
    void addVar(String name, int more) {
      Integer count = vars.get(name);
      if (count == null) count = 0;
      count += more * factor;
      vars.put(name, count);
    }
    
    void addVar(String name) {
      addVar(name, 1);
    }
    
    @Override
    public Void visitConstDouble(ConstDouble constDoubleExpr) {
      addConst(constDoubleExpr);
      return null;
    }
    
    @Override
    public Void visitConstIntExpr(ConstInt constIntExpr) {
      addConst(constIntExpr);
      return null;
    }
    
    @Override
    public Void visitVarExpr(Var varExpr) {
      addVar(varExpr.name);
      return null;
    }
    
    @Override
    public Void visitPlus(Plus plus) {
      if (plus.isConst()) addConst(plus);
      else ok = false;
      return null;
    }
    
    @Override
    public Void visitMul(Mul mul) {
      if (mul.isConst()) {
        addConst(mul);
        return null;
      }
      mul.left.visit(this);
      mul.right.visit(this);
      return null;
    }
    
    @Override
    public Void visitMinus(Minus minus) {
      if (minus.isConst()) addConst(minus);
      else ok = false;
      return null;
    }
    
    @Override
    public Void visitDiv(Div div) {
      if (div.isConst()) {
        addConst(div);
        return null;
      }
      
      new Mul(div.top, new IntPower(div.bottom, -1)).visit(this);
      
      return null;
    }
    
    @Override
    public Void visitIntPower(IntPower intPower) {
      if (intPower.isConst()) {
        addConst(intPower);
        return null;
      }
      
      if (intPower.pow == 0) return null;
      
      factor *= intPower.pow;
      intPower.exp.visit(this);
      factor /= intPower.pow;
      
      return null;
    }
    
    @Override
    public Void visitSkob(Skob skob) {
      skob.target.visit(this);
      return null;
    }
    
    @Override
    public Void visitMinis(Minis minis) {
      minis.target.visit(this);
      minised = !minised;
      return null;
    }
    
  }
  
  @Override
  public Expr visitMul(Mul mul) {
    if (mul.isConst()) return mul;
    
    CollectVars vars = new CollectVars();
    mul.visit(vars);
    if (!vars.ok) return super.visitMul(mul);
    
    // TODO Auto-generated method stub
    return super.visitMul(mul);
  }
}
