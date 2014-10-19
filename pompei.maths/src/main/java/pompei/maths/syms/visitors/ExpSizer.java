package pompei.maths.syms.visitors;

import java.awt.Graphics2D;
import java.util.HashMap;
import java.util.Map;

import pompei.maths.syms.top.Expr;
import pompei.maths.syms.top.Visitor;
import pompei.maths.syms.visitable.ConstDoubleExpr;
import pompei.maths.syms.visitable.ConstIntExpr;
import pompei.maths.syms.visitable.Div;
import pompei.maths.syms.visitable.IntPower;
import pompei.maths.syms.visitable.Minus;
import pompei.maths.syms.visitable.Mul;
import pompei.maths.syms.visitable.Plus;
import pompei.maths.syms.visitable.VarExpr;

public class ExpSizer implements Visitor<PaintSize> {
  private GraphicsSource gs;
  
  public ExpSizer(GraphicsSource gs) {
    this.gs = gs;
  }
  
  public int level = 1;
  
  private String id(Expr expr) {
    return System.identityHashCode(expr) + "-" + level;
  }
  
  private Map<String, PaintSize> sizes = new HashMap<>();
  
  public void assignCacheFrom(ExpSizer other) {
    sizes = other.sizes;
  }
  
  @Override
  public PaintSize visitConstDouble(ConstDoubleExpr constDoubleExpr) {
    String id = id(constDoubleExpr);
    {
      PaintSize ret = sizes.get(id);
      if (ret != null) return ret;
    }
    {
      PaintSize ret = strSize("" + constDoubleExpr.value);
      sizes.put(id, ret);
      return ret;
    }
  }
  
  @Override
  public PaintSize visitConstIntExpr(ConstIntExpr constIntExpr) {
    String id = id(constIntExpr);
    {
      PaintSize ret = sizes.get(id);
      if (ret != null) return ret;
    }
    {
      PaintSize ret = strSize("" + constIntExpr.value);
      sizes.put(id, ret);
      return ret;
    }
  }
  
  @Override
  public PaintSize visitVarExpr(VarExpr varExpr) {
    String id = id(varExpr);
    {
      PaintSize ret = sizes.get(id);
      if (ret != null) return ret;
    }
    {
      PaintSize ret = strSize("" + varExpr.varName);
      sizes.put(id, ret);
      return ret;
    }
  }
  
  @Override
  public PaintSize visitPlus(Plus plus) {
    String id = id(plus);
    {
      PaintSize ret = sizes.get(id);
      if (ret != null) return ret;
    }
    {
      PaintSize ret = new PaintSize(0, 0, 0);
      
      ret.expandOnRight(plus.left.visit(this));
      ret.expandOnRight(strSize("+"));
      ret.expandOnRight(plus.right.visit(this));
      
      sizes.put(id, ret);
      return ret;
    }
  }
  
  @Override
  public PaintSize visitMul(Mul mul) {
    String id = id(mul);
    {
      PaintSize ret = sizes.get(id);
      if (ret != null) return ret;
    }
    {
      PaintSize ret = new PaintSize();
      
      ret.expandOnRight(mul.left.visit(this));
      ret.expandOnRight(strSize("·"));
      ret.expandOnRight(mul.right.visit(this));
      
      sizes.put(id, ret);
      return ret;
    }
  }
  
  @Override
  public PaintSize visitMinus(Minus minus) {
    String id = id(minus);
    {
      PaintSize ret = sizes.get(id);
      if (ret != null) return ret;
    }
    {
      PaintSize ret = new PaintSize();
      
      ret.expandOnRight(minus.left.visit(this));
      ret.expandOnRight(strSize("-"));
      ret.expandOnRight(minus.right.visit(this));
      
      sizes.put(id, ret);
      return ret;
    }
  }
  
  @Override
  public PaintSize visitDiv(Div div) {
    PaintSize top = div.top.visit(this);
    PaintSize bottom = div.bottom.visit(this);
    Graphics2D g = gs.getGraphics(level);
    int ascent = g.getFontMetrics().getAscent();
    
    int upDist = (int)(ascent * gs.ascendingMiddleProportion(level) + 0.5);
    
    DivConf dc = gs.div();
    
    int h1 = dc.paddingUp(level) + top.h1 + top.h2 + upDist;
    int h2 = dc.paddingDown(level) + bottom.h1 + bottom.h2 - upDist;
    
    int w = (top.w > bottom.w ? top.w : bottom.w) //
        + dc.paddingLeft(level) + dc.paddingRight(level);
    
    return new PaintSize(w, h1, h2);
  }
  
  @Override
  public PaintSize visitIntPower(IntPower intPower) {
    // TODO Auto-generated method stub
    return null;
  }
  
  private PaintSize strSize(String str) {
    Graphics2D g = gs.getGraphics(level);
    int w = g.getFontMetrics().stringWidth(str);
    int h1 = g.getFontMetrics().getAscent();
    int h2 = g.getFontMetrics().getDescent();
    return new PaintSize(w, h1, h2);
  }
}
