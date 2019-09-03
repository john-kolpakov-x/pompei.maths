package pompei.maths.mendeleev_table.elements;

public enum Align {
  LEFT, RIGHT;

  public Align invert() {
    switch (this) {
      case LEFT:
        return RIGHT;
      case RIGHT:
        return LEFT;
      default:
        throw new RuntimeException("Unknown align = " + this);
    }
  }
}
