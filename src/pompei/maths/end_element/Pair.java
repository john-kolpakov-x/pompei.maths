package pompei.maths.end_element;

public class Pair<T> {
  public final T a;
  public final T b;

  public Pair(T a, T b) {
    this.a = a;
    this.b = b;
  }

  public static <T> Pair<T> with(T a, T b) {return new Pair<>(a, b);}
}
