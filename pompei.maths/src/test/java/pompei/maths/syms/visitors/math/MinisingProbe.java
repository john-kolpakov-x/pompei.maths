package pompei.maths.syms.visitors.math;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import pompei.maths.ProbeUtil;
import pompei.maths.syms.top.Expr;
import pompei.maths.syms.visitable.Var;
import pompei.maths.syms.visitable.ex;
import pompei.maths.syms.visitors.Skobing;

public class MinisingProbe {
  public static void main(String[] args) throws Exception {
    
    int width = 1800, height = 600;
    
    BufferedImage image = ProbeUtil.createImage(width, height);
    Expr in = create(2);
    
    ProbeUtil.paint(image, 100, 150, Skobing.add(in));
    
    Expr out1 = in.visit(new Minising(true));
    
    ProbeUtil.paint(image, 100, 200, Skobing.add(out1));
    
    Expr out2 = out1.visit(new KillMulPlus());
    
    ProbeUtil.paint(image, 100, 250, Skobing.add(out2));
    
    Expr out3 = out2.visit(new Minising(false));
    
    ProbeUtil.paint(image, 100, 300, Skobing.add(out3));
    
    Expr out4 = out3.visit(new ReorganizeMinuses());
    
    ProbeUtil.paint(image, 100, 350, Skobing.add(out4));
    
    ImageIO.write(image, "png", new File("build/MinisingProbe.png"));
    
    System.out.println("OK build/MinisingProbe.png");
  }
  
  private static Expr create(int nomer) {
    switch (nomer) {
    case 1:
      return create1();
    case 2:
      return create2();
    }
    throw new RuntimeException();
  }
  
  private static Expr create1() {
    Var a = ex.var("a");
    Var b = ex.var("b");
    Var c = ex.var("c");
    Var d = ex.var("d");
    
    Expr ab = ex.minus(a, b);
    Expr cd = ex.minus(c, d);
    
    Expr mul = ex.mul((ab), (cd));
    
    Expr plus = ex.plus(mul, ex.div(ex.fix(1), ex.fix(2)));
    
    Expr mul2 = ex.mul(ab, d);
    
    return ex.plus(plus, mul2);
  }
  
  private static Expr create2() {
    Var a = ex.var("a");
    Var b = ex.var("b");
    Var c = ex.var("c");
    Var d = ex.var("d");
    
    Expr ab = ex.minus(a, b);
    Expr cd = ex.minus(c, d);
    
    Expr mul = ex.mul((ab), (cd));
    
    return mul;
  }
  
}
