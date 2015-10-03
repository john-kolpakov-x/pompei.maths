package pompei.maths.syms_diff.visitors.podob;

import pompei.maths.syms_diff.model.Form;
import pompei.maths.syms_diff.visitable.Func;

public class PodoUnitFunc extends PodoUnit {
  public final String name;
  public final int n;
  
  public PodoUnitFunc(String name, int n) {
    this.name = name;
    this.n = n;
  }
  
  @Override
  public Form form() {
    return new Func(name, n);
  }
  
  @Override
  public String toString() {
    return "{" + name + "." + n + "}";
  }
  
  @Override
  public int hashCode() {
    final int prime = 131;
    int result = 1;
    result = prime * result + n;
    result = prime * result + ((name == null) ? 0 : name.hashCode());
    return result;
  }
  
  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null) return false;
    if (getClass() != obj.getClass()) return false;
    PodoUnitFunc other = (PodoUnitFunc)obj;
    if (n != other.n) return false;
    if (name == null) {
      if (other.name != null) return false;
    } else if (!name.equals(other.name)) return false;
    return true;
  }
  
  @Override
  public int compareTo(PodoUnit o) {
    if (equals(o)) return 0;
    if (o instanceof PodoUnitVar) return 1;
    if (o instanceof PodoUnitFunc) {
      PodoUnitFunc func = (PodoUnitFunc)o;
      int cmp = name.compareTo(func.name);
      if (cmp != 0) return cmp;
      return n - func.n;
    }
    
    throw new IllegalArgumentException(o.getClass() + ":" + o);
  }
}
