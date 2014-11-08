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
  
  /**
   * Расширенный алгоритм Евклида <br/>
   * d = НОД(a,b), ax + by = d
   * 
   * @param a
   *          входной параметр
   * @param b
   *          входной параметр
   * @return [d, x, y]
   */
  public static BigInteger[] gcd(BigInteger a, BigInteger b) {
    if (a == null) a = BigInteger.ZERO;
    if (b == null) b = BigInteger.ZERO;
    if (a.compareTo(b) < 0) return gcd(b, a);
    BigInteger[] ret = new BigInteger[3];
    
    if (b.compareTo(BigInteger.ZERO) == 0) {
      ret[0] = a;
      ret[1] = BigInteger.ONE;
      ret[2] = BigInteger.ZERO;
      return ret;
    }
    
    BigInteger x2 = BigInteger.ONE;
    BigInteger x1 = BigInteger.ZERO;
    
    BigInteger y2 = BigInteger.ZERO;
    BigInteger y1 = BigInteger.ONE;
    
    while (b.compareTo(BigInteger.ZERO) > 0) {
      BigInteger q = a.divide(b);
      
      BigInteger r = a.subtract(q.multiply(b));
      
      BigInteger x = x2.subtract(q.multiply(x1));
      BigInteger y = y2.subtract(q.multiply(y1));
      
      a = b;
      b = r;
      x2 = x1;
      x1 = x;
      y2 = y1;
      y1 = y;
    }
    
    ret[0] = a;
    ret[1] = x2;
    ret[2] = y2;
    
    return ret;
  }
  
}
