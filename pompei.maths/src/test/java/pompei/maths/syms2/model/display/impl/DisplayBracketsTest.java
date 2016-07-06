package pompei.maths.syms2.model.display.impl;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pompei.maths.syms2.model.display.BracketsType;
import pompei.maths.syms2.model.display.DisplayExpr;
import pompei.maths.syms2.model.display.DisplayPortImpl;
import pompei.maths.syms2.model.display.Size;

import java.awt.Color;
import java.awt.image.BufferedImage;

import static pompei.maths.syms2.model.display.impl.TestDisplayUtil.displayToFile;

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

    DisplayPortImpl port = new DisplayPortImpl();
    displayBrackets.setPort(port);

    {
      BufferedImage image = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);

      port.setGraphics(image.createGraphics());

      displayBrackets.size();

      port.graphics().dispose();
    }

    displayToFile(displayBrackets, port, getClass().getSimpleName() + '_' + bracketsType);
  }
}