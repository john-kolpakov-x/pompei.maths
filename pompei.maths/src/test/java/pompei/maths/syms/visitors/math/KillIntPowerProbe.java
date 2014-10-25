package pompei.maths.syms.visitors.math;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import pompei.maths.ProbeUtil;
import pompei.maths.syms.top.Expr;
import pompei.maths.syms.visitable.ConstInt;
import pompei.maths.syms.visitable.Div;
import pompei.maths.syms.visitable.IntPower;
import pompei.maths.syms.visitable.Mul;
import pompei.maths.syms.visitable.Plus;
import pompei.maths.syms.visitable.Var;
import pompei.maths.syms.visitable.ex;
import pompei.maths.syms.visitors.Skobing;

public class KillIntPowerProbe {
  public static void main(String[] args) throws Exception {
    
    int width = 800, height = 600;
    
    BufferedImage image = ProbeUtil.createImage(width, height);
    Expr in = create(2);
    
    ProbeUtil.paint(image, 100, 150, Skobing.add(in));
    
    Expr out = in.visit(new KillIntPower());
    
    ProbeUtil.paint(image, 100, 300, Skobing.add(out));
    //    ProbeUtil.paint(image, 100, 450, Skobing.add(out));
    
    ImageIO.write(image, "png", new File("build/KillIntPowerProbe.png"));
    
    System.out.println("OK build/KillIntPowerProbe.png");
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
    Expr a = ex.var("a");
    Expr b = ex.var("b");
    Expr a_b = ex.plus(a, b);
    IntPower pow = ex.power(a_b, -3);
    return pow;
  }
  
  private static Expr create3() {
    ConstInt c1 = ex.fix(1);
    ConstInt c2 = ex.fix(2);
    Div c1_2 = ex.div(c1, c2);
    IntPower pow = ex.power(c1_2, -19);
    
    return new Plus(pow, pow);
  }
  
  private static Expr create1() {
    Var a = ex.var("a");
    Var b = ex.var("b");
    Var c = ex.var("c");
    Var d = ex.var("d");
    
    Plus ab = ex.plus(a, b);
    Plus cd = ex.plus(c, d);
    
    IntPower cd17 = ex.power(cd, -17);
    
    //    Mul mul = ex.mul(ex.s(ab), ex.s(cd));
    Mul mul = ex.mul((ab), (cd17));
    
    Plus plus = ex.plus(mul, ex.div(ex.fix(1), ex.fix(2)));
    
    return plus;
  }
  
}
