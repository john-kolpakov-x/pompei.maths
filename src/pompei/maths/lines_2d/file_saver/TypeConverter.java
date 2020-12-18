package pompei.maths.lines_2d.file_saver;

public class TypeConverter {

  public static String toStr(Object value, Class<?> fieldType) {
    return value == null ? null : value.toString();
  }

  public static Object fromStr(String strValue, Class<?> fieldType) {
    if (strValue == null) {
      return null;
    }

    if (fieldType == Integer.class || fieldType == int.class) {
      return Integer.valueOf(strValue);
    }

    if (fieldType == Boolean.class || fieldType == boolean.class) {
      return Boolean.valueOf(strValue);
    }
    if (fieldType == Long.class || fieldType == long.class) {
      return Long.valueOf(strValue);
    }
    if (fieldType == Double.class || fieldType == double.class) {
      return Double.valueOf(strValue);
    }
    if (fieldType == Float.class || fieldType == float.class) {
      return Float.valueOf(strValue);
    }

    throw new RuntimeException("37HG5TPeb9 :: Cannot convert to type " + fieldType);
  }

}
