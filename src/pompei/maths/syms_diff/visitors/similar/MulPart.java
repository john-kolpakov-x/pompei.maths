package pompei.maths.syms_diff.visitors.similar;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import pompei.maths.syms_diff.model.Const;
import pompei.maths.syms_diff.model.Form;
import pompei.maths.syms_diff.visitable.*;

public class MulPart implements Comparable<MulPart> {

  public Const arg = null;

  private final List<MulElement> elements = new ArrayList<>();

  private MulPart() {
  }

  public MulPart(MulPart a, Const c) {
    elements.addAll(a.elements);
    arg = ConstOp.plus(a.arg, c);
  }

  public static MulPart from(Form notPlus) {
    if (notPlus instanceof Plus) throw new IllegalArgumentException("notPlus is Plus");

    MulPart ret = new MulPart();
    ret.apply(notPlus);

    return ret.allFormsGood ? ret : null;
  }

  private boolean allFormsGood = true;

  private void apply(Form form) {
    if (form instanceof Minis) throw new IllegalArgumentException("Please kill all Minis first");

    if (form instanceof Mul) {
      Mul mul = (Mul) form;
      apply(mul.left);
      apply(mul.right);
      return;
    }

    {
      MulElement mulElement = MulElement.from(form);

      if (mulElement == null) allFormsGood = false;
      else elements.add(mulElement);
    }
  }

  public void normalize() {
    elements.sort(Comparator.comparing(MulElement::comparing));

    List<MulElement> list = new ArrayList<>();

    {
      MulElement last = null;
      for (MulElement element : elements) {
        if (last == null) last = element;
        else {

          MulElement result = last.mul(element);
          if (result == null) {
            list.add(last);
            last = element;
          } else {
            last = result;
          }

        }
      }
      if (last != null) list.add(last);
    }

    elements.clear();

    for (MulElement e : list) {

      if (e instanceof MulElementConst) {
        if (arg != null) throw new IllegalStateException("MulElementConst not alone");
        arg = ((MulElementConst) e).value;
        continue;
      }

      if (!e.isZeroPower()) elements.add(e);
    }

    if (arg == null) arg = ConstInt.ONE;
  }

  @Override
  public int compareTo(MulPart o) {
    final int size = elements.size(), oSize = o.elements.size();

    for (int i = 0; ; i++) {
      if (i < size && i < oSize) {
        int cmp = elements.get(i).comparing().compareTo(o.elements.get(i).comparing());
        if (cmp != 0) return cmp;
        continue;
      }

      return Integer.compare(size, oSize);
    }
  }

  public MulPart add(MulPart another) {
    if (compareTo(another) != 0) return null;

    return new MulPart(this, another.arg);
  }

  public Form toForm() {
    Form ret = arg.isOne() ? null : arg;
    for (MulElement e : elements) {
      if (ret == null) ret = e.toForm();
      else ret = new Mul(ret, e.toForm());
    }
    return ret == null ? ConstInt.ONE : ret;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("MulPart{");
    sb.append(arg).append('*');
    elements.forEach(sb::append);
    sb.append('}');
    return sb.toString();
  }

  public boolean isZero() {
    return arg.isZero();
  }
}
