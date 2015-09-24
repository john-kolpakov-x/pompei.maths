package pompei.maths.syms_diff.visitable;

import pompei.maths.syms_diff.model.Form;
import pompei.maths.syms_diff.model.FormVisitor;

public class Plus implements Form {
  
  public final Form left;
  public final Form right;
  
  @Override
  public <T> T visit(FormVisitor<T> v) {
    return v.visitPlus(this);
  }
  
  public Plus(Form left, Form right) {
    this.left = left;
    this.right = right;
  }
}
