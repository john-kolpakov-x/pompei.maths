package pompei.maths.syms.gens;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import pompei.maths.ProbeUtil;
import pompei.maths.syms.top.Expr;
import pompei.maths.syms.visitable.Div;
import pompei.maths.syms.visitable.ex;
import pompei.maths.syms.visitors.Skobing;
import pompei.maths.syms.visitors.math.Dividing;
import pompei.maths.syms.visitors.math.KillMulPlus;
import pompei.maths.syms.visitors.math.Minising;

public class ApproximationsLagranjProbe {
  public static void main(String[] args) throws Exception {
    
    int width = 1800, height = 600;
    
    BufferedImage image = ProbeUtil.createImage(width, height);
    Expr in = Approximations.lagranj("x", "t", 3);
    
    int x = 20, y = 0, st = 75;
    
    ProbeUtil.paint(image, x, y += st, Skobing.add(in));
    
    Expr out = in;
    
    out = out.visit(new Minising(true));
    out = out.visit(new Dividing());
    
    ProbeUtil.paint(image, x, y += st, Skobing.add(out));
    
    Expr chis = ((Div)out).top;
    Expr znam = ((Div)out).bottom;
    
    chis = chis.visit(new KillMulPlus());
    ProbeUtil.paint(image, x, y += st, Skobing.add(ex.div(chis, znam)));
    
    chis = chis.visit(new Minising(true));
    //chis = chis.visit(new ReorganizeMinuses());
    
    ProbeUtil.paint(image, x, y += st, Skobing.add(ex.div(chis, znam)));
    
    ImageIO.write(image, "png", new File("build/ApproximationsLagranj.png"));
    
    System.out.println("OK build/ApproximationsLagranj.png");
  }
  
}
