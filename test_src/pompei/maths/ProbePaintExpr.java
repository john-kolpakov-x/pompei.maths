package pompei.maths;

import pompei.maths.syms.top.Expr;
import pompei.maths.syms.visitable.ex;
import pompei.maths.syms.visitors.ExpPainter;
import pompei.maths.syms.visitors.ExpSizer;
import pompei.maths.syms.visitors.GraphicsSource;
import pompei.maths.syms.visitors.PaintSize;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;

public class ProbePaintExpr {
  public static void main(String[] args) throws Exception {

    Expr expr = create1();

    int width = 800, height = 600;

    BufferedImage image = ProbeUtil.createImage(width, height);

    GraphicsSource gs = ProbeUtil.createGS(image);

    ExpSizer expSizer = new ExpSizer(gs);

    PaintSize size = expr.visit(expSizer);

    int x = 100, y = 300;

    {
      Graphics2D g = image.createGraphics();
      g.setColor(Color.BLUE);

      g.drawLine(x, y, x + size.w, y);
      g.drawLine(x, y - size.h1, x + size.w, y - size.h1);
      g.drawLine(x, y + size.h2, x + size.w, y + size.h2);

      g.drawLine(x, y - size.h1, x, y + size.h2);
      g.drawLine(x + size.w, y - size.h1, x + size.w, y + size.h2);

      g.dispose();
    }

    ExpPainter ep = new ExpPainter(expSizer, x, y);
    expr.visit(ep);

    gs.closeAll();

    ImageIO.write(image, "png", new File("build/probe-paint.png"));

    System.out.println("OK build/probe-paint.png");
  }

  private static Expr create1() {
    Expr c23 = ex.fix(23);
    Expr c23_6 = ex.power(c23, -17);

    Expr c1 = ex.fix(1);

    Expr c2 = ex.fix(2);
    Expr c1_2 = ex.div(c1, c2);

    Expr c1_12 = ex.plus(c1, c1_2);

    Expr div23_6_1 = ex.div(c23_6, c1_12);

    Expr c31 = ex.fix(3.1);
    Expr varA = ex.var("A");
    Expr c31_varA = ex.mul(c31, varA);
    Expr plus = ex.plus(c31_varA, div23_6_1);

    return plus;
  }

}
