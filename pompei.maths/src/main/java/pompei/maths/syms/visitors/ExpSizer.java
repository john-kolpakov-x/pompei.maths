package pompei.maths.syms.visitors;

import java.awt.Graphics2D;

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
  public PaintSize visitConstDouble(ConstDouble constDoubleExpr) {
    return strSize("" + constDoubleExpr.value);
  }
  
  @Override
  public PaintSize visitConstInt(ConstInt constIntExpr) {
    return strSize(constIntExpr.displayStr());
  }
  
  @Override
  public PaintSize visitVar(Var varExpr) {
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
    ret.expandOnRight(strSize("−"));
    ret.expandOnRight(minus.right.visit(this));
    
    return ret;
  }
  
  @Override
  public PaintSize visitMinis(Minis minis) {
    
    PaintSize ret = new PaintSize();
    
    ret.expandOnRight(strSize("-"));
    ret.expandOnRight(minis.target.visit(this));
    
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
    
    int w = (top.w > bottom.w ? top.w :bottom.w) //
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
    
    int h1_ = powSize.h1 + upMove;
    int h2_ = powSize.h2 - upMove;
    
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
    int w = (int)(size.h() * gs.skob().ySizeWidthFactor(level) + 0.5);
    int minWidth = gs.skob().minWidth(level);
    if (w < minWidth) w = minWidth;
    
    return new PaintSize(size.w + 2 * w, size.h1, size.h2);
  }
  
}
