package pompei.maths.syms_diff.visitors.podob;

import pompei.maths.syms_diff.model.Const;
import pompei.maths.syms_diff.model.Form;
import pompei.maths.syms_diff.model.FormVisitor;
import pompei.maths.syms_diff.visitable.*;

public class SimilarPartExtractVisitor implements FormVisitor<SimilarPart> {

  @Override
  public SimilarPart visitVar(Var var) {
    return new SimilarPart(var, 1);
  }

  @Override
  public SimilarPart visitFunc(Func func) {
    return new SimilarPart(func, 1);
  }

  @Override
  public SimilarPart visitConst(Const c) {
    return new SimilarPart(c);
  }

  @Override
  public SimilarPart visitDiff(Diff diff) {
    throw new CannotExtractSimilar();
  }

  @Override
  public SimilarPart visitPower(Power power) {
    Form form = power.form;
    if (form instanceof Var) {
      Var var = (Var) form;
      return new SimilarPart(var, power.n);
    }
    if (form instanceof Func) {
      Func func = (Func) form;
      return new SimilarPart(func, power.n);
    }
    throw new CannotExtractSimilar();
  }

  @Override
  public SimilarPart visitDiv(Div div) {
    throw new CannotExtractSimilar();
  }

  @Override
  public SimilarPart visitSkob(Skob skob) {
    return skob.form.visit(this);
  }

  @Override
  public SimilarPart visitMinis(Minis minis) {
    return minis.form.visit(this).minis();
  }

  @Override
  public SimilarPart visitPlus(Plus plus) {
    throw new CannotExtractSimilar();
  }

  @Override
  public SimilarPart visitMinus(Minus minus) {
    throw new CannotExtractSimilar();
  }

  @Override
  public SimilarPart visitMul(Mul mul) {
    SimilarPart left = mul.left.visit(this);
    SimilarPart right = mul.right.visit(this);
    return left.mul(right);
  }

}
