package pompei.maths.syms_diff.visitable;

import pompei.maths.syms_diff.model.Form;
import pompei.maths.syms_diff.model.FormVisitor;

public class Power implements Form {

  public final Form form;
  public final int n;

  @Override
  public <T> T visit(FormVisitor<T> v) {
    return v.visitPower(this);
  }

  public Power(int n, Form form) {
    this.form = form;
    this.n = n;
  }
}
