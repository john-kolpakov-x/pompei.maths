package pompei.maths.syms.gens;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import pompei.maths.ProbeUtil;
import pompei.maths.syms.top.Expr;
import pompei.maths.syms.visitors.Skobing;
import pompei.maths.syms.visitors.math.Dividing;
import pompei.maths.syms.visitors.math.Minising;

public class ApproximationsLagranj_DividingProbe {
  public static void main(String[] args) throws Exception {
    
    int width = 1800, height = 600;
    
    BufferedImage image = ProbeUtil.createImage(width, height);
    Expr in = Approximations.lagranj("x", "t", 3);
    
    ProbeUtil.paint(image, 20, 150, Skobing.add(in));
    
    Expr out = in;
    
    out = out.visit(new Minising(true));
    out = out.visit(new Dividing());
    
    ProbeUtil.paint(image, 20, 300, Skobing.add(out));
    
    ImageIO.write(image, "png", new File("build/ApproximationsLagranj_DividingProbe.png"));
    
    System.out.println("OK build/ApproximationsLagranj_DividingProbe.png");
  }
  
}
