package pompei.maths.syms2.model.display.impl;

import java.awt.Color;
import java.awt.image.BufferedImage;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pompei.maths.syms2.model.display.DisplayPortImpl;


import static pompei.maths.syms2.model.display.impl.TestDisplayUtil.displayToFile;

public class DisplayTextTest {
  @DataProvider
  public Object[][] dataProvider() {
    return new Object[][]{

      new Object[]{"asd", false, false, -2},
      new Object[]{"asd", false, false, -1},
      new Object[]{"asd", false, false, 0},
      new Object[]{"asd", false, false, 1},
      new Object[]{"asd", false, false, 2},

      new Object[]{"Style", false, false, 0},
      new Object[]{"Style", true, false, 0},
      new Object[]{"Style", true, true, 0},
      new Object[]{"Style", false, true, 0},

    };
  }

  @Test(dataProvider = "dataProvider")
  public void display(String text, boolean bold, boolean italic, int levelOffset) throws Exception {

    DisplayText expr = new DisplayText(0, text, Color.black, bold, italic);

    DisplayPortImpl port = new DisplayPortImpl();
    port.levelOffset = levelOffset;
    expr.setPort(port);

    {
      BufferedImage image = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
      port.setGraphics(image.createGraphics());
      expr.size();
      port.graphics().dispose();
    }

    displayToFile(expr, port, getClass().getSimpleName() + '_' + text
      + (bold ? "_bold" : "") + (italic ? "_italic" : "") + '_' + levelOffset
    );
  }
}