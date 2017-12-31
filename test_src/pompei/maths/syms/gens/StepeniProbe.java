package pompei.maths.syms.gens;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import pompei.maths.ProbeUtil;
import pompei.maths.syms.top.Expr;
import pompei.maths.syms.visitable.IntPower;
import pompei.maths.syms.visitable.Plus;
import pompei.maths.syms.visitable.ex;
import pompei.maths.syms.visitors.Skobing;
import pompei.maths.syms.visitors.math.ConstIntToDiv;
import pompei.maths.syms.visitors.math.EvalConsts;
import pompei.maths.syms.visitors.math.KillIntPower;
import pompei.maths.syms.visitors.math.KillMulOne;
import pompei.maths.syms.visitors.math.KillMulPlus;
import pompei.maths.syms.visitors.math.Minising;
import pompei.maths.syms.visitors.math.podobnye.Similar;

public class StepeniProbe {
  public static void main(String[] args) throws Exception {
    
    int width = 1800, height = 600;
    
    BufferedImage image = ProbeUtil.createImage(width, height);
    Expr in = create(1);
    
    int x = 20, y = 0, st = 75;
    
    ProbeUtil.paint(image, x, y += st, Skobing.add(in));
    
    Expr out = in;
    
    out = out.visit(new Minising(true));
    out = out.visit(new KillIntPower());
    ProbeUtil.paint(image, x, y += st, Skobing.add(out));
    
    out = out.visit(new KillMulPlus());
    ProbeUtil.paint(image, x, y += st, Skobing.add(out));
    
    out = out.visit(new Similar(true));
    ProbeUtil.paint(image, x, y += st, Skobing.add(out));
    
    out = out.visit(new EvalConsts());
    out = out.visit(new KillMulOne());
    ProbeUtil.paint(image, x, y += st, Skobing.add(out));
    out = out.visit(new ConstIntToDiv());
    ProbeUtil.paint(image, x, y += st, Skobing.add(out));
    
    ImageIO.write(image, "png", new File("build/StepeniProbe.png"));
    
    System.out.println("OK build/StepeniProbe.png");
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
    Expr a = ex.var("a");
    Expr b = ex.var("b");
    
    Plus abc = ex.plus(a, b);
    
    return new IntPower(abc, 13);
  }
  
}
