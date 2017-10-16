package pompei.maths.syms.gens;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import pompei.maths.ProbeUtil;
import pompei.maths.syms.top.Expr;
import pompei.maths.syms.visitable.ex;
import pompei.maths.syms.visitors.Skobing;
import pompei.maths.syms.visitors.math.Dividing;
import pompei.maths.syms.visitors.math.EvalConsts;
import pompei.maths.syms.visitors.math.KillMulPlus;
import pompei.maths.syms.visitors.math.Minising;
import pompei.maths.syms.visitors.math.podobnye.Similar;

public class SomeProbe2 {
  public static void main(String[] args) throws Exception {
    
    int width = 1800, height = 600;
    
    BufferedImage image = ProbeUtil.createImage(width, height);
    Expr in = create(1);
    
    int x = 20, y = 0, st = 120;
    
    ProbeUtil.paint(image, x, y += st, Skobing.add(in));
    
    Expr out = in;
    
    out = out.visit(new Minising(true));
    out = out.visit(new Dividing());
    ProbeUtil.paint(image, x, y += st, Skobing.add(out));
    out = out.visit(new KillMulPlus());
    ProbeUtil.paint(image, x, y += st, Skobing.add(out));
    out = out.visit(new Similar(true));
    out = out.visit(new EvalConsts());
    ProbeUtil.paint(image, x, y += st, Skobing.add(out));
    
    ImageIO.write(image, "png", new File("build/SomeProbe2.png"));
    
    System.out.println("OK build/SomeProbe2.png");
  }
  
  private static Expr create(int nomer) {
    switch (nomer) {
    case 1:
      return create1();
      
    default:
      throw new RuntimeException();
    }
  }
  
  private static Expr create1() {
    Expr abcd = ddivv("A", "B", "A", "C");
    Expr efgh = ddivv("B", "A", "B", "C");
    Expr ijkl = ddivv("C", "A", "C", "B");
    Expr mnop = ddivv("A", "B", "C", "B");
    
    abcd = plus1(abcd);
    efgh = plus1(efgh);
    ijkl = plus1(ijkl);
    mnop = plus1(mnop);
    
    Expr top = plus1(ex.mul(abcd, efgh));
    Expr bottom = plus1(ex.mul(ijkl, mnop));
    
    return ex.div(top, bottom);
  }
  
  private static Expr plus1(Expr expr) {
    return ex.plus(expr, ex.fix(1));
  }
  
  private static Expr ddivv(String A, String B, String C, String D) {
    Expr a = ex.var(A);
    Expr b = ex.var(B);
    Expr c = ex.var(C);
    Expr d = ex.var(D);
    
    Expr ab = ex.minus(a, b);
    Expr cd = ex.minus(c, d);
    
    return ex.div(ab, cd);
  }
}
