package pompei.maths.syms_diff.visitable;

import pompei.maths.syms_diff.model.Form;
import pompei.maths.syms_diff.model.FormVisitor;

public class Func implements Form {
  
  public final String name;
  public final int n;
  
  public Func(String name, int n) {
    this.name = name;
    this.n = n;
  }
  
  @Override
  public <T> T visit(FormVisitor<T> v) {
    return v.visitFunc(this);
  }
}
