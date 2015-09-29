package pompei.maths.syms_diff.visitors.paint;

import static java.awt.image.BufferedImage.TYPE_INT_ARGB;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import pompei.maths.syms_diff.model.Form;

public class PaintUtil {
  
  private static final int PADDING = 10;
  
  public static void paintToFile(String filename, Form... form) throws IOException {
    new File(filename).getParentFile().mkdirs();
    
    BufferedImage tmpImage = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
    
    Graphics2D gtmp = tmpImage.createGraphics();
    PaintVisitor vis = new PaintVisitor(gtmp);
    gtmp.dispose();
    
    int width = 0, height = PADDING;
    
    List<Painter> pp = new ArrayList<>();
    for (Form f : form) {
      Painter p = f.visit(vis);
      pp.add(p);
      Size s = p.getSize();
      if (s.width > width) width = s.width;
      height += s.height() + PADDING;
    }
    
    BufferedImage image = new BufferedImage(width + 2 * PADDING, height, TYPE_INT_ARGB);
    
    Graphics2D g = image.createGraphics();
    g.setColor(Color.WHITE);
    g.fillRect(0, 0, image.getWidth(), image.getHeight());
    
    int y = PADDING;
    for (Painter p : pp) {
      Size s = p.getSize();
      p.paintTo(g, PADDING, y + s.heightTop);
      y += s.height() + PADDING;
    }
    
    g.dispose();
    
    ImageIO.write(image, "png", new File(filename));
  }
  
}
