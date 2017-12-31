package pompei.maths.end_element;

import java.util.ArrayList;
import java.util.List;

public class Cell {
  public final double x1, y1, x2, y2;

  public int p1, p2, p3, p4;

  public final List<SideRef> sides = new ArrayList<>();

  public Cell(double x1, double y1, double x2, double y2) {
    this.x1 = x1;
    this.y1 = y1;
    this.x2 = x2;
    this.y2 = y2;
  }
}
