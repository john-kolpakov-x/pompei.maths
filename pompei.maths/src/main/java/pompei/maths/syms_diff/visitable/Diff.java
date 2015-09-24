package pompei.maths.syms_diff.visitable;

import pompei.maths.syms_diff.model.Form;
import pompei.maths.syms_diff.model.FormVisitor;

public class Diff implements Form {
  
  public final Form form;
  public final int n;
  
  @Override
  public <T> T visit(FormVisitor<T> v) {
    return v.visitDiff(this);
  }
  
  public Diff(Form form, int n) {
    this.form = form;
    this.n = n;
  }
}
