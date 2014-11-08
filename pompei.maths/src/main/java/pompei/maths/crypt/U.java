package pompei.maths.crypt;

import java.math.BigInteger;

import javax.xml.bind.DatatypeConverter;

public class U {
  
  public static void ___(int len) {
    char c[] = new char[len];
    for (int i = 0; i < len; i++) {
      c[i] = '-';
    }
    
    System.out.println(c);
  }
  
  public static String splt(String str, int len) {
    StringBuilder ret = new StringBuilder();
    
    int strLen = str.length(), from = 0;
    while (from < strLen) {
      int to = from + len;
      if (to > strLen) to = strLen;
      ret.append(str.substring(from, to)).append('\n');
      from += len;
    }
    ret.setLength(ret.length() - 1);
    return ret.toString();
  }
  
  public static String toBase64(BigInteger e) {
    return DatatypeConverter.printBase64Binary(e.toByteArray());
  }
  
}
