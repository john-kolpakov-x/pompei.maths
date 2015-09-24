package pompei.maths.syms_diff.visitable;

import pompei.maths.syms_diff.model.Form;
import pompei.maths.syms_diff.model.FormVisitor;

public class Var implements Form {
  
  public final String name;
  
  public Var(String name) {
    this.name = name;
  }
  
  @Override
  public <T> T visit(FormVisitor<T> v) {
    return v.visitVar(this);
  }
}
