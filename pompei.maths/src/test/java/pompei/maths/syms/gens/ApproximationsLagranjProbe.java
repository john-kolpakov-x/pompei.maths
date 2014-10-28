package pompei.maths.syms.gens;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import pompei.maths.ProbeUtil;
import pompei.maths.syms.top.Expr;
import pompei.maths.syms.visitable.ConstInt;
import pompei.maths.syms.visitable.Var;
import pompei.maths.syms.visitors.Skobing;
import pompei.maths.syms.visitors.math.EvalConsts;
import pompei.maths.syms.visitors.math.KillMulPlus;
import pompei.maths.syms.visitors.math.Minising;
import pompei.maths.syms.visitors.math.podobnye.Podobnye;

public class ApproximationsLagranjProbe {
  public static void main(String[] args) throws Exception {
    
    int width = 1800, height = 600;
    
    BufferedImage image = ProbeUtil.createImage(width, height);
    
    int NUM = 4;
    
    Expr in = Approximations.lagranj("x", "t", NUM);
    
    int x = 20, y = 0, st = 75;
    
    ProbeUtil.paint(image, x, y += st, Skobing.add(in));
    
    Replacer re = new Replacer();
    re.add("t", new Var("u"));
    for (int i = 1; i <= NUM; i++) {
      re.add("t" + i, ConstInt.get(i));
    }
    for (int i = 1; i <= NUM; i++) {
      re.add("x" + i, ConstInt.get(i * 187 + 1123));
    }
    
    in = in.visit(re);
    
    Expr out = in;
    
    out = out.visit(new Minising(true));
    out = out.visit(new EvalConsts());
    ProbeUtil.paint(image, x, y += st, Skobing.add(out));
    
    out = out.visit(new KillMulPlus());
    ProbeUtil.paint(image, x, y += st, Skobing.add(out));
    
    out = out.visit(new Podobnye(true));
    ProbeUtil.paint(image, x, y += st, Skobing.add(out));
    
    out = out.visit(new EvalConsts());
    ProbeUtil.paint(image, x, y += st, Skobing.add(out));
    
    ImageIO.write(image, "png", new File("build/ApproximationsLagranj.png"));
    
    System.out.println("OK build/ApproximationsLagranj.png");
  }
}
