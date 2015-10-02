package pompei.maths.syms_diff;

import static pompei.maths.syms_diff.visitable.frm.f;
import static pompei.maths.syms_diff.visitable.frm.p;
import pompei.maths.syms_diff.model.Form;
import pompei.maths.syms_diff.visitors.AddSkobVisitor;
import pompei.maths.syms_diff.visitors.DiffVisitor;
import pompei.maths.syms_diff.visitors.KillMinusSkobVisitor;
import pompei.maths.syms_diff.visitors.paint.PaintUtil;

public class DiffVisitorProbe {
  private static final AddSkobVisitor S = new AddSkobVisitor();
  @SuppressWarnings("unused")
  private static final KillMinusSkobVisitor D = new KillMinusSkobVisitor();
  
  public static void main(String[] args) throws Exception {
    Form a0 = f("a", "*", p("b", -1));
    
    DiffVisitor differ = new DiffVisitor();
    
    Form a1 = a0.visit(differ);
    Form a2 = a1.visit(differ);
    Form a3 = a2.visit(differ);
    Form a4 = a3.visit(differ);
    
    PaintUtil.paintToFile("build/DiffVisitorProbe.png", //
        a0.visit(S), a1.visit(S), a2.visit(S), a3.visit(S), a4.visit(S));
    
    System.out.println("OK");
  }
  
}
