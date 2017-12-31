package pompei.maths.syms.gens;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import pompei.maths.ProbeUtil;
import pompei.maths.syms.top.Expr;
import pompei.maths.syms.visitable.ex;
import pompei.maths.syms.visitors.Skobing;
import pompei.maths.syms.visitors.math.ConstIntToDiv;
import pompei.maths.syms.visitors.math.EvalConsts;
import pompei.maths.syms.visitors.math.Minising;
import pompei.maths.syms.visitors.math.podobnye.Similar;

public class SomeProbe {
  public static void main(String[] args) throws Exception {
    
    int width = 1800, height = 600;
    
    BufferedImage image = ProbeUtil.createImage(width, height);
    Expr in = create(1);
    
    int x = 20, y = 0, st = 120;
    
    ProbeUtil.paint(image, x, y += st, Skobing.add(in));
    
    Expr out = in;
    
    out = out.visit(new Minising(true));
    out = out.visit(new Similar(true));
    ProbeUtil.paint(image, x, y += st, Skobing.add(out));
    
    out = out.visit(new EvalConsts());
    out = out.visit(new ConstIntToDiv());
    ProbeUtil.paint(image, x, y += st, Skobing.add(out));
    
    ImageIO.write(image, "png", new File("build/SomeProbe.png"));
    
    System.out.println("OK build/SomeProbe.png");
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
    Expr top1 = ex.power(ex.var("A"), -13);
    
    Expr top2 = ex.minus(ex.plus(ex.power(ex.fix(3), 3), ex.fix(6)), ex.div(ex.fix(1), ex.fix(3)));
    top2 = ex.power(top2, -2);
    
    Expr top3 = ex.power(ex.var("B"), 2);
    
    Expr top4 = ex.minus(ex.fix(1), ex.div(ex.fix(1), ex.power(ex.fix(2), 3)));
    
    Expr chis = ex.muls(top1, top2, top3, top4);
    
    Expr bot1 = ex.power(ex.mul(ex.power(ex.var("B"), 2), ex.power(ex.var("A"), 7)), -3);
    
    Expr bot2 = ex.power(ex.var("C"), -3);
    
    Expr bot3 = ex.plus(ex.fix(6), ex.fix(3));
    bot3 = ex.power(bot3, -2);
    
    Expr bot4_1 = ex.mul(ex.plus(ex.fix(1), ex.fix(2)), ex.power(ex.var("A"), 4));
    Expr bot4_2 = ex.mul(ex.power(ex.var("C"), 3),
        ex.plus(ex.div(ex.fix(1), ex.fix(35)), ex.fix(6)));
    Expr bot4 = ex.div(bot4_1, bot4_2);
    
    Expr znam = ex.muls(bot1, bot2, bot3, bot4);
    
    Expr one = ex.div(chis, znam);
    Expr two = ex.fix(3);
    Expr three = ex.power(ex.var("Z"), -3);
    
    return ex.plus(ex.muls(one, two, three), ex.fix(1));
  }
}
