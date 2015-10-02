package pompei.maths.syms_diff.visitable;

import pompei.maths.syms_diff.model.Form;

public class CannotDiff extends RuntimeException {
  public final Form form;
  
  public CannotDiff(Form form) {
    super("" + form);
    this.form = form;
  }
}
