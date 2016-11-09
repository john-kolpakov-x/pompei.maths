package pompei.maths.syms_diff.visitors.paint;

import static java.lang.Math.max;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import pompei.maths.syms_diff.model.Const;
import pompei.maths.syms_diff.model.Form;
import pompei.maths.syms_diff.model.FormVisitor;
import pompei.maths.syms_diff.visitable.Diff;
import pompei.maths.syms_diff.visitable.Div;
import pompei.maths.syms_diff.visitable.Func;
import pompei.maths.syms_diff.visitable.Minis;
import pompei.maths.syms_diff.visitable.Minus;
import pompei.maths.syms_diff.visitable.Mul;
import pompei.maths.syms_diff.visitable.Plus;
import pompei.maths.syms_diff.visitable.Power;
import pompei.maths.syms_diff.visitable.Skob;
import pompei.maths.syms_diff.visitable.Var;

public class PaintVisitor implements FormVisitor<Painter> {
  
  private final Graphics2D g;
  public Font font;
  
  public PaintVisitor(Graphics2D g) {
    this.g = g;
    font = g.getFont();
  }
  
  public float mainSize = 24;
  public float powerSize = 14;
  
  public float downFactor = 0.4f;
  
  @Override
  public Painter visitVar(Var constVar) {
    return new StrPainter(g, font.deriveFont(mainSize), constVar.name, Color.BLACK);
  }
  
  @Override
  public Painter visitFunc(Func func) {
    StrPainter exp = new StrPainter(g, font.deriveFont(mainSize), func.name, Color.RED);
    if (func.n == 0) return exp;
    return painterInPower(exp, "" + func.n, Color.RED);
  }
  
  private Painter painterInPower(Painter exp, String powStr, Color color) {
    StrPainter pow = new StrPainter(g, font.deriveFont(powerSize), powStr, color);
    Size powSize = pow.getSize();
    int h = powSize.heightTop + powSize.heightBottom;
    int downStep = (int)(h * downFactor + 0.5f);
    
    Size expSize = exp.getSize();
    
    int dx = expSize.width;
    int dy = expSize.heightTop - downStep;
    
    Painter powOffset = OffsetPainter.offset(pow, dx, dy);
    
    return UnionPainter.union(exp, powOffset);
  }
  
  @Override
  public Painter visitConst(Const c) {
    return new StrPainter(g, font.deriveFont(mainSize), c.asStr(), Color.BLACK);
  }
  
  @Override
  public Painter visitDiff(Diff diff) {
    Painter formPainter = diff.form.visit(this);
    if (diff.n == 0) return formPainter;
    return painterInPower(formPainter, "" + diff.n, Color.RED);
  }
  
  @Override
  public Painter visitPower(Power power) {
    Painter formPainter = power.form.visit(this);
    if (power.n == 1) return formPainter;
    return painterInPower(formPainter, "" + power.n, Color.BLACK);
  }
  
  @Override
  public Painter visitSkob(Skob skob) {
    Painter p = skob.form.visit(this);
    Size pSize = p.getSize();
    int h = pSize.heightBottom + pSize.heightTop;
    int w = (int)(h * 0.2f + 0.5f);
    Size skobSize = new Size(w, pSize.heightTop, pSize.heightBottom);
    SkobPainter left = new SkobPainter(skobSize, false);
    SkobPainter right = new SkobPainter(skobSize, true);
    return OrderPainter.order(left, p, right);
  }
  
  @Override
  public Painter visitMinis(Minis minis) {
    StrPainter operPainter = new StrPainter(g, font.deriveFont(mainSize), "-", Color.BLACK);
    return OrderPainter.order(operPainter, minis.form.visit(this));
  }
  
  @Override
  public Painter visitPlus(Plus plus) {
    return visitOper(plus.left, "+", plus.right);
  }
  
  @Override
  public Painter visitMinus(Minus minus) {
    return visitOper(minus.left, "-", minus.right);
  }
  
  private Painter visitOper(Form left, String oper, Form right) {
    StrPainter operPainter = new StrPainter(g, font.deriveFont(mainSize), oper, Color.BLACK);
    return OrderPainter.order(left.visit(this), operPainter, right.visit(this));
  }
  
  @Override
  public Painter visitMul(Mul mul) {
    return visitOper(mul.left, "·", mul.right);
  }
  
  @Override
  public Painter visitDiv(Div div) {
    final int DV = 3, DH = 2;
    
    final int UP = 9;
    
    final Painter top = div.top.visit(this);
    final Painter bottom = div.bottom.visit(this);
    
    Size topSize = top.getSize();
    Size bottomSize = bottom.getSize();
    
    final int w = max(topSize.width, bottomSize.width);
    
    final int dxTop = (w - topSize.width) / 2 + DV;
    final int dxBottom = (w - bottomSize.width) / 2 + DV;
    
    final int dyTop = -topSize.heightBottom - DH - UP;
    final int dyBottom = bottomSize.heightTop + DH - UP;
    
    final Size size = new Size(w + 2 * DV, //
        topSize.height() + DH - UP, //
        bottomSize.height() + DH - UP);
    
    return new Painter() {
      @Override
      public void paintTo(Graphics2D g, int x, int y) {
        top.paintTo(g, x + dxTop, y + dyTop);
        bottom.paintTo(g, x + dxBottom, y + dyBottom);
        
        g.setColor(Color.BLACK);
        g.fillRect(x, y - UP - 1, size.width, 2);
      }
      
      @Override
      public Size getSize() {
        return size;
      }
    };
  }
}
