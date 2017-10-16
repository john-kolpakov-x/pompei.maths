package scripts;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.math.BigInteger;
import java.util.Arrays;

public class EulerCalc {
  public static String calc(BigInteger r, BigInteger a, BigInteger b, BigInteger c) {

    BigInteger a4 = a.multiply(a).multiply(a).multiply(a);

    BigInteger b4 = b.multiply(b).multiply(b).multiply(b);

    BigInteger c4 = c.multiply(c).multiply(c).multiply(c);

    BigInteger acb4 = a4.add(b4).add(c4);

    BigInteger r4 = r.multiply(r).multiply(r).multiply(r);

    return acb4.subtract(r4) + " | " + acb4 + " | " + r4;

  }

  public static String toStr(InputStream inputStream) throws Exception {
    try {

      ByteArrayOutputStream out = new ByteArrayOutputStream();

      byte[] buffer = new byte[8 * 1024];
      while (true) {
        int count = inputStream.read(buffer);
        if (count < 0) return out.toString("UTF-8");
        out.write(buffer, 0, count);
      }

    } finally {
      inputStream.close();
    }
  }

  public static void main(String[] args) throws Exception {
    String str = toStr(EulerCalc.class.getResourceAsStream("/EulerData.txt"));
    Arrays.stream(str.split("\n")).forEachOrdered(s -> {
      String[] line = s.split("\t");

      BigInteger r = new BigInteger(line[1]);
      BigInteger a = new BigInteger(line[2]);
      BigInteger b = new BigInteger(line[3]);
      BigInteger c = new BigInteger(line[4]);

      System.out.println(
        line[1] + "^4 = " +
          line[2] + "^4 + " +
          line[3] + "^4 + " +
          line[4] + "^4 ~~~ " + calc(r, a, b, c));
    });

  }
}
