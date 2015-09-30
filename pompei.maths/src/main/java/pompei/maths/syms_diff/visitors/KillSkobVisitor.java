package pompei.maths.syms_diff.visitors;

import pompei.maths.syms_diff.model.Form;
import pompei.maths.syms_diff.visitable.Skob;

public class KillSkobVisitor extends Scanner {
  @Override
  public Form visitSkob(Skob skob) {
    return skob.form.visit(this);
  }
}
