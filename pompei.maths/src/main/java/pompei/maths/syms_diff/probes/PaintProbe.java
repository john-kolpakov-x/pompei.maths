package pompei.maths.syms_diff.probes;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import pompei.maths.syms_diff.model.Form;
import pompei.maths.syms_diff.visitable.Power;
import pompei.maths.syms_diff.visitable.Var;
import pompei.maths.syms_diff.visitors.paint.PaintVisitor;
import pompei.maths.syms_diff.visitors.paint.Painter;
import pompei.maths.syms_diff.visitors.paint.Size;

public class PaintProbe {
  public static void main(String[] args) throws Exception {
    new File("build").mkdirs();
    
    BufferedImage tmpImage = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
    
    Form form = getForm();
    
    Graphics2D gtmp = tmpImage.createGraphics();
    PaintVisitor vis = new PaintVisitor(gtmp);
    gtmp.dispose();
    
    Painter p = form.visit(vis);
    
    Size size = p.getSize();
    
    BufferedImage image = new BufferedImage(size.width, size.heightBottom + size.heightTop,
        BufferedImage.TYPE_INT_ARGB);
    Graphics2D g = image.createGraphics();
    g.setColor(Color.WHITE);
    g.fillRect(0, 0, image.getWidth(), image.getHeight());
    p.paintTo(g, 0, size.heightTop);
    g.dispose();
    
    ImageIO.write(image, "png", new File("build/im.png"));
    
    System.out.println("Complete");
  }
  
  private static Form getForm() {
    Var a = new Var("W");
    Power power = new Power(a, -13);
    return power;
  }
}
