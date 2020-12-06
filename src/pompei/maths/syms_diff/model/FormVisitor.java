package pompei.maths.syms_diff.model;

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

public interface FormVisitor<T> {

  T visitVar(Var var);

  T visitFunc(Func func);

  T visitConst(Const c);

  T visitDiff(Diff diff);

  T visitPower(Power power);

  T visitDiv(Div div);

  T visitSkob(Skob skob);

  T visitMinis(Minis minis);

  T visitPlus(Plus plus);

  T visitMinus(Minus minus);

  T visitMul(Mul mul);
}
