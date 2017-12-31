package pompei.maths.end_element;

import java.util.Objects;

public class IntPair {
  public final int a;
  public final int b;

  public IntPair(int a, int b) {
    this.a = a;
    this.b = b;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    IntPair intPair = (IntPair) o;
    return a == intPair.a &&
        b == intPair.b;
  }

  @Override
  public int hashCode() {

    return Objects.hash(a, b);
  }
}
