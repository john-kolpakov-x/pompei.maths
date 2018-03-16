package pompei.maths.difur;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import pompei.maths.difur.DiffUr;
import pompei.maths.difur.DiffUrDefault;
import pompei.maths.difur.F;
import pompei.maths.difur.Stepper_H4_Hoine;

public class TriMassy {
  
  private final static double k0 = 40;
  private final static double k1 = 2;
  private final static double k2 = 2;
  
  private final static double m0 = 10;
  private final static double m1 = 1;
  private final static double m2 = 1;
  
  private final static double x0 = 0;
  private final static double x1 = 1;
  private final static double x2 = 0;
  
  private final static double v0 = 0;
  private final static double v1 = 0;
  private final static double v2 = 0;
  
  private final static double k0m0 = k0 / m0;
  private final static double k1m0 = k1 / m0;
  private final static double k2m0 = k2 / m0;
  
  private final static double k1m1 = k1 / m1;
  private final static double k2m2 = k2 / m2;
  
  private final static double k0m0_k1m0_k2m0 = k0m0 + k1m0 + k2m0;
  
  private final static F f = (res, t, x) -> {
    res[0] = x[3];
    res[1] = x[4];
    res[2] = x[5];

    res[3] = k1m0 * x[1] + k2m0 * x[2] - k0m0_k1m0_k2m0 * x[0];
    res[4] = k1m1 * (x[0] - x[1]);
    res[5] = k2m2 * (x[0] - x[2]);
  };
  
  private final static double h = 0.0001;
  private final static double xMax = 2;
  private final static double deltaT = 1.0 / 24;
  
  private final static int width = 1300;
  private final static int height = 768;
  
  public static void main(String[] args) throws Exception {
    DiffUr ur = new DiffUrDefault(new Stepper_H4_Hoine());
    ur.prepare(6, f);
    double[] x = ur.getX();
    x[0] = x0;
    x[1] = x1;
    x[2] = x2;
    x[3] = v0;
    x[4] = v1;
    x[5] = v2;
    
    ur.setH(h);
    ur.setT(0);
    
    double tShow = ur.getT() - deltaT;
    int index = 1;
    
    while (ur.getT() < 50) {
      
      if (tShow < ur.getT()) {
        tShow = ur.getT() + deltaT;
        show(ur, index++);
      }
      
      ur.step();
    }
    
    System.out.println("Complete");
  }
  
  private static void show(DiffUr ur, int index) throws Exception {
    double[] x = ur.getX();
    double x0 = x[0];
    double x1 = x[1];
    double x2 = x[2];
    
    BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
    Graphics2D g = img.createGraphics();
    g.setColor(Color.WHITE);
    g.fillRect(0, 0, width, height);
    
    g.setColor(Color.GRAY);
    g.drawLine(0, height / 2, width, height / 2);
    
    {
      g.setColor(Color.GRAY);
      g.drawLine(width / 4, 0, width / 4, height);
      
      int X = width / 4, Y = height / 2 - (int)(x1 / xMax * height / 2);
      
      drawGruz(g, m1, X, Y);
    }
    {
      g.setColor(Color.GRAY);
      g.drawLine(width / 2, 0, width / 2, height);
      
      int X = width / 2, Y = height / 2 - (int)(x0 / xMax * height / 2);
      
      drawGruz(g, m0, X, Y);
    }
    {
      g.setColor(Color.GRAY);
      g.drawLine(3 * width / 4, 0, 3 * width / 4, height);
      
      int X = 3 * width / 4, Y = height / 2 - (int)(x2 / xMax * height / 2);
      
      drawGruz(g, m2, X, Y);
    }
    
    g.dispose();
    
    StringBuilder I = new StringBuilder("" + index);
    while (I.length() < 4) {
      I.insert(0, "0");
    }
    File file = new File("build/images/img-" + I + ".png");
    file.getParentFile().mkdirs();
    ImageIO.write(img, "png", file);
    System.out.println("Written " + file + ", T = " + ur.getT());
  }
  
  private static void drawGruz(Graphics2D g, double m, int x, int y) {
    int x1 = x - 10;
    int y1 = y - 5;
    int x2 = x + 10;
    int y2 = y + 5;
    
    g.setColor(Color.WHITE);
    g.fillRect(x1, y1, x2 - x1, y2 - y1);
    g.setColor(Color.BLACK);
    g.drawRect(x1, y1, x2 - x1, y2 - y1);
  }
}
