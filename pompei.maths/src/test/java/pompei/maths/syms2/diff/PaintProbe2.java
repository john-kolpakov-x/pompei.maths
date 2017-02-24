package pompei.maths.syms2.diff;

import static pompei.maths.syms_diff.visitable.frm.f;
import pompei.maths.syms_diff.model.Form;
import pompei.maths.syms_diff.visitors.AddSkobVisitor;
import pompei.maths.syms_diff.visitors.KillMinusSkobVisitor;
import pompei.maths.syms_diff.visitors.paint.PaintUtil;

public class PaintProbe2 {
  public static void main(String[] args) throws Exception {
    
    Form form1 = f(f("a", "-", "b"), "*", f("c", "/", "D"));
    
    AddSkobVisitor v = new AddSkobVisitor();
    
    Form form2 = form1.visit(v);
    
    KillMinusSkobVisitor del = new KillMinusSkobVisitor();
    
    Form form3 = form2.visit(del);
    
    PaintUtil.paintToFile("build/xx.png", form1, form2, form3);
    
    System.out.println("Complete");
  }
}
