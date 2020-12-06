package pompei.maths.distribution;

import java.io.PrintStream;
import java.util.concurrent.atomic.AtomicInteger;

public class Desk {
  private final AtomicInteger[] values;
  private final double x1, x2;
  private final AtomicInteger totalCount = new AtomicInteger(0);

  public Desk(int valueCount, double x1, double x2) {
    this.x1 = x1;
    this.x2 = x2;
    values = new AtomicInteger[valueCount];
    for (int i = 0; i < valueCount; i++) {
      values[i] = new AtomicInteger(0);
    }
  }

  public void put(double x) {
    if (x < x1) {
      return;
    }
    if (x >= x2) {
      return;
    }
    int i = position(x);
    values[i].incrementAndGet();
    totalCount.incrementAndGet();
  }

  private int position(double x) {
    return (int) Math.floor((x - x1) / (x2 - x1) * (double) values.length);
  }

  public double get(double x) {
    if (x < x1) {
      return 0;
    }
    if (x > x2) {
      return 0;
    }
    if (totalCount.get() == 0) {
      return 0;
    }
    return getInPosition(position(x));
  }

  private double getInPosition(int position) {
    return (double) values[position].get() / (x2 - x1) / (double) totalCount.get() * (double) values.length;
  }

  public void printTo(PrintStream pr) {
    for (int i = 0, c = values.length; i < c; i++) {
      double x = (double) i / (double) c * (x2 - x1) + x1;
      double y = getInPosition(i);

      pr.println((x + " " + y));
    }
  }
}
