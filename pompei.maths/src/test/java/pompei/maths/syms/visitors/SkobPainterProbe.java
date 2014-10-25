package pompei.maths.syms.visitors;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import pompei.maths.ProbeUtil;

public class SkobPainterProbe {
  public static void main(String[] args) throws Exception {
    int width = 800, height = 600;
    
    BufferedImage image = ProbeUtil.createImage(width, height);
    
    {
      Graphics2D g = image.createGraphics();
      
      {
        int x = 100, y = 100, w = 70, h = 400;
        paint(g, x, y, w, h, false);
      }
      {
        int x = 300, y = 100, w = 70, h = 400;
        paint(g, x, y, w, h, true);
      }
      g.dispose();
    }
    
    ImageIO.write(image, "png", new File("build/probe-skob.png"));
    
    System.out.println("OK build/probe-skob.png");
  }
  
  private static void paint(Graphics2D g, int x, int y, int w, int h, boolean isRight) {
    g.setColor(Color.BLUE);
    g.drawRect(x, y, w, h);
    g.setColor(Color.BLACK);
    SkobPainter.paint(g, null, x, y, w, h, isRight);
  }
}
