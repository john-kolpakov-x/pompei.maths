package pompei.maths.syms.gens;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import pompei.maths.ProbeUtil;
import pompei.maths.syms.top.Expr;
import pompei.maths.syms.visitors.Skobing;
import pompei.maths.syms.visitors.math.KillMulPlus;
import pompei.maths.syms.visitors.math.Minising;
import pompei.maths.syms.visitors.math.ReorganizeMinuses;

public class ApproximationsLagranjProbe {
  public static void main(String[] args) throws Exception {
    
    int width = 1800, height = 600;
    
    BufferedImage image = ProbeUtil.createImage(width, height);
    Expr in = Approximations.lagranj("x", "t", 3);
    
    ProbeUtil.paint(image, 100, 150, Skobing.add(in));
    
    Expr out = in;
    
    out = out.visit(new Minising(true));
    out = out.visit(new KillMulPlus());
    out = out.visit(new Minising(false));
    out = out.visit(new ReorganizeMinuses());
    
    ProbeUtil.paint(image, 100, 300, Skobing.add(out));
    
    ImageIO.write(image, "png", new File("build/ApproximationsLagranj.png"));
    
    System.out.println("OK build/ApproximationsLagranj.png");
  }
  
}
