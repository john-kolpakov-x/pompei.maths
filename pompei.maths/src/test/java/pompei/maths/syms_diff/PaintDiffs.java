package pompei.maths.syms_diff;

import static pompei.maths.syms_diff.visitable.frm.d;
import static pompei.maths.syms_diff.visitable.frm.f;
import pompei.maths.syms_diff.model.Form;
import pompei.maths.syms_diff.visitors.AddSkobVisitor;
import pompei.maths.syms_diff.visitors.KillMinusSkobVisitor;
import pompei.maths.syms_diff.visitors.paint.PaintUtil;

public class PaintDiffs {
  private static final AddSkobVisitor S = new AddSkobVisitor();
  @SuppressWarnings("unused")
  private static final KillMinusSkobVisitor D = new KillMinusSkobVisitor();
  
  public static void main(String[] args) throws Exception {
    Form from = d(f("a", "/", "b"));
    
    PaintUtil.paintToFile("build/PaintDiffs.png", from.visit(S));
    
    System.out.println("Complete");
  }
}
