package pompei.maths.difur.many_masses;

import java.awt.Graphics2D;
import java.util.HashSet;
import java.util.Set;

public abstract class Node {
  public final String id;
  final Set<Connection> fromSet = new HashSet<>(), toSet = new HashSet<>();
  
  public Node(String id) {
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
    Node other = (Node)obj;
    if (id == null) {
      if (other.id != null) return false;
    } else if (!id.equals(other.id)) return false;
    return true;
  }
  
  public abstract String asStr();
  
  public abstract void draw(Graphics2D g);
  
  public abstract int centerX();
  
  public abstract int centerY();
  
  public Vec2d getForse() {
    Vec2d ret = new Vec2d(0, 0);
    
    for (Connection s : toSet) {
      ret = ret.plus(s.getToForse());
    }
    for (Connection s : fromSet) {
      ret = ret.minus(s.getFromForse());
    }
    
    return ret;
  }
}
