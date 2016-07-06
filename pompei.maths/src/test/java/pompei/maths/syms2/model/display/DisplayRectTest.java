package pompei.maths.syms2.model.display;

import org.testng.annotations.Test;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;


public class DisplayRectTest {
  @Test
  public void display() throws Exception {
    DisplayExpr rect = new DisplayRect(new Size(50, 50, 100),
        new Color(255, 0, 0),
        new Color(181, 200, 46)
    );

    BufferedImage image = new BufferedImage(120, 120, BufferedImage.TYPE_INT_ARGB);

    {

      DisplayPortImpl port = new DisplayPortImpl();
      port.setGraphics(image.createGraphics());

      rect.setPort(port);

      rect.displayTo(10, 60);

      port.graphics().dispose();
    }

    new File("build").mkdirs();
    ImageIO.write(image, "png", new File("build/" + getClass().getSimpleName() + ".png"));
    System.out.println("OK");
  }
}