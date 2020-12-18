package pompei.maths.lines_2d.file_saver;

public interface FieldAcceptor {

  String read();

  void write(String strValue);

  String name();

}
