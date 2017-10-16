package pompei.maths.utils;

import java.util.Random;

public class RND {

  public static char[] eng = "abcdefghijklmnopqrstuvwxyz".toCharArray();
  public static char[] ENG = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
  public static char[] rus = "абвгдеёжзийклмнопрстуфхцчшщъыьэюя".toCharArray();
  public static char[] RUS = "АБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ".toCharArray();
  public static char[] DIGIT = "0123456789".toCharArray();
  public static char[] HEX_DIGITS = "0123456789abcdef".toCharArray();
  public static char[] ALL = new char[eng.length + ENG.length + rus.length + RUS.length + DIGIT.length];

  static {
    int index = 0;
    for (char[] arr : new char[][]{eng, ENG, rus, RUS, DIGIT}) {
      for (char c : arr) {
        ALL[index++] = c;
      }
    }
  }

  public static final Random RANDOM = new Random();

  public static String str(int len) {
    char ret[] = new char[len];
    final int length = ALL.length;
    for (int i = 0; i < len; i++) {
      ret[i] = ALL[RANDOM.nextInt(length)];
    }
    return new String(ret);
  }

  public static String strInt(int len) {
    char ret[] = new char[len];
    final int length = DIGIT.length;
    for (int i = 0; i < len; i++) {
      ret[i] = DIGIT[RANDOM.nextInt(length)];
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
}
