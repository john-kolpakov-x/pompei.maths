package pompei.maths.syms2.diff;

import pompei.maths.syms_diff.model.Form;
import pompei.maths.syms_diff.visitors.paint.PaintUtil;

import static pompei.maths.syms_diff.visitable.frm.d;
import static pompei.maths.syms_diff.visitable.frm.f;

public class PaintDiffs {
  public static void main(String[] args) throws Exception {
    Form from = d(f("a", "/", "b"));

    PaintUtil.paintToFile("build/PaintDiffs.png", from.visit(R.S));

    System.out.println("Complete");
  }
}
