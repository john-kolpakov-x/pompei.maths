package pompei.maths.syms2.diff;

import static pompei.maths.syms_diff.visitable.frm.f;
import static pompei.maths.syms_diff.visitable.frm.p;

import java.io.IOException;

import pompei.maths.syms_diff.model.Form;
import pompei.maths.syms_diff.visitors.KillDivVisitor;
import pompei.maths.syms_diff.visitors.paint.PaintUtil;

public class KillDivVisitorProbe {
  
  public static void main(String[] args) throws IOException {
    
    Form from = f(f("a", "-", "b"), "*", f("c", "/", p(p(f("A", "*", "B -1"), 7), 4)));
    
    KillDivVisitor killDiv = new KillDivVisitor();
    
    Form to = from.visit(R.D).visit(killDiv);
    
    PaintUtil.paintToFile("build/KillDivVisitorProbe.png", from.visit(R.S), to.visit(R.S));
    
    System.out.println("Complete");
  }
}
