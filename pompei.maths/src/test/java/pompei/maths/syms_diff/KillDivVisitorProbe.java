package pompei.maths.syms_diff;

import static pompei.maths.syms_diff.visitable.frm.f;
import static pompei.maths.syms_diff.visitable.frm.p;

import java.io.IOException;

import pompei.maths.syms_diff.model.Form;
import pompei.maths.syms_diff.visitors.AddSkobVisitor;
import pompei.maths.syms_diff.visitors.KillDivVisitor;
import pompei.maths.syms_diff.visitors.KillMinusSkobVisitor;
import pompei.maths.syms_diff.visitors.paint.PaintUtil;

public class KillDivVisitorProbe {
  private static final AddSkobVisitor S = new AddSkobVisitor();
  private static final KillMinusSkobVisitor D = new KillMinusSkobVisitor();
  
  public static void main(String[] args) throws IOException {
    
    Form from = f(f("a", "-", "b"), "*", f("c", "/", p(p(f("A", "*", "B -1"), 7), 4)));
    
    KillDivVisitor killDiv = new KillDivVisitor();
    
    Form to = from.visit(D).visit(killDiv);
    
    PaintUtil.paintToFile("build/KillDivVisitorProbe.png", from.visit(S), to.visit(S));
    
    System.out.println("Complete");
  }
}
