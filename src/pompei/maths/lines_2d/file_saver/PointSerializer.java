package pompei.maths.lines_2d.file_saver;

import java.awt.Point;

public class PointSerializer implements ObjectSerializer<Point> {
  @Override
  public String toStr(Point point) {
    return point.x + " " + point.y;
  }

  @Override
  public Point fromStr(String str) {
    String[] split = str.split("\\s+");
    return new Point(Integer.parseInt(split[0]), Integer.parseInt(split[1]));
  }
}
