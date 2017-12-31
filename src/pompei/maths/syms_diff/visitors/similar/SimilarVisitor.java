package pompei.maths.syms_diff.visitors.similar;

import pompei.maths.syms_diff.model.Form;
import pompei.maths.syms_diff.visitable.Plus;
import pompei.maths.syms_diff.visitors.Scanner;

public class SimilarVisitor extends Scanner {

  @Override
  public Form visitPlus(Plus plus) {

    MulPartCollector mulPartCollector = new MulPartCollector();
    mulPartCollector.collectFrom(plus);

    Form leftPart = null;

    for (Form e : mulPartCollector.leftPart) {
      Form visitedElement = e.visit(this);
      if (leftPart == null) leftPart = visitedElement;
      else leftPart = new Plus(leftPart, visitedElement);
    }

    Form similar = mulPartCollector.similar();

    if (leftPart == null && similar == null) {
      throw new NullPointerException("leftPart == null && similar == null");
    }

    if (leftPart == null) return similar;
    if (similar == null) return leftPart;
    return new Plus(leftPart, similar);
  }
}
