package pompei.maths.mendeleev_table.elements;

public enum ElementType {
  IRON(Align.LEFT), HALF_IRON(Align.RIGHT), NOT_IRON(Align.RIGHT),

  LANTANOID(Align.RIGHT), ACTINOID(Align.RIGHT);

  public final Align align;

  ElementType(Align align) {this.align = align;}
}
