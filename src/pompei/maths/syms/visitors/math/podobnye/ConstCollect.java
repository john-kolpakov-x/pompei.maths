package pompei.maths.syms.visitors.math.podobnye;

import pompei.maths.syms.top.Expr;
import pompei.maths.syms.visitable.ConstInt;
import pompei.maths.syms.visitable.IntPower;
import pompei.maths.syms.visitable.Minis;
import pompei.maths.syms.visitable.Mul;

import java.util.ArrayList;
import java.util.List;

public class ConstCollect {
  final List<Expr> list = new ArrayList<>();
  boolean minis = false;

  void addConst(Expr expr, int pow) {
    if (pow == 0) {
      return;
    }
    if (pow == 1) {
      list.add(expr);
      return;
    }
    list.add(new IntPower(expr, pow));
  }

  void addConst(Expr expr) {
    list.add(expr);
  }

  void minis() {
    minis = !minis;
  }

  boolean isOne() {
    if (list.size() > 0) {
      return false;
    }
    return !minis;
  }

  boolean isMinisOne() {
    if (list.size() > 0) {
      return false;
    }
    return minis;
  }

  Expr createExpr() {
    int C = list.size();
    if (C == 0) {
      return minis ? ConstInt.M_ONE : ConstInt.ONE;
    }

    Expr ret = list.get(0);

    for (int i = 1; i < C; i++) {
      ret = new Mul(ret, list.get(i));
    }

    return ret;
  }

  public Expr mulTo(Expr expr) {
    if (isOne()) {
      return expr;
    }
    if (isMinisOne()) {
      return new Minis(expr);
    }
    return new Mul(createExpr(), expr);
  }
}
