package pompei.maths.lines_2d.model;

public enum Vert {
  TOP, MIDDLE, BOTTOM,
  ;

  public Vert invert() {
    switch (this) {
      case BOTTOM:
        return TOP;
      case TOP:
        return BOTTOM;
      default:
        return this;
    }
  }
}
