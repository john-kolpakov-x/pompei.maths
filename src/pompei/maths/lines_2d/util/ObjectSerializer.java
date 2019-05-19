package pompei.maths.lines_2d.util;

public interface ObjectSerializer<T> {

  String toStr(T t);

  T fromStr(String str);

}
