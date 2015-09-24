package pompei.maths.syms_diff.visitable;

import pompei.maths.syms_diff.model.Form;
import pompei.maths.syms_diff.model.FormVisitor;

public class Minis implements Form {
  
  public final Form form;
  
  @Override
  public <T> T visit(FormVisitor<T> v) {
    return v.visitMinis(this);
  }
  
  public Minis(Form form) {
    this.form = form;
  }
}
