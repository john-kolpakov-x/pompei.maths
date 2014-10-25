package pompei.maths.syms.visitors;

import pompei.maths.syms.top.Expr;
import pompei.maths.syms.visitable.Skob;

public class Unskobing extends Scanner {
  
  public Unskobing() {}
  
  public int removedSkobs = 0;
  
  public static Expr remove(Expr expr) {
    return expr.visit(new Unskobing());
  }
  
  @Override
  public Expr visitSkob(Skob skob) {
    Expr ret = skob;
    while (ret instanceof Skob) {
      ret = ((Skob)ret).target;
      removedSkobs++;
    }
    return ret;
  }
  
}
