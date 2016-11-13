package pompei.maths.syms_diff.visitors.podob;

import pompei.maths.syms_diff.model.Const;
import pompei.maths.syms_diff.model.Form;
import pompei.maths.syms_diff.visitable.ConstOp;
import pompei.maths.syms_diff.visitable.Plus;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import static java.util.Collections.unmodifiableMap;

public class Similar {
  private final Map<SimilarPart, Const> map;

  private Similar(Map<SimilarPart, Const> map) {
    this.map = map;
  }

  public Similar(SimilarPart similarPart) {
    Map<SimilarPart, Const> tmp = new HashMap<>();
    tmp.put(similarPart, similarPart.c);
    map = Collections.unmodifiableMap(tmp);
  }

  private static final SimilarPartExtractVisitor spe = new SimilarPartExtractVisitor();

  public static Similar extract(Form form) {

    if (form instanceof Plus) {
      Plus plus = (Plus) form;

      Similar left = extract(plus.left);
      Similar right = extract(plus.right);

      return left.plus(right);
    }

    return new Similar(form.visit(spe));
  }
  
  public Similar plus(Similar a) {
    final Map<SimilarPart, Const> all = new HashMap<>();
    all.putAll(map);

    for (Entry<SimilarPart, Const> e : a.map.entrySet()) {
      SimilarPart key = e.getKey();
      Const c = e.getValue();

      {
        Const c2 = all.get(key);
        if (c2 != null) {
          c = ConstOp.plus(c, c2);
        }
      }

      all.put(key, c);
    }

    return new Similar(unmodifiableMap(all));
  }

  public Form form() {
    Form ret = null;
    for (Entry<SimilarPart, Const> e : map.entrySet()) {
      Form form = e.getKey().form(e.getValue());
      if (ret == null) {
        ret = form;
      } else {
        if (form instanceof Const) {
          Const c = (Const) form;
          if (c.sign() != 0) {
            ret = new Plus(ret, form);
          }
        } else {
          ret = new Plus(ret, form);
        }
      }
    }
    return ret;
  }
}
