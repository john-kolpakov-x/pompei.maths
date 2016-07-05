package pompei.maths.syms2.model.display;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;

public class DisplayBracketsTest {

  @DataProvider
  public Object[][] dataProvider() {
    return new Object[][]{

        new Object[]{BracketsType.SQUARE},
        new Object[]{BracketsType.ROUND},

    };
  }

  @Test(dataProvider = "dataProvider")
  public void display(BracketsType bracketsType) throws Exception {

    DisplayExpr in = new DisplayRect(new Size(70, 50, 100),
        new Color(255, 0, 0),
        new Color(181, 200, 46)
    );

    DisplayBrackets displayBrackets = new DisplayBrackets(in, bracketsType, new Color(0, 0, 0));

    DisplayPort port = new DisplayPort();
    displayBrackets.setPort(port);

    {
      BufferedImage image = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);

      port.graphics = image.createGraphics();

      displayBrackets.size();

      port.graphics.dispose();
    }

    {
      Size size = displayBrackets.size();
      BufferedImage image = new BufferedImage(size.width + 20, size.height() + 20, BufferedImage.TYPE_INT_ARGB);

      port.graphics = image.createGraphics();

      displayBrackets.displayTo(10, 10 + size.top);

      port.graphics.dispose();

      new File("build").mkdirs();
      ImageIO.write(image, "png", new File("build/" + getClass().getSimpleName() + '_' + bracketsType + ".png"));
      System.out.println("OK");
    }
  }
}