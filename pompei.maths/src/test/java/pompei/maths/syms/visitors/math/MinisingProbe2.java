package pompei.maths.syms.visitors.math;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import pompei.maths.ProbeUtil;
import pompei.maths.syms.top.Expr;
import pompei.maths.syms.visitable.Var;
import pompei.maths.syms.visitable.ex;
import pompei.maths.syms.visitors.Skobing;

public class MinisingProbe2 {
  public static void main(String[] args) throws Exception {
    
    int width = 1800, height = 600;
    
    BufferedImage image = ProbeUtil.createImage(width, height);
    Expr in = create(1);
    
    ProbeUtil.paint(image, 100, 150, Skobing.add(in));
    
    Expr out1 = in.visit(new Minising(true));
    
    ProbeUtil.paint(image, 100, 200, Skobing.add(out1));
    
    ImageIO.write(image, "png", new File("build/MinisingProbe2.png"));
    
    System.out.println("OK build/MinisingProbe2.png");
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
    Expr a = ex.var("a");
    Expr b = ex.var("b");
    Expr c = ex.var("c");
    Expr d = ex.var("d");
    
    b = ex.minis(b);
    c = ex.minis(c);
    
    return ex.plus(ex.muls(a, b, c, d), ex.muls(a, b, c, d));
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
