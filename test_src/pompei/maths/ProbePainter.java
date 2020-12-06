package pompei.maths;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;

public class ProbePainter {
  public static void main(String[] args) throws Exception {
    int width = 800, height = 600;

    BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

    Graphics2D g = image.createGraphics();
    {
      g.setColor(Color.WHITE);
      g.fillRect(0, 0, width, height);

      g.setFont(g.getFont().deriveFont(70f));

      g.setColor(Color.BLUE);
      int ascent = g.getFontMetrics().getAscent();
      int descent = g.getFontMetrics().getDescent();

      g.drawLine(10, 100, width, 100);
      g.drawLine(10, 100 - ascent, width, 100 - ascent);
      g.drawLine(10, 100 + descent, width, 100 + descent);

      g.setColor(Color.BLACK);

      g.drawString("World!? jgqщ", 10, 100);
    }
    g.dispose();

    ImageIO.write(image, "png", new File("build/asd.png"));

    System.out.println("OK");
  }
}
