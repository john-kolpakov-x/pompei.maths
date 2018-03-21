package pompei.maths.utils;

import java.util.Random;

public class RND {

  public static String eng = "abcdefghijklmnopqrstuvwxyz";
  public static String ENG = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
  public static String rus = "абвгдеёжзийклмнопрстуфхцчшщъыьэюя";
  public static String RUS = "АБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ";
  public static String DIGIT = "0123456789";
  public static char[] DIGIT_CHARS = DIGIT.toCharArray();
  public static char[] HEX_DIGITS = "0123456789abcdef".toCharArray();
  public static char[] ALL_CHARS = (eng + ENG + rus + RUS + DIGIT).toCharArray();

  public static final Random RANDOM = new Random();

  public static String str(int len) {
    char ret[] = new char[len];
    final int length = ALL_CHARS.length;
    for (int i = 0; i < len; i++) {
      ret[i] = ALL_CHARS[RANDOM.nextInt(length)];
    }
    return new String(ret);
  }

  public static String strInt(int len) {
    char ret[] = new char[len];
    final int length = DIGIT_CHARS.length;
    for (int i = 0; i < len; i++) {
      ret[i] = DIGIT_CHARS[RANDOM.nextInt(length)];
    }
    return new String(ret);
  }

  public static String strHex(int len) {
    char ret[] = new char[len];
    final int length = HEX_DIGITS.length;
    for (int i = 0; i < len; i++) {
      ret[i] = HEX_DIGITS[RANDOM.nextInt(length)];
    }
    return new String(ret);
  }

  public static int plusInt(int bound) {
    return RANDOM.nextInt(bound);
  }

  public static boolean bool() {
    return RANDOM.nextBoolean();
  }
}
