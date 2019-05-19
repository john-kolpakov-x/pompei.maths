package pompei.maths.lines_2d.util;

public class NumberUtil {

  public static double notNegative(double value) {
    if (value < 0) {
      throw new IllegalArgumentException(value + " must be positive or zero");
    }
    return value;
  }

}
