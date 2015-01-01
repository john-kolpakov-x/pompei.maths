package pompei.maths.difur.many_masses;

import static org.fest.assertions.Assertions.assertThat;

import java.awt.Graphics2D;

public abstract class Svjaz {
  public final String id;
  public final Uzel from, to;
  
  public Svjaz(String id, Uzel from, Uzel to) {
    this.id = id;
    assertThat(from).isNotNull();
    assertThat(to).isNotNull();
    this.from = from;
    this.to = to;
  }
  
  public abstract String asStr();
  
  public abstract void draw(Graphics2D g);
}
