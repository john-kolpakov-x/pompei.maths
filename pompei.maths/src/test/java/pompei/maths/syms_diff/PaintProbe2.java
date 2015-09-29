package pompei.maths.syms_diff;

import static pompei.maths.syms_diff.visitable.frm.f;
import pompei.maths.syms_diff.model.Form;
import pompei.maths.syms_diff.visitors.AddSkobVisitor;
import pompei.maths.syms_diff.visitors.paint.PaintUtil;

public class PaintProbe2 {
  public static void main(String[] args) throws Exception {
    
    Form form1 = f(f("a", "+", "b"), "*", f("c", "/", "D"));
    
    AddSkobVisitor v = new AddSkobVisitor();
    
    Form form2 = form1.visit(v);
    
    PaintUtil.paintToFile("build/xx.png", form1, form2);
    
    System.out.println("Complete");
  }
}
