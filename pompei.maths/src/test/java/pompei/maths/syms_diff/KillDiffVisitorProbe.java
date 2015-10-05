package pompei.maths.syms_diff;

import static pompei.maths.syms_diff.R.S;
import static pompei.maths.syms_diff.visitable.frm.d;
import static pompei.maths.syms_diff.visitable.frm.f;
import pompei.maths.syms_diff.model.Form;
import pompei.maths.syms_diff.visitors.KillDiffVisitor;
import pompei.maths.syms_diff.visitors.paint.PaintUtil;

public class KillDiffVisitorProbe {
  public static void main(String[] args) throws Exception {
    Form a1 = d(f("a", "/", "b"));
    
    KillDiffVisitor v = new KillDiffVisitor();
    
    Form a2 = a1.visit(v);
    
    Form a3 = d(a2);
    
    Form a4 = a3.visit(v);
    
    PaintUtil.paintToFile("build/KillDiffVisitorProbe.png"//
        , a1.visit(S), a2.visit(S), a3.visit(S), a4.visit(S));
    
    System.out.println("OK");
  }
}
