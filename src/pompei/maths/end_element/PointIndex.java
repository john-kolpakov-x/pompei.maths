package pompei.maths.end_element;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class PointIndex {
  private final double kx;
  private final double ky;

  private final Map<Pin, Set<Point>> map = new HashMap<>();

  private static class Pin {
    long x, y;

    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      Pin pin = (Pin) o;
      return x == pin.x && y == pin.y;
    }

    @Override
    public int hashCode() {
      return Objects.hash(x, y);
    }
  }

  private static class Point {
    final double x, y;
    final int index;

    public Point(double x, double y, int index) {
      this.x = x;
      this.y = y;
      this.index = index;
    }

    public double distTo(double x, double y) {
      double dx = this.x - x;
      double dy = this.y - y;
      dx *= dx;
      dy *= dy;
      return dx + dy;
    }
  }

  public PointIndex(double kx, double ky) {
    this.kx = kx;
    this.ky = ky;
  }

  public void addIndex(double x, double y, int index) {
    Pin pin = new Pin();
    pin.x = Math.round(kx * x);
    pin.y = Math.round(ky * y);
    map.computeIfAbsent(pin, k -> new HashSet<>()).add(new Point(x, y, index));
  }

  public int getNearestIndex(double x, double y) {
    if (map.size() == 0) throw new RuntimeException("No elements");
    Pin pin = new Pin();
    pin.x = Math.round(kx * x);
    pin.y = Math.round(ky * y);
    {
      Point point = findNearest(map.get(pin), x, y);
      if (point != null) return point.index;
    }

    long X = pin.x, Y = pin.y;

    for (int r = 1; ; r++) {
      pin.y = Y - r;
      pin.x = X - r;
      int n = 2 * r + 1;

      for (int i = 0; i < n; i++) {
        System.out.println("FIND HERE " + pin.x + ", " + pin.y);
        Point point = findNearest(map.get(pin), x, y);
        if (point != null) return point.index;
        pin.x++;
      }
      for (int i = 0; i < n; i++) {
        System.out.println("FIND HERE " + pin.x + ", " + pin.y);
        Point point = findNearest(map.get(pin), x, y);
        if (point != null) return point.index;
        pin.y++;
      }
      for (int i = 0; i < n; i++) {
        System.out.println("FIND HERE " + pin.x + ", " + pin.y);
        Point point = findNearest(map.get(pin), x, y);
        if (point != null) return point.index;
        pin.x--;
      }
      for (int i = 0; i < n; i++) {
        System.out.println("FIND HERE " + pin.x + ", " + pin.y);
        Point point = findNearest(map.get(pin), x, y);
        if (point != null) return point.index;
        pin.y--;
      }

    }
  }

  private Point findNearest(Set<Point> points, double x, double y) {
    if (points == null) return null;
    Point ret = null;
    double dist = 0;
    for (Point point : points) {
      if (ret == null) {
        ret = point;
        dist = point.distTo(x, y);
      } else {
        double newDist = point.distTo(x, y);
        if (newDist < dist) {
          ret = point;
          dist = newDist;
        }
      }
    }
    return ret;
  }

}
