package pompei.maths.syms_diff.visitors.paint;

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
  
  public float mainSize = 17;
  public float powerSize = 12;
  
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
    
    Painter powOffseted = OffsetPainter.offset(pow, dx, dy);
    
    return UnionPainter.union(exp, powOffseted);
  }
  
  @Override
  public Painter visitConst(Const c) {
    return new StrPainter(g, font.deriveFont(mainSize), c.asStr(), Color.BLACK);
  }
  
  @Override
  public Painter visitDiff(Diff diff) {
    Painter formPainter = diff.form.visit(this);
    if (diff.n == 1) return formPainter;
    return painterInPower(formPainter, "" + diff.n, Color.RED);
  }
  
  @Override
  public Painter visitPower(Power power) {
    Painter formPainter = power.form.visit(this);
    if (power.n == 1) return formPainter;
    return painterInPower(formPainter, "" + power.n, Color.BLACK);
  }
  
  @Override
  public Painter visitDiv(Div div) {
    throw new UnsupportedOperationException();
  }
  
  @Override
  public Painter visitSkob(Skob skob) {
    Painter p = skob.form.visit(this);
    Size pSize = p.getSize();
    int h = pSize.heightBottom + pSize.heightTop;
    int w = (int)(h * 0.3f + 0.5f);
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
}
