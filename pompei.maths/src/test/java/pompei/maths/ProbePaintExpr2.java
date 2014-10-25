package pompei.maths;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import pompei.maths.syms.top.Expr;
import pompei.maths.syms.visitable.ConstIntExpr;
import pompei.maths.syms.visitable.Div;
import pompei.maths.syms.visitable.IntPower;
import pompei.maths.syms.visitable.Mul;
import pompei.maths.syms.visitable.Plus;
import pompei.maths.syms.visitable.VarExpr;
import pompei.maths.syms.visitable.ex;
import pompei.maths.syms.visitors.ExpPainter;
import pompei.maths.syms.visitors.ExpSizer;
import pompei.maths.syms.visitors.GraphicsSource;
import pompei.maths.syms.visitors.PaintSize;
import pompei.maths.syms.visitors.Skobing;
import pompei.maths.syms.visitors.Unskobing;

public class ProbePaintExpr2 {
  public static void main(String[] args) throws Exception {
    
    int width = 800, height = 600;
    
    BufferedImage image = ProbeUtil.createImage(width, height);
    Expr expr = create(3);
    
    paint(image, 100, 150, expr);
    Expr exprS = Skobing.add(expr);
    paint(image, 100, 300, exprS);
    Expr expr2 = Unskobing.remove(exprS);
    paint(image, 100, 450, expr2);
    
    ImageIO.write(image, "png", new File("build/probe-paint2.png"));
    
    System.out.println("OK build/probe-paint2.png");
  }
  
  private static void paint(BufferedImage image, int x, int y, Expr expr) {
    GraphicsSource gs = ProbeUtil.createGS(image);
    
    ExpSizer expSizer = new ExpSizer(gs);
    
    PaintSize size = expr.visit(expSizer);
    
    {
      Graphics2D g = image.createGraphics();
      g.setColor(Color.BLUE);
      
      g.drawLine(x, y, x + size.w, y);
      g.drawLine(x, y - size.h1, x + size.w, y - size.h1);
      g.drawLine(x, y + size.h2, x + size.w, y + size.h2);
      
      g.drawLine(x, y - size.h1, x, y + size.h2);
      g.drawLine(x + size.w, y - size.h1, x + size.w, y + size.h2);
      
      g.dispose();
    }
    
    ExpPainter ep = new ExpPainter(expSizer, x, y);
    expr.visit(ep);
    
    gs.closeAll();
  }
  
  private static Expr create(int nomer) {
    switch (nomer) {
    case 1:
      return create1();
    case 2:
      return create2();
    case 3:
      return create3();
    }
    throw new RuntimeException();
  }
  
  private static Expr create2() {
    ConstIntExpr c1 = ex.fix(1);
    ConstIntExpr c2 = ex.fix(2);
    Div c1_2 = ex.div(c1, c2);
    IntPower pow = ex.power(c1_2, -19);
    return pow;
  }
  
  private static Expr create3() {
    ConstIntExpr c1 = ex.fix(1);
    ConstIntExpr c2 = ex.fix(2);
    Div c1_2 = ex.div(c1, c2);
    IntPower pow = ex.power(c1_2, -19);
    
    return new Plus(pow, pow);
  }
  
  private static Expr create1() {
    VarExpr a = ex.var("a");
    VarExpr b = ex.var("b");
    VarExpr c = ex.var("c");
    VarExpr d = ex.var("d");
    
    Plus ab = ex.plus(a, b);
    Plus cd = ex.plus(c, d);
    
    IntPower cd17 = ex.power(cd, -17);
    
    //    Mul mul = ex.mul(ex.s(ab), ex.s(cd));
    Mul mul = ex.mul((ab), (cd17));
    
    return mul;
  }
  
}
