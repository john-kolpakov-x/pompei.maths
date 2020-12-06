package pompei.maths.syms_diff.visitors.similar;

import pompei.maths.syms_diff.model.Form;
import pompei.maths.syms_diff.visitable.ConstInt;
import pompei.maths.syms_diff.visitable.Plus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MulPartCollector {

  public final List<Form> leftPart = new ArrayList<>();

  private final List<MulPart> mulParts = new ArrayList<>();

  public void collectFrom(Plus plus) {
    collectPlusArgument(plus.left);
    collectPlusArgument(plus.right);
  }

  private void collectPlusArgument(Form plusArgument) {

    if (plusArgument instanceof Plus) {
      collectFrom((Plus) plusArgument);
      return;
    }

    MulPart mulPart = MulPart.from(plusArgument);
    if (mulPart == null) leftPart.add(plusArgument);
    else mulParts.add(mulPart);
  }

  Form similar = null;
  boolean similarPrepared = false;

  private void prepareSimilar() {
    if (similarPrepared) return;
    similarPrepared = true;

    mulParts.forEach(MulPart::normalize);

    Collections.sort(mulParts);

    List<MulPart> list = new ArrayList<>();
    {
      MulPart last = null;
      for (MulPart mulPart : mulParts) {
        if (last == null) {
          last = mulPart;
        } else {
          MulPart localResult = last.add(mulPart);
          if (localResult == null) {
            list.add(last);
            last = mulPart;
          } else {
            last = localResult;
          }
        }
      }
      if (last != null) list.add(last);
    }

    Form result = null;
    for (MulPart mulPart : list) {
      if (mulPart.isZero()) continue;
      if (result == null) result = mulPart.toForm();
      else result = new Plus(result, mulPart.toForm());
    }

    similar = result == null ? ConstInt.ZERO : result;
  }

  public Form similar() {
    prepareSimilar();
    return similar;
  }
}
