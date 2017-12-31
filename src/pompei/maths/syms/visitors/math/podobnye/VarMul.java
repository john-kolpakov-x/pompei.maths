package pompei.maths.syms.visitors.math.podobnye;

import pompei.maths.syms.top.Expr;
import pompei.maths.syms.visitable.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class VarMul extends HashMap<String, Integer> {

  public void addVar(String name, int aCount) {
    Integer count = get(name);
    if (count == null) count = 0;
    count += aCount;
    put(name, count);
  }

  @SuppressWarnings("unused")
  public void addVar(String name) {
    addVar(name, 1);
  }

  public static VarMul alone(String varName) {
    VarMul ret = new VarMul();
    ret.put(varName, 1);
    return ret;
  }

  public Expr createExpr(boolean withDiv) {
    List<Expr> topList = new ArrayList<>();
    List<Expr> bottomList = new ArrayList<>();

    List<String> varNames = new ArrayList<>();
    varNames.addAll(keySet());
    Collections.sort(varNames);

    for (String varName : varNames) {
      int pow = get(varName);
      if (pow == 0) continue; //a^0 == 1
      if (withDiv && pow < 0) {
        addVarToList(bottomList, varName, -pow);
      } else {
        addVarToList(topList, varName, pow);
      }
    }

    Expr top = mulAll(topList);
    Expr bottom = mulAll(bottomList);

    if (top == null) top = ConstInt.ONE;

    if (bottom == null) return top;

    return new Div(top, bottom);
  }

  private static void addVarToList(List<Expr> list, String varName, int pow) {
    if (pow == 0) return;
    Var var = new Var(varName);
    if (pow == 1) {
      list.add(var);
    } else {
      list.add(new IntPower(var, pow));
    }
  }

  private static Expr mulAll(List<Expr> list) {
    int C = list.size();
    if (C == 0) return null;
    Expr ret = list.get(0);
    for (int i = 1; i < C; i++) {
      ret = new Mul(ret, list.get(i));
    }
    return ret;
  }
}
