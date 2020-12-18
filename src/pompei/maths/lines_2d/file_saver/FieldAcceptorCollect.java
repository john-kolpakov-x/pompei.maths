package pompei.maths.lines_2d.file_saver;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class FieldAcceptorCollect {

  public static List<FieldAcceptor> collect(Object object) {
    List<FieldAcceptor> ret = new ArrayList<>();

    for (Field field : object.getClass().getFields()) {
      if (field.getAnnotation(Savable.class) == null) {
        continue;
      }

      ret.add(new FieldAcceptor() {
        @Override
        public String read() {
          try {
            return TypeConverter.toStr(field.get(object), field.getType());
          } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
          }
        }

        @Override
        public void write(String strValue) {
          try {
            field.set(object, TypeConverter.fromStr(strValue, field.getType()));
          } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
          }
        }

        @Override
        public String name() {
          return field.getName();
        }
      });
    }

    return ret;
  }

}
