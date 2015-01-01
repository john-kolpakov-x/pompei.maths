package pompei.maths.difur.many_masses;

import java.awt.Graphics2D;
import java.util.HashSet;
import java.util.Set;

public abstract class Uzel {
  public final String id;
  final Set<Svjaz> fromSet = new HashSet<>(), toSet = new HashSet<>();
  
  public Uzel(String id) {
    this.id = id;
  }
  
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    return result;
  }
  
  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null) return false;
    if (getClass() != obj.getClass()) return false;
    Uzel other = (Uzel)obj;
    if (id == null) {
      if (other.id != null) return false;
    } else if (!id.equals(other.id)) return false;
    return true;
  }
  
  public abstract String asStr();
  
  public abstract void draw(Graphics2D g);
  
  public abstract int centerX();
  
  public abstract int centerY();
}
