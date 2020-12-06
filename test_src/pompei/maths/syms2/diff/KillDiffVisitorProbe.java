package pompei.maths.syms2.diff;

import pompei.maths.syms_diff.model.Form;
import pompei.maths.syms_diff.visitors.KillDiffVisitor;
import pompei.maths.syms_diff.visitors.paint.PaintUtil;

import static pompei.maths.syms_diff.visitable.frm.d;
import static pompei.maths.syms_diff.visitable.frm.f;

public class KillDiffVisitorProbe {
  public static void main(String[] args) throws Exception {
    Form a1 = d(f("a", "/", "b"));

    KillDiffVisitor v = new KillDiffVisitor();

    Form a2 = a1.visit(v);

    Form a3 = d(a2);

    Form a4 = a3.visit(v);

    PaintUtil.paintToFile("build/KillDiffVisitorProbe.png"//
        , a1.visit(R.S), a2.visit(R.S), a3.visit(R.S), a4.visit(R.S));

    System.out.println("OK");
  }
}
