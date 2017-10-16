package pompei.maths.syms2.model.display.impl;

import java.awt.Color;
import org.testng.annotations.Test;
import pompei.maths.syms2.model.display.DisplayExpr;
import pompei.maths.syms2.model.display.DisplayPortImpl;
import pompei.maths.syms2.model.display.Size;


import static pompei.maths.syms2.model.display.impl.TestDisplayUtil.displayToFile;


public class DisplayRectTest {
  @Test
  public void display() throws Exception {
    DisplayExpr expr = new DisplayRect(new Size(50, 50, 100),
      new Color(255, 0, 0),
      new Color(181, 200, 46)
    );

    DisplayPortImpl port = new DisplayPortImpl();
    expr.setPort(port);

    displayToFile(expr, port, getClass().getSimpleName());
  }
}