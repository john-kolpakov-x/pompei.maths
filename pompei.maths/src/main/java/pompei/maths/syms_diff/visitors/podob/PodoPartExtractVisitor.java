package pompei.maths.syms_diff.visitors.podob;

import pompei.maths.syms_diff.model.Const;
import pompei.maths.syms_diff.model.Form;
import pompei.maths.syms_diff.model.FormVisitor;
import pompei.maths.syms_diff.visitable.Diff;
import pompei.maths.syms_diff.visitable.Div;
import pompei.maths.syms_diff.visitable.Func;
import pompei.maths.syms_diff.visitable.Minis;
import pompei.maths.syms_diff.visitable.Minus;
import pompei.maths.syms_diff.visitable.Mul;
import pompei.maths.syms_diff.visitable.Plus;
import pompei.maths.syms_diff.visitable.Power;
import pompei.maths.syms_diff.visitable.Skob;
import pompei.maths.syms_diff.visitable.Var;

public class PodoPartExtractVisitor implements FormVisitor<PodoPart> {
  
  @Override
  public PodoPart visitVar(Var var) {
    return new PodoPart(var, 1);
  }
  
  @Override
  public PodoPart visitFunc(Func func) {
    return new PodoPart(func, 1);
  }
  
  @Override
  public PodoPart visitConst(Const c) {
    return new PodoPart(c);
  }
  
  @Override
  public PodoPart visitDiff(Diff diff) {
    throw new CannotExtractPodob();
  }
  
  @Override
  public PodoPart visitPower(Power power) {
    Form form = power.form;
    if (form instanceof Var) {
      Var var = (Var)form;
      return new PodoPart(var, power.n);
    }
    if (form instanceof Func) {
      Func func = (Func)form;
      return new PodoPart(func, power.n);
    }
    throw new CannotExtractPodob();
  }
  
  @Override
  public PodoPart visitDiv(Div div) {
    throw new CannotExtractPodob();
  }
  
  @Override
  public PodoPart visitSkob(Skob skob) {
    return skob.form.visit(this);
  }
  
  @Override
  public PodoPart visitMinis(Minis minis) {
    return minis.form.visit(this).minis();
  }
  
  @Override
  public PodoPart visitPlus(Plus plus) {
    throw new CannotExtractPodob();
  }
  
  @Override
  public PodoPart visitMinus(Minus minus) {
    throw new CannotExtractPodob();
  }
  
  @Override
  public PodoPart visitMul(Mul mul) {
    PodoPart left = mul.left.visit(this);
    PodoPart right = mul.right.visit(this);
    return left.mul(right);
  }
  
}
