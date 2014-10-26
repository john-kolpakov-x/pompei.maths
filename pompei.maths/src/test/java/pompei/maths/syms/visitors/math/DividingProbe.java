package pompei.maths.syms.visitors.math;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import pompei.maths.ProbeUtil;
import pompei.maths.syms.top.Expr;
import pompei.maths.syms.visitable.Var;
import pompei.maths.syms.visitable.ex;
import pompei.maths.syms.visitors.Skobing;

public class DividingProbe {
  public static void main(String[] args) throws Exception {
    
    for (int i = 1; i <= 10; i++) {
      paintByNomer(i);
    }
    
    System.out.println("OK build/DividingProbe.png");
  }
  
  private static void paintByNomer(int nomer) throws IOException {
    Expr in = create(nomer);
    
    int width = 1800, height = 600;
    BufferedImage image = ProbeUtil.createImage(width, height);
    
    {
      Graphics2D g = image.createGraphics();
      g.setFont(g.getFont().deriveFont(45f));
      g.setColor(Color.BLACK);
      g.drawString("Схема №" + nomer, 10, 50);
      g.drawString("➝", 300, 200);
      g.dispose();
    }
    
    ProbeUtil.paint(image, 100, 200, Skobing.add(in));
    
    Expr out = in;
    
    out = out.visit(new Dividing());
    
    ProbeUtil.paint(image, 400, 200, Skobing.add(out));
    
    String N = "" + nomer;
    while (N.length() < 2) {
      N = "0" + N;
    }
    ImageIO.write(image, "png", new File("build/DividingProbe_" + N + ".png"));
  }
  
  private static Expr create(int nomer) {
    switch (nomer) {
    case 1:
      return create1();
    case 2:
      return create2();
    case 3:
      return create3();
    case 4:
      return create4();
    case 5:
      return create5();
    case 6:
      return create6();
    case 7:
      return create7();
    case 8:
      return create8();
    case 9:
      return create9();
    case 10:
      return create10();
    }
    throw new RuntimeException();
  }
  
  private static Expr create3() {
    Var a = ex.var("a");
    Var b = ex.var("b");
    Var c = ex.var("c");
    
    return ex.mul(a, ex.div(b, c));
  }
  
  private static Expr create2() {
    Var a = ex.var("a");
    Var b = ex.var("b");
    Var c = ex.var("c");
    
    return ex.mul(ex.div(a, b), c);
  }
  
  private static Expr create4() {
    Var a = ex.var("a");
    Var b = ex.var("b");
    Var c = ex.var("c");
    Var d = ex.var("d");
    
    Expr ab = ex.div(a, b);
    Expr cd = ex.div(c, d);
    
    return ex.div(ab, cd);
  }
  
  private static Expr create1() {
    Var a = ex.var("a");
    Var b = ex.var("b");
    Var c = ex.var("c");
    Var d = ex.var("d");
    
    Expr ab = ex.div(a, b);
    Expr cd = ex.div(c, d);
    
    return ex.mul(ab, cd);
  }
  
  private static Expr create5() {
    Var a = ex.var("a");
    Var b = ex.var("b");
    Var c = ex.var("c");
    
    Expr ab = ex.div(a, b);
    
    return ex.div(ab, c);
  }
  
  private static Expr create6() {
    Var a = ex.var("a");
    Var b = ex.var("b");
    Var c = ex.var("c");
    
    Expr bc = ex.div(b, c);
    
    return ex.div(a, bc);
  }
  
  private static Expr create9() {
    Var a = ex.var("a");
    Var b = ex.var("b");
    Var c = ex.var("c");
    
    Expr bc = ex.div(b, c);
    
    return ex.plus(a, bc);
  }
  
  private static Expr create8() {
    Var a = ex.var("a");
    Var b = ex.var("b");
    Var c = ex.var("c");
    
    Expr ab = ex.div(a, b);
    
    return ex.plus(ab, c);
  }
  
  private static Expr create7() {
    Var a = ex.var("a");
    Var b = ex.var("b");
    Var c = ex.var("c");
    Var d = ex.var("d");
    
    Expr ab = ex.div(a, b);
    Expr cd = ex.div(c, d);
    
    return ex.plus(ab, cd);
  }
  
  private static Expr create10() {
    Var a = ex.var("a");
    Var b = ex.var("b");
    
    Expr ab = ex.div(a, b);
    
    return ex.minis(ab);
  }
}
