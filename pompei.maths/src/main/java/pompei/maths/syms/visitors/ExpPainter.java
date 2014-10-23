package pompei.maths.syms.visitors;

import pompei.maths.syms.top.Visitor;
import pompei.maths.syms.visitable.ConstDoubleExpr;
import pompei.maths.syms.visitable.ConstIntExpr;
import pompei.maths.syms.visitable.Div;
import pompei.maths.syms.visitable.IntPower;
import pompei.maths.syms.visitable.Minus;
import pompei.maths.syms.visitable.Mul;
import pompei.maths.syms.visitable.Plus;
import pompei.maths.syms.visitable.VarExpr;

public class ExpPainter implements Visitor<Void> {
  
  private ExpSizer sizer;
  public int x, y;
  
  public ExpPainter(ExpSizer sizer, int x, int y) {
    this.sizer = sizer;
    this.x = x;
    this.y = y;
  }
  
  @Override
  public Void visitConstDouble(ConstDoubleExpr constDoubleExpr) {
    sizer.g().drawString("" + constDoubleExpr.value, x, y);
    return null;
  }
  
  @Override
  public Void visitConstIntExpr(ConstIntExpr constIntExpr) {
    sizer.g().drawString("" + constIntExpr.value, x, y);
    return null;
  }
  
  @Override
  public Void visitVarExpr(VarExpr varExpr) {
    sizer.g().drawString(varExpr.name, x, y);
    return null;
  }
  
  @Override
  public Void visitPlus(Plus plus) {
    // TODO Auto-generated method stub
    return null;
  }
  
  @Override
  public Void visitMul(Mul mul) {
    // TODO Auto-generated method stub
    return null;
  }
  
  @Override
  public Void visitMinus(Minus minus) {
    // TODO Auto-generated method stub
    return null;
  }
  
  @Override
  public Void visitDiv(Div div) {
    // TODO Auto-generated method stub
    return null;
  }
  
  @Override
  public Void visitIntPower(IntPower intPower) {
    // TODO Auto-generated method stub
    return null;
  }
  
}
