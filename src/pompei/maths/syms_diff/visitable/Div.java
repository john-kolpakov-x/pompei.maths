package pompei.maths.syms_diff.visitable;

import pompei.maths.syms_diff.model.Form;
import pompei.maths.syms_diff.model.FormVisitor;

public class Div implements Form {

  public final Form top;
  public final Form bottom;

  @Override
  public <T> T visit(FormVisitor<T> v) {
    return v.visitDiv(this);
  }

  public Div(Form top, Form bottom) {
    this.top = top;
    this.bottom = bottom;
  }
}
