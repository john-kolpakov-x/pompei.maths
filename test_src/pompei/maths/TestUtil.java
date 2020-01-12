package pompei.maths;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Random;

public class TestUtil {

  @SuppressWarnings("SpellCheckingInspection")
  private static final String ENG = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
  private static final String DEG = "0123456789";
  private static final String RUS = "АБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ";
  private static final String CHIN = "⻨⻩⻪⻫⻬⻭⻮⻯⻰⻱⻲⻳⻠⻡⻢⻣⻤⻥⻦⻧⾀⾁⾂⾃⾄⾅⾆⾇⾈⾉⾊⾋⾌⾍⾎⾏⾐⾑⾒⾓⾔⾕⾖⾗⾘" +
    "⾙⾚⾛⾜⾝⾞⾟⾠⾡⾢⾣⾤⾥⾦⾧⾨⾩⾪⾫⾬⾭⾮⾯⾰⾱⾲⾳⾴⾵⾶⾷⾸⾹⾺⾻⾼⾽⾾⾿⿀⿁⿂⿃⿄⿅⿆⿇⿈⿉⿊⿋⿌⿍⿎⿏⿐⿑⿒⿓⿔⿕";

  private static final char[] ALL = (ENG.toUpperCase() + ENG.toLowerCase() + DEG + RUS.toLowerCase()
    + RUS.toUpperCase() + CHIN).toCharArray();

  public static String rndStr(Random rnd, int length) {
    char[] chars = new char[length];
    for (int i = 0; i < length; i++) {
      chars[i] = ALL[rnd.nextInt(ALL.length)];
    }
    return new String(chars);
  }

  public static BigDecimal rndBd(Random rnd) {
    byte[] intBytes = new byte[30 + rnd.nextInt(30)];
    rnd.nextBytes(intBytes);

    BigInteger bi = new BigInteger(intBytes);
    int scale = rnd.nextInt();
    return new BigDecimal(bi, scale);
  }

}
