package pompei.maths.syms2.diff;

import static pompei.maths.syms_diff.visitable.frm.f;
import static pompei.maths.syms_diff.visitable.frm.p;
import pompei.maths.syms_diff.model.Form;
import pompei.maths.syms_diff.visitors.DiffVisitor;
import pompei.maths.syms_diff.visitors.paint.PaintUtil;

public class DiffVisitorProbe {
  public static void main(String[] args) throws Exception {
    Form a0 = f("a", "*", p("b", -1));
    
    DiffVisitor differ = new DiffVisitor();
    
    Form a1 = a0.visit(differ);
    Form a2 = a1.visit(differ);
    Form a3 = a2.visit(differ);
    Form a4 = a3.visit(differ);
    
    PaintUtil.paintToFile("build/DiffVisitorProbe.png", //
        a0.visit(R.S), a1.visit(R.S), a2.visit(R.S), a3.visit(R.S), a4.visit(R.S));
    
    System.out.println("OK");
  }
  
}
