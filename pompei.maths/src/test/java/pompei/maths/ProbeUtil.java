package pompei.maths;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import pompei.maths.syms.visitors.ConfDiv;
import pompei.maths.syms.visitors.ConfPower;
import pompei.maths.syms.visitors.ConfSkob;
import pompei.maths.syms.visitors.GraphicsSource;

public class ProbeUtil {
  
  static GraphicsSource createGS(BufferedImage image) {
    final Graphics2D g1 = image.createGraphics();
    {
      g1.setFont(g1.getFont().deriveFont(30f));
      g1.setColor(Color.BLACK);
    }
    final Graphics2D g2 = image.createGraphics();
    {
      g2.setFont(g2.getFont().deriveFont(20f));
      g2.setColor(Color.BLACK);
    }
    
    return new GraphicsSource() {
      
      @Override
      public ConfSkob skob() {
        return new ConfSkob() {
          
          @Override
          public double xSizeFactor(int level) {
            return 0.15;
          }
          
          @Override
          public double topSizeFactor(int level) {
            return 0;
          }
          
          @Override
          public double bottomSizeFactor(int level) {
            return 0.5;
          }
          
          @Override
          public int minWidth(int level) {
            return 5;
          }
        };
      }
      
      @Override
      public ConfPower power() {
        return new ConfPower() {
          @Override
          public double upPercent(int expLevel) {
            return 0.65;
          }
          
          @Override
          public int powExpDistance(int expLevel) {
            return 2;
          }
        };
      }
      
      @Override
      public Graphics2D getGraphics(int level) {
        return level == 1 ? g1 : g2;
      }
      
      @Override
      public ConfDiv div() {
        return new ConfDiv() {
          @Override
          public int lineWidth(int level) {
            return level == 1 ? 3 : 2;
          }
          
          @Override
          public int paddingUp(int level) {
            return 0;
          }
          
          @Override
          public int paddingRight(int level) {
            return 0;
          }
          
          @Override
          public int paddingLeft(int level) {
            return 0;
          }
          
          @Override
          public int paddingDown(int level) {
            return 0;
          }
        };
      }
      
      @Override
      public void closeAll() {
        g1.dispose();
        g2.dispose();
      }
      
      @Override
      public float ascendingMiddleProportion(int level) {
        return 0.35f;
      }
    };
  }
  
  public static BufferedImage createImage(int width, int height) {
    BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
    
    Graphics2D g = image.createGraphics();
    {
      g.setColor(Color.WHITE);
      g.fillRect(0, 0, width, height);
    }
    g.dispose();
    return image;
  }
  
}
