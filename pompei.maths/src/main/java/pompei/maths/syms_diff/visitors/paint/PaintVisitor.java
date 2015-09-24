package pompei.maths.syms_diff.visitors.paint;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import pompei.maths.syms_diff.model.Const;
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
  
  public float mainSize = 14;
  public float powerSize = 10;
  
  public float downFactor = 0.7f;
  
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
    throw new IllegalAccessError();
  }
  
  @Override
  public Painter visitPower(Power power) {
    Painter formPainter = power.form.visit(this);
    if (power.n == 1) return formPainter;
    return painterInPower(formPainter, "" + power.n, Color.BLACK);
  }
  
  @Override
  public Painter visitDiv(Div div) {
    throw new IllegalAccessError();
  }
  
  @Override
  public Painter visitSkob(Skob skob) {
    throw new IllegalAccessError();
  }
  
  @Override
  public Painter visitMinis(Minis minis) {
    throw new IllegalAccessError();
  }
  
  @Override
  public Painter visitPlus(Plus plus) {
    throw new IllegalAccessError();
  }
  
  @Override
  public Painter visitMinus(Minus minus) {
    throw new IllegalAccessError();
  }
  
  @Override
  public Painter visitMul(Mul mul) {
    throw new IllegalAccessError();
  }
}
