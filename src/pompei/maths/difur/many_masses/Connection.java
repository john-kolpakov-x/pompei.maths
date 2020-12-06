package pompei.maths.difur.many_masses;

import java.awt.Graphics2D;

import static org.fest.assertions.Assertions.assertThat;

public abstract class Connection {
  public final String id;
  public final Node from, to;

  public Connection(String id, Node from, Node to) {
    this.id = id;
    assertThat(from).isNotNull();
    assertThat(to).isNotNull();
    this.from = from;
    this.to = to;
  }

  public abstract String asStr();

  public abstract void draw(Graphics2D g);

  public abstract Vec2d getFromForse();

  public abstract Vec2d getToForse();
}
