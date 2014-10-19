package pompei.maths.syms.visitors;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
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

public class ExpPainter implements Visitor<Void> {
  
  private BufferedImage image;
  
  public ExpPainter(BufferedImage image) {
    this.image = image;
    if (image == null) {
      image = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
    }
  }
  
  private Graphics2D getGraphics() {
    return image.createGraphics();
  }
  
  private static int id(Expr expr) {
    return System.identityHashCode(expr);
  }
  
  private final Map<Integer, PaintSize> sizes = new HashMap<>();
  
  public final Visitor<PaintSize> sizer = new Visitor<PaintSize>() {
    @Override
    public PaintSize visitConstDouble(ConstDoubleExpr constDoubleExpr) {
      int id = id(constDoubleExpr);
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
      int id = id(constIntExpr);
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
      int id = id(varExpr);
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
      PaintSize ret = new PaintSize();
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
      // TODO Auto-generated method stub
      return null;
    }
    
    @Override
    public PaintSize visitIntPower(IntPower intPower) {
      // TODO Auto-generated method stub
      return null;
    }
  };
  
  @Override
  public Void visitConstDouble(ConstDoubleExpr constDoubleExpr) {
    // TODO Auto-generated method stub
    return null;
  }
  
  @Override
  public Void visitConstIntExpr(ConstIntExpr constIntExpr) {
    // TODO Auto-generated method stub
    return null;
  }
  
  @Override
  public Void visitVarExpr(VarExpr varExpr) {
    // TODO Auto-generated method stub
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
  
  private PaintSize strSize(String str) {
    Graphics2D g = getGraphics();
    int w = g.getFontMetrics().stringWidth(str);
    int h1 = g.getFontMetrics().getAscent();
    int h2 = g.getFontMetrics().getDescent();
    g.dispose();
    PaintSize ret = new PaintSize(w, h1, h2);
    return ret;
  }
  
}
