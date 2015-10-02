package pompei.maths.syms_diff;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import pompei.maths.syms_diff.model.Form;
import pompei.maths.syms_diff.visitable.ConstInt;
import pompei.maths.syms_diff.visitable.Div;
import pompei.maths.syms_diff.visitable.Func;
import pompei.maths.syms_diff.visitable.Minus;
import pompei.maths.syms_diff.visitable.Mul;
import pompei.maths.syms_diff.visitable.Plus;
import pompei.maths.syms_diff.visitable.Power;
import pompei.maths.syms_diff.visitable.Skob;
import pompei.maths.syms_diff.visitable.Var;
import pompei.maths.syms_diff.visitors.paint.PaintVisitor;
import pompei.maths.syms_diff.visitors.paint.Painter;
import pompei.maths.syms_diff.visitors.paint.Size;

public class PaintProbe {
  public static void main(String[] args) throws Exception {
    new File("build").mkdirs();
    
    BufferedImage tmpImage = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
    
    Form form = getForm1();
    
    Graphics2D gtmp = tmpImage.createGraphics();
    PaintVisitor vis = new PaintVisitor(gtmp);
    gtmp.dispose();
    
    Painter p = form.visit(vis);
    
    Size size = p.getSize();
    
    BufferedImage image = new BufferedImage(size.width + 20, size.heightBottom + size.heightTop
        + 20, BufferedImage.TYPE_INT_ARGB);
    Graphics2D g = image.createGraphics();
    g.setColor(Color.WHITE);
    g.fillRect(0, 0, image.getWidth(), image.getHeight());
    p.paintTo(g, 10, size.heightTop + 10);
    g.dispose();
    
    ImageIO.write(image, "png", new File("build/im.png"));
    
    System.out.println("Complete");
  }
  
  static Form getForm1() {
    Var w = new Var("W");
    Power power = new Power(-13, w);
    
    Var b = new Var("B");
    
    Plus w_plus_b = new Plus(power, b);
    
    Var c = new Var("C");
    Form d = new Func("D", 4);
    
    Minus c_minus_d = new Minus(c, d);
    
    Mul mul = new Mul(s(w_plus_b), s(c_minus_d));
    
    return new Div(ConstInt.TEN, new Plus(ConstInt.ONE, mul));
  }
  
  static Form getForm2() {
    return s(ConstInt.ONE);
  }
  
  private static Form s(Form form) {
    return new Skob(form);
  }
}
