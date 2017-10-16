package pompei.maths.syms2.model.display.impl;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import pompei.maths.syms2.model.display.DisplayExpr;
import pompei.maths.syms2.model.display.DisplayPortImpl;
import pompei.maths.syms2.model.display.Size;

public class TestDisplayUtil {
  public static void displayToFile(DisplayExpr displayExpr, DisplayPortImpl port, String fileName) throws IOException {

    {
      BufferedImage image = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
      port.setGraphics(image.createGraphics());
      displayExpr.size();
      port.graphics().dispose();
    }

    {
      Size size = displayExpr.size();
      BufferedImage image = new BufferedImage(size.width + 20, size.height() + 20, BufferedImage.TYPE_INT_ARGB);

      port.setGraphics(image.createGraphics());

      displayExpr.displayTo(10, 10 + size.top);

      port.graphics().dispose();

      new File("build").mkdirs();

      ImageIO.write(image, "png", new File("build/" + fileName + ".png"));
    }
  }
}
