package pompei.maths.syms.visitors.math;

import pompei.maths.ProbeUtil;
import pompei.maths.syms.top.Expr;
import pompei.maths.syms.visitable.Var;
import pompei.maths.syms.visitable.ex;
import pompei.maths.syms.visitors.Skobing;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

public class KillMulPlusProbe {
  public static void main(String[] args) throws Exception {

    int width = 1800, height = 600;

    BufferedImage image = ProbeUtil.createImage(width, height);
    Expr in = create(1);

    ProbeUtil.paint(image, 100, 150, Skobing.add(in));

    Expr out = in.visit(new Minising(true)).visit(new KillMulPlus()).visit(new Minising(false));

    ProbeUtil.paint(image, 100, 300, Skobing.add(out));
    //    ProbeUtil.paint(image, 100, 450, Skobing.add(out));

    ImageIO.write(image, "png", new File("build/KillMulPlusProbe.png"));

    System.out.println("OK build/KillMulPlusProbe.png");
  }

  private static Expr create(int nomer) {
    switch (nomer) {
      case 1:
        return create1();
    }
    throw new RuntimeException();
  }

  private static Expr create1() {
    Var a = ex.var("a");
    Var b = ex.var("b");
    Var c = ex.var("c");
    Var d = ex.var("d");

    Expr ab = ex.minus(a, b);
    Expr cd = ex.plus(c, d);

    Expr mul = ex.mul((ab), (cd));

    Expr plus = ex.plus(mul, ex.div(ex.fix(1), ex.fix(2)));

    Expr mul2 = ex.mul(ab, d);

    return ex.plus(plus, mul2);
  }

}
