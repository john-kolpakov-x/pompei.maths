package pompei.maths.grav_masses;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class PicturePainter {
  public double w2 = 10;
  public double W = 1024, H = 768;

  public double h2;
  public double x1, x2;
  public double y1, y2;


  private static int toInt(double x) {
    return (int) Math.round(x);
  }

  BufferedImage image;
  Graphics2D g;

  public void initPicture() {

    h2 = w2 * H / W;
    x1 = -w2;
    x2 = w2;
    y1 = -h2;
    y2 = h2;

    image = new BufferedImage(toInt(W), toInt(H), BufferedImage.TYPE_INT_ARGB);
    g = image.createGraphics();
    g.setColor(Color.WHITE);
    g.fillRect(0, 0, image.getWidth(), image.getHeight());

    g.setColor(Color.GRAY);
    line(x1, 0, x2, 0);
    line(0, y1, 0, y2);
    g.setColor(Color.GRAY.brighter());

    for (double x = 5; x <= x2; x += 5) {
      line(x, y1, x, y2);
    }
    for (double x = -5; x >= x1; x -= 5) {
      line(x, y1, x, y2);
    }

    for (double y = 5; y <= y2; y += 5) {
      line(x1, y, x2, y);
    }
    for (double y = -5; y >= y1; y -= 5) {
      line(x1, y, x2, y);
    }


  }

  private int calcX(double x) {
    return toInt((x - x1) / (x2 - x1) * W);
  }

  private int calcY(double y) {
    return image.getHeight() - toInt((y - y1) / (y2 - y1) * H);
  }

  private void line(double x1, double y1, double x2, double y2) {
    g.drawLine(calcX(x1), calcY(y1), calcX(x2), calcY(y2));
  }


  public void saveTo(File file) throws IOException {
    g.dispose();
    ImageIO.write(image, "png", file);
  }

  public void paintPoint(Vec3 p, int pointSize) {
    int X = calcX(p.x);
    int Y = calcY(p.y);
    for (int y = Y - pointSize; y <= Y + pointSize; y++) {
      g.drawLine(X - pointSize, y, X + pointSize + 1, y);
    }
  }
}
