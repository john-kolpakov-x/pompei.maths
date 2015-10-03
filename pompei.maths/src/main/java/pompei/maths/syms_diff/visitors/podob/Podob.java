package pompei.maths.syms_diff.visitors.podob;

import static java.util.Collections.unmodifiableMap;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import pompei.maths.syms_diff.model.Const;
import pompei.maths.syms_diff.model.Form;
import pompei.maths.syms_diff.visitable.ConstOp;
import pompei.maths.syms_diff.visitable.Plus;

public class Podob {
  private final Map<PodoPart, Const> map;
  
  private Podob(Map<PodoPart, Const> map) {
    this.map = map;
  }
  
  public Podob(PodoPart podoPart) {
    Map<PodoPart, Const> tmp = new HashMap<>();
    tmp.put(podoPart, podoPart.c);
    map = Collections.unmodifiableMap(tmp);
  }
  
  private static final PodoPartExtractVisitor ppev = new PodoPartExtractVisitor();
  
  public static Podob extract(Form form) {
    
    if (form instanceof Plus) {
      Plus plus = (Plus)form;
      
      Podob left = extract(plus.left);
      Podob right = extract(plus.right);
      
      return left.plus(right);
    }
    
    return new Podob(form.visit(ppev));
  }
  
  public Podob plus(Podob a) {
    final Map<PodoPart, Const> all = new HashMap<>();
    all.putAll(map);
    
    for (Entry<PodoPart, Const> e : a.map.entrySet()) {
      PodoPart key = e.getKey();
      Const c = e.getValue();
      
      {
        Const c2 = all.get(key);
        if (c2 != null) {
          c = ConstOp.plus(c, c2);
        }
      }
      
      all.put(key, c);
    }
    
    return new Podob(unmodifiableMap(all));
  }
  
  public Form form() {
    Form ret = null;
    for (Entry<PodoPart, Const> e : map.entrySet()) {
      Form form = e.getKey().form(e.getValue());
      if (ret == null) {
        ret = form;
      } else {
        ret = new Plus(ret, form);
      }
    }
    return ret;
  }
}
