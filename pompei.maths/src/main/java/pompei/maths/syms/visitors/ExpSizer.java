package pompei.maths.syms.visitors;

import java.awt.Graphics2D;

import pompei.maths.syms.top.Visitor;
import pompei.maths.syms.visitable.ConstDoubleExpr;
import pompei.maths.syms.visitable.ConstIntExpr;
import pompei.maths.syms.visitable.Div;
import pompei.maths.syms.visitable.IntPower;
import pompei.maths.syms.visitable.Minus;
import pompei.maths.syms.visitable.Mul;
import pompei.maths.syms.visitable.Plus;
import pompei.maths.syms.visitable.Skob;
import pompei.maths.syms.visitable.VarExpr;

public class ExpSizer implements Visitor<PaintSize> {
  public final GraphicsSource gs;
  
  public ExpSizer(GraphicsSource gs) {
    this.gs = gs;
  }
  
  public int level = 1;
  
  public Graphics2D g() {
    return gs.getGraphics(level);
  }
  
  @Override
  public PaintSize visitConstDouble(ConstDoubleExpr constDoubleExpr) {
    return strSize("" + constDoubleExpr.value);
  }
  
  @Override
  public PaintSize visitConstIntExpr(ConstIntExpr constIntExpr) {
    return strSize("" + constIntExpr.value);
  }
  
  @Override
  public PaintSize visitVarExpr(VarExpr varExpr) {
    return strSize("" + varExpr.name);
  }
  
  @Override
  public PaintSize visitPlus(Plus plus) {
    PaintSize ret = new PaintSize(0, 0, 0);
    
    ret.expandOnRight(plus.left.visit(this));
    ret.expandOnRight(strSize("+"));
    ret.expandOnRight(plus.right.visit(this));
    
    return ret;
  }
  
  @Override
  public PaintSize visitMul(Mul mul) {
    PaintSize ret = new PaintSize();
    
    ret.expandOnRight(mul.left.visit(this));
    ret.expandOnRight(strSize("·"));
    ret.expandOnRight(mul.right.visit(this));
    
    return ret;
  }
  
  @Override
  public PaintSize visitMinus(Minus minus) {
    PaintSize ret = new PaintSize();
    
    ret.expandOnRight(minus.left.visit(this));
    ret.expandOnRight(strSize("-"));
    ret.expandOnRight(minus.right.visit(this));
    
    return ret;
  }
  
  @Override
  public PaintSize visitDiv(Div div) {
    PaintSize top = div.top.visit(this);
    PaintSize bottom = div.bottom.visit(this);
    Graphics2D g = gs.getGraphics(level);
    int ascent = g.getFontMetrics().getAscent();
    
    int upDist = (int)(ascent * gs.ascendingMiddleProportion(level) + 0.5);
    
    ConfDiv dc = gs.div();
    
    int h1 = dc.paddingUp(level) + top.h1 + top.h2 + upDist;
    int h2 = dc.paddingDown(level) + bottom.h1 + bottom.h2 - upDist;
    
    int w = (top.w > bottom.w ? top.w : bottom.w) //
        + dc.paddingLeft(level) + dc.paddingRight(level);
    
    return new PaintSize(w, h1, h2);
  }
  
  @Override
  public PaintSize visitIntPower(IntPower intPower) {
    PaintSize expSize = intPower.exp.visit(this);
    level++;
    PaintSize powSize = strSize("" + intPower.pow);
    level--;
    
    int powExpDistance = gs.power().powExpDistance(level);
    double upPercent = gs.power().upPercent(level);
    
    int h1 = expSize.h1;
    int h2 = expSize.h2;
    
    int upMove = (int)(h1 * upPercent);
    
    int h1_ = expSize.h1 + upMove;
    int h2_ = expSize.h2 - upMove;
    
    if (h1 < h1_) h1 = h1_;
    if (h2 < h2_) h2 = h2_;
    
    int w = expSize.w + powExpDistance + powSize.w;
    
    return new PaintSize(w, h1, h2);
  }
  
  public PaintSize strSize(String str) {
    Graphics2D g = gs.getGraphics(level);
    int w = g.getFontMetrics().stringWidth(str);
    int h1 = g.getFontMetrics().getAscent();
    int h2 = g.getFontMetrics().getDescent();
    return new PaintSize(w, h1, h2);
  }
  
  @Override
  public PaintSize visitSkob(Skob skob) {
    
    PaintSize size = skob.target.visit(this);
    size.w = (int)(size.w * gs.skob().xSizeFactor(level) + 0.5);
    
    return size;
  }
  
}
