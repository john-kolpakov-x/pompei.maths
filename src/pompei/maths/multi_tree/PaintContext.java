package pompei.maths.multi_tree;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class PaintContext {

  private File outPngFile;

  public void setOutPngFileName(String outPngFileName) {
    outPngFile = new File(outPngFileName);
  }

  private int width = 0, height = 0;

  private BufferedImage image = null;

  private Graphics2D graphics = null;

  public Graphics2D graphics() {

    if (graphics == null) {
      if (image == null) {
        createImage();
      }

      graphics = image.createGraphics();
      graphics.setColor(Color.WHITE);
      graphics.fillRect(0, 0, image.getWidth(), image.getHeight());
    }

    return graphics;
  }

  public void setSize(int width, int height) {
    this.width = width;
    this.height = height;
    image = null;
    graphics = null;
  }

  private void createImage() {
    if (width <= 0) {
      width = 1;
    }
    if (height <= 0) {
      height = 1;
    }

    image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
  }

  public void finish() {

    if (image == null || graphics == null) {
      throw new RuntimeException("Cannot finish without image or graphics");
    }

    if (outPngFile == null) {
      throw new RuntimeException("Cannot finish without outPngFile");
    }

    //noinspection ResultOfMethodCallIgnored
    outPngFile.getParentFile().mkdirs();

    graphics();
    graphics.dispose();
    graphics = null;

    try {
      ImageIO.write(image, "png", outPngFile);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

  }

  public int referenceRadius() {
    return 4;
  }

  public int elementSpace() {
    return 5;
  }

  public int paddingLeft() {
    return 4;
  }

  public int paddingRight() {
    return 4;
  }

  public int paddingTop() {
    return 4;
  }

  public int paddingBottom() {
    return 4;
  }

  public int nodeSpaceH() {
    return 10;
  }

  public int nodeSpaceV() {
    return 20;
  }

  public int pageTop() {
    return 15;
  }

  public int pageRight() {
    return 15;
  }

  public int pageBottom() {
    return 15;
  }

  public int pageLeft() {
    return 15;
  }
}
