package pompei.maths.syms_diff.visitors.podob;

import static java.util.Collections.unmodifiableMap;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import pompei.maths.syms_diff.model.Const;
import pompei.maths.syms_diff.model.Form;
import pompei.maths.syms_diff.visitable.ConstInt;
import pompei.maths.syms_diff.visitable.ConstOp;
import pompei.maths.syms_diff.visitable.Func;
import pompei.maths.syms_diff.visitable.Mul;
import pompei.maths.syms_diff.visitable.Power;
import pompei.maths.syms_diff.visitable.Var;

public class PodoPart {
  public final Const c;
  
  private final Map<PodoUnit, Integer> map;
  
  public PodoPart(Var var, int pow) {
    if (pow == 0) throw new IllegalArgumentException("pow = 0");
    
    c = ConstInt.ONE;
    
    HashMap<PodoUnit, Integer> tmp = new HashMap<>();
    tmp.put(new PodoUnitVar(var.name), pow);
    map = unmodifiableMap(tmp);
  }
  
  public PodoPart(Func func, int pow) {
    if (pow == 0) throw new IllegalArgumentException("pow = 0");
    c = ConstInt.ONE;
    HashMap<PodoUnit, Integer> tmp = new HashMap<>();
    tmp.put(new PodoUnitFunc(func.name, func.n), pow);
    map = unmodifiableMap(tmp);
  }
  
  @SuppressWarnings("unchecked")
  public PodoPart(Const c) {
    this.c = c;
    map = Collections.EMPTY_MAP;
  }
  
  private PodoPart(Map<PodoUnit, Integer> map, Const c) {
    this.map = map;
    this.c = c;
  }
  
  private ArrayList<PodoUnit> sortedUnits() {
    ArrayList<PodoUnit> units = new ArrayList<>();
    units.addAll(map.keySet());
    Collections.sort(units);
    return units;
  }
  
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append(c.asStr());
    
    for (PodoUnit unit : sortedUnits()) {
      sb.append(unit).append(map.get(unit));
    }
    return sb.toString();
  }
  
  @Override
  public int hashCode() {
    return map.hashCode();
  }
  
  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null) return false;
    if (getClass() != obj.getClass()) return false;
    return map.equals(((PodoPart)obj).map);
  }
  
  public PodoPart minis() {
    return new PodoPart(map, c.minis());
  }
  
  public PodoPart mul(PodoPart a) {
    Const mul = ConstOp.mul(c, a.c);
    if (mul.sign() == 0) return new PodoPart(mul);
    
    Map<PodoUnit, Integer> all = new HashMap<>();
    all.putAll(map);
    
    for (Entry<PodoUnit, Integer> e : a.map.entrySet()) {
      PodoUnit unit = e.getKey();
      int pow = e.getValue();
      
      {
        Integer pow2 = all.get(unit);
        if (pow2 != null) pow += pow2;
      }
      
      if (pow == 0) {
        all.remove(unit);
      } else {
        all.put(unit, pow);
      }
    }
    
    return new PodoPart(all, mul);
  }
  
  public Form form(Const value) {
    Form ret = value.isOne() ? null : value;
    for (PodoUnit unit : sortedUnits()) {
      if (ret == null) {
        ret = formUnit(unit);
      } else {
        ret = new Mul(ret, formUnit(unit));
      }
    }
    return ret;
  }
  
  private Form formUnit(PodoUnit unit) {
    int pow = map.get(unit);
    if (pow == 1) return unit.form();
    return new Power(pow, unit.form());
  }
}
