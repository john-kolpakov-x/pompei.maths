package pompei.maths.collada.core;

import pompei.maths.utils.Conv;

import java.util.stream.Collectors;
import java.util.stream.DoubleStream;

public class Matrix4 {
  public double m11 = 1, m12 = 0, m13 = 0, m14 = 0;
  public double m21 = 0, m22 = 1, m23 = 0, m24 = 0;
  public double m31 = 0, m32 = 0, m33 = 1, m34 = 0;
  public double m41 = 0, m42 = 0, m43 = 0, m44 = 1;

  public DoubleStream valuesStream() {
    return DoubleStream.of(
        m11, m12, m13, m14,
        m21, m22, m23, m24,
        m31, m32, m33, m34,
        m41, m42, m43, m44
                          );
  }

  public String spacedCoord() {
    return valuesStream().mapToObj(Conv::doubleToStr).collect(Collectors.joining(" "));
  }
}
