package pompei.maths.syms.gens;

import pompei.maths.syms.top.Expr;
import pompei.maths.syms.visitable.ex;

public class Approximations {
  public static Expr laGrange(String fVar, String xVar, int count) {
    Expr ret = null;

    for (int i = 1; i <= count; i++) {
      Expr part = ex.mul(ex.var(fVar + i), basePolynomial(xVar, i, count));
      if (ret == null) {
        ret = part;
      } else {
        ret = ex.plus(ret, part);
      }
    }

    return ret;
  }

  private static Expr basePolynomial(String xVar, int i, int count) {
    Expr top = null;
    Expr bottom = null;

    for (int j = 1; j <= count; j++) {
      if (i == j) {
        continue;
      }
      Expr topPart = ex.minus(ex.var(xVar), ex.var(xVar + j));
      Expr bottomPart = ex.minus(ex.var(xVar + i), ex.var(xVar + j));

      if (top == null) {
        top = topPart;
        bottom = bottomPart;
      } else {
        top = ex.mul(top, topPart);
        bottom = ex.mul(bottom, bottomPart);
      }
    }

    return ex.div(top, bottom);
  }
}
