package pompei.maths.crypt;

import java.math.BigInteger;
import java.util.Base64;

public class U {

  public static void ___(int len) {
    char[] c = new char[len];
    for (int i = 0; i < len; i++) {
      c[i] = '-';
    }

    System.out.println(c);
  }

  public static String split(String str, int len) {
    StringBuilder ret = new StringBuilder();

    int strLen = str.length(), from = 0;
    while (from < strLen) {
      int to = from + len;
      if (to > strLen) {
        to = strLen;
      }
      ret.append(str, from, to).append('\n');
      from += len;
    }
    ret.setLength(ret.length() - 1);
    return ret.toString();
  }

  @SuppressWarnings("unused")
  public static String toBase64(BigInteger e) {
    return Base64.getEncoder().encodeToString(e.toByteArray());
  }

  public static String split(String title, String str, int len) {

    final int spaces = title == null ? 0 : title.length();
    final int width = len - spaces;
    final int strLength = str.length();

    String spaceStr = null;

    StringBuilder ret = new StringBuilder(spaces + strLength + 100);
    if (title != null) {
      ret.append(title);
    }
    int to = width;
    if (to > strLength) {
      to = strLength;
    }
    ret.append(str, 0, to);
    while (to < strLength) {
      int from = to;
      to += width;
      if (to > strLength) {
        to = strLength;
      }

      ret.append('\n');
      if (spaceStr == null) {
        char[] spaceChars = new char[spaces];
        for (int i = 0; i < spaces; i++) {
          spaceChars[i] = ' ';
        }
        spaceStr = new String(spaceChars);
      }
      ret.append(spaceStr);
      ret.append(str, from, to);
    }

    return ret.toString();
  }
}
