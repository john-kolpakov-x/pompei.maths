package pompei.maths.end_element;

public class SideRef {
  public final Cell owner;
  public final Side side;
  public final boolean right;

  public SideRef(Cell owner, Side side, boolean right) {
    this.owner = owner;
    this.side = side;
    this.right = right;
  }
}
