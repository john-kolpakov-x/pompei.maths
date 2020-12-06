package pompei.maths.syms2.diff;

import pompei.maths.syms_diff.model.Form;
import pompei.maths.syms_diff.visitors.DiffVisitor;
import pompei.maths.syms_diff.visitors.KillMulPlusVisitor;
import pompei.maths.syms_diff.visitors.paint.PaintUtil;
import pompei.maths.syms_diff.visitors.similar.SimilarVisitor;

import static pompei.maths.syms_diff.visitable.frm.f;
import static pompei.maths.syms_diff.visitable.frm.p;

public class DiffVisitorProbe {

  private static Form openBracketsAndSimilar(Form form) {
    KillMulPlusVisitor killMulPlusVisitor = new KillMulPlusVisitor(false);

    do {
      killMulPlusVisitor.reset();
      form = form.visit(killMulPlusVisitor);
    } while (killMulPlusVisitor.wasOperations());


    SimilarVisitor similarVisitor = new SimilarVisitor();

    return form.visit(similarVisitor);
  }

  public static void main(String[] args) throws Exception {

    Form a0 = f(p("a", 1), "*", p("b", -2));

    DiffVisitor differ = new DiffVisitor();

    Form a1 = openBracketsAndSimilar(a0.visit(differ));
    Form a2 = openBracketsAndSimilar(a1.visit(differ));
    Form a3 = openBracketsAndSimilar(a2.visit(differ));
    Form a4 = openBracketsAndSimilar(a3.visit(differ));

    Form a34 = f(a3, "*", a4).visit(R.S);
    Form a34x = openBracketsAndSimilar(a34.visit(R.D));

    PaintUtil.paintToFile("build/DiffVisitorProbe.png",
                          a0.visit(R.S), a1.visit(R.S), a2.visit(R.S), a3.visit(R.S), a4.visit(R.S), a34, a34x);

    System.out.println("OK");
  }
}
