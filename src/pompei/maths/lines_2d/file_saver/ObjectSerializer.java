package pompei.maths.lines_2d.file_saver;

public interface ObjectSerializer<T> {

  String toStr(T t);

  T fromStr(String str);

}
