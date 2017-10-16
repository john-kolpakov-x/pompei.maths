package pompei.maths.syms.gens;

import java.util.HashMap;
import java.util.Map;

import pompei.maths.syms.top.Expr;
import pompei.maths.syms.visitable.Var;
import pompei.maths.syms.visitors.Scanner;

public class Replacer extends Scanner {
  private final Map<String, Expr> replaceMap = new HashMap<>();
  
  public void add(String varName, Expr withWhat) {
    replaceMap.put(varName, withWhat);
  }
  
  @Override
  public Expr visitVar(Var var) {
    Expr expr = replaceMap.get(var.name);
    return expr == null ? var :expr;
  }
}
