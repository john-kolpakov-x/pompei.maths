package pompei.maths.syms_diff;

import static pompei.maths.syms_diff.visitable.frm.f;
import static pompei.maths.syms_diff.visitable.frm.p;

import java.io.IOException;

import pompei.maths.syms_diff.model.Form;
import pompei.maths.syms_diff.visitable.frm;
import pompei.maths.syms_diff.visitors.AddSkobVisitor;
import pompei.maths.syms_diff.visitors.KillMinusSkobVisitor;
import pompei.maths.syms_diff.visitors.paint.PaintUtil;
import pompei.maths.syms_diff.visitors.podob.Podob;

public class PodobProbe {
  private static final AddSkobVisitor S = new AddSkobVisitor();
  @SuppressWarnings("unused")
  private static final KillMinusSkobVisitor D = new KillMinusSkobVisitor();
  
  public static void main(String[] args) throws IOException {
    
    Form a1 = f("a", "*", "b");
    Form a2 = f("a", "*", "b 1");
    Form a3 = f("a", "*", p("b 1", -2));
    Form a4 = f("b 1", "*", "a");
    
    Form a = frm.plus(a1, a2, a3, a4);
    
    Podob res = Podob.extract(a);
    
    PaintUtil.paintToFile("build/PodobProbe.png", a.visit(S), res.form().visit(S));
    
    System.out.println("OK");
  }
}
