package pompei.maths.syms_diff.visitors;

import pompei.maths.syms_diff.model.Form;
import pompei.maths.syms_diff.visitable.Minis;
import pompei.maths.syms_diff.visitable.Minus;
import pompei.maths.syms_diff.visitable.Plus;
import pompei.maths.syms_diff.visitable.Skob;

public class KillMinusSkobVisitor extends Scanner {
  @Override
  public Form visitMinus(Minus minus) {
    Form left = minus.left.visit(this);
    Form right = minus.right.visit(this);
    return new Plus(left, new Minis(right));
  }

  @Override
  public Form visitSkob(Skob skob) {
    return skob.form.visit(this);
  }
}
