package pompei.maths.syms_diff.visitors.podob;

import pompei.maths.syms_diff.model.Form;
import pompei.maths.syms_diff.visitable.Var;

public class PodoUnitVar extends PodoUnit {
  public final String name;
  
  public PodoUnitVar(String name) {
    this.name = name;
  }
  
  @Override
  public Form form() {
    return new Var(name);
  }
  
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((name == null) ? 0 : name.hashCode());
    return result;
  }
  
  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null) return false;
    if (getClass() != obj.getClass()) return false;
    PodoUnitVar other = (PodoUnitVar)obj;
    if (name == null) {
      if (other.name != null) return false;
    } else if (!name.equals(other.name)) return false;
    return true;
  }
  
  @Override
  public String toString() {
    return "[" + name + "]";
  }
  
  @Override
  public int compareTo(PodoUnit o) {
    if (equals(o)) return 0;
    if (o instanceof PodoUnitFunc) return -1;
    if (o instanceof PodoUnitVar) {
      PodoUnitVar var = (PodoUnitVar)o;
      return name.compareTo(var.name);
    }
    
    throw new IllegalArgumentException(o.getClass() + ":" + o);
  }
}
