package pompei.maths.syms2.model.display.impl;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pompei.maths.syms2.model.display.DisplayExpr;
import pompei.maths.syms2.model.display.DisplayPortImpl;
import pompei.maths.syms2.model.display.Size;

import java.awt.Color;

import static pompei.maths.syms2.model.display.impl.TestDisplayUtil.displayToFile;

public class DisplayLeaningTest {

  enum Side {
    LEFT, RIGHT
  }

  @DataProvider
  public Object[][] dataProvider() {
    return new Object[][]{
        new Object[]{Side.LEFT, 0, 0.05},

        new Object[]{Side.RIGHT, 0, 0.05},

        new Object[]{Side.RIGHT, 1, 0.05},
        new Object[]{Side.RIGHT, -1, 0.05},

        new Object[]{Side.RIGHT, 0.5, 0.05},
        new Object[]{Side.RIGHT, -0.5, 0.05},

        new Object[]{Side.RIGHT, 0.8, 0.05},
        new Object[]{Side.RIGHT, -0.8, 0.05},
    };
  }

  @Test(dataProvider = "dataProvider")
  public void display(Side side, double upFactor, double spaceFactor) throws Exception {

    DisplayExpr base = new DisplayRect(new Size(50, 50, 100),
                                       new Color(255, 0, 0),
                                       new Color(181, 200, 46)
    );

    DisplayExpr leaning = new DisplayRect(new Size(30, 30, 70),
                                          new Color(192, 0, 0),
                                          new Color(142, 161, 41)
    );

    DisplayLeaning expr = new DisplayLeaning(base, leaning, side == Side.RIGHT, upFactor, spaceFactor);

    DisplayPortImpl port = new DisplayPortImpl();
    port.levelOffset = 0;
    expr.setPort(port);

    displayToFile(expr, port, getClass().getSimpleName() + '_' + side.name() + '_' + upFactor + '_' + spaceFactor);
  }
}
