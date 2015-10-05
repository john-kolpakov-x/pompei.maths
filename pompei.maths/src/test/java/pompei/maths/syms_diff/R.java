package pompei.maths.syms_diff;

import pompei.maths.syms_diff.visitors.AddSkobVisitor;
import pompei.maths.syms_diff.visitors.KillMinusSkobVisitor;

public class R {
  public static final AddSkobVisitor S = new AddSkobVisitor();
  public static final KillMinusSkobVisitor D = new KillMinusSkobVisitor();
}
