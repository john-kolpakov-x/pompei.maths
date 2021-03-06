package pompei.maths.syms.visitors;

import pompei.maths.syms.top.Visitor;
import pompei.maths.syms.visitable.ConstDouble;
import pompei.maths.syms.visitable.ConstInt;
import pompei.maths.syms.visitable.Div;
import pompei.maths.syms.visitable.IntPower;
import pompei.maths.syms.visitable.Minis;
import pompei.maths.syms.visitable.Minus;
import pompei.maths.syms.visitable.Mul;
import pompei.maths.syms.visitable.Plus;
import pompei.maths.syms.visitable.Skob;
import pompei.maths.syms.visitable.Var;

import java.awt.Graphics2D;

public class ExpPainter implements Visitor<Void> {

  private final ExpSizer sizer;
  public int x, y;

  public ExpPainter(ExpSizer sizer, int x, int y) {
    this.sizer = sizer;
    this.x = x;
    this.y = y;
  }

  @Override
  public Void visitConstDouble(ConstDouble constDoubleExpr) {
    sizer.g().drawString("" + constDoubleExpr.value, x, y);
    return null;
  }

  @Override
  public Void visitConstInt(ConstInt constIntExpr) {
    sizer.g().drawString(constIntExpr.displayStr(), x, y);
    return null;
  }

  @Override
  public Void visitVar(Var varExpr) {
    sizer.g().drawString(varExpr.name, x, y);
    return null;
  }

  @Override
  public Void visitPlus(Plus plus) {

    int savedX = x;
    plus.left.visit(this);
    x += plus.left.visit(sizer).w;
    sizer.g().drawString("+", x, y);
    x += sizer.strSize("+").w;
    plus.right.visit(this);
    x = savedX;

    return null;
  }

  @Override
  public Void visitMul(Mul mul) {

    int savedX = x;
    mul.left.visit(this);
    x += mul.left.visit(sizer).w;
    sizer.g().drawString("·", x, y);
    x += sizer.strSize("·").w;
    mul.right.visit(this);
    x = savedX;

    return null;
  }

  @Override
  public Void visitMinus(Minus minus) {

    int savedX = x;
    minus.left.visit(this);
    x += minus.left.visit(sizer).w;
    sizer.g().drawString("−", x, y);
    x += sizer.strSize("−").w;
    minus.right.visit(this);
    x = savedX;

    return null;
  }

  @Override
  public Void visitMinis(Minis minis) {

    int savedX = x;
    sizer.g().drawString("-", x, y);
    x += sizer.strSize("-").w;
    minis.target.visit(this);
    x = savedX;

    return null;
  }

  @Override
  public Void visitDiv(Div div) {
    PaintSize top = div.top.visit(sizer);
    PaintSize bottom = div.bottom.visit(sizer);
    Graphics2D g = sizer.g();
    int ascent = g.getFontMetrics().getAscent();

    int upDist = (int) (ascent * sizer.gs.ascendingMiddleProportion(sizer.level) + 0.5);

    ConfDiv dc = sizer.gs.div();

    int savedX = x, savedY = y;

    int maxW = Math.max(top.w, bottom.w);

    x = savedX + dc.paddingLeft(sizer.level) + (maxW - top.w) / 2;
    y = savedY - upDist - dc.paddingUp(sizer.level) - top.h2;
    div.top.visit(this);

    x = savedX + dc.paddingLeft(sizer.level) + (maxW - bottom.w) / 2;
    y = savedY - upDist + dc.paddingDown(sizer.level) + bottom.h1;
    div.bottom.visit(this);

    x = savedX;
    y = savedY;

    PaintSize size = div.visit(sizer);

    sizer.g().drawLine(x, y - upDist, x + size.w - 1, y - upDist);
    int lineWidth = dc.lineWidth(sizer.level);
    if (lineWidth > 1) {
      int h2 = lineWidth / 2;
      sizer.g().fillRect(x, y - upDist - h2, size.w, lineWidth);
    }

    return null;
  }

  @Override
  public Void visitIntPower(IntPower intPower) {

    PaintSize expSize = intPower.exp.visit(sizer);

    ConfPower confPower = sizer.gs.power();

    int powExpDistance = confPower.powExpDistance(sizer.level);
    double upPercent = confPower.upPercent(sizer.level);

    int h1 = expSize.h1;

    int upMove = (int) (h1 * upPercent);

    intPower.exp.visit(this);
    int x1 = x + expSize.w + powExpDistance;
    int y1 = y - upMove;
    sizer.gs.getGraphics(sizer.level + 1).drawString("" + intPower.pow, x1, y1);

    return null;
  }

  @Override
  public Void visitSkob(Skob skob) {

    PaintSize targetSize = skob.target.visit(sizer);
    ConfSkob conf = sizer.gs.skob();
    int sw = (int) (targetSize.h() * conf.ySizeWidthFactor(sizer.level) + 0.5);
    int minWidth = conf.minWidth(sizer.level);
    if (sw < minWidth) {
      sw = minWidth;
    }

    {
      int sh1 = (int) (targetSize.h1 * (1 - conf.topSizeFactor(sizer.level)) + 0.5);
      int sh2 = (int) (targetSize.h2 * (1 - conf.bottomSizeFactor(sizer.level)) + 0.5);
      int sh = sh1 + sh2;

      {
        int sx = x, sy = y - sh1;
        SkobPainter.paint(sizer.g(), sx, sy, sw, sh, false);
      }
      {
        int sx = x + sw + targetSize.w, sy = y - sh1;
        SkobPainter.paint(sizer.g(), sx, sy, sw, sh, true);
      }
    }

    {
      int savedX = x;
      x += sw;
      skob.target.visit(this);
      x = savedX;
    }

    return null;
  }

}
