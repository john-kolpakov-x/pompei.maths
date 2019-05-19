package pompei.maths.lines_2d.util;

import java.awt.Dimension;

public class DimensionSerializer implements ObjectSerializer<Dimension> {
  @Override
  public String toStr(Dimension dimension) {
    return dimension.width + " " + dimension.height;
  }

  @Override
  public Dimension fromStr(String str) {
    String[] split = str.split("\\s+");
    return new Dimension(Integer.parseInt(split[0]), Integer.parseInt(split[1]));
  }
}
