package pompei.maths.diofant;

import pompei.maths.bdmath.Rational;

public class DiofantExample {

  public static void main(String[] args) {
    var diofant = new Diofant(Rational.parse("-1"), Rational.parse("1"), Dot.xy("1", "1"));
    System.out.println("UZ486673X1 :: diofant = " + diofant);

    var P2 = diofant.plus(diofant.P, diofant.P);
    var P4 = diofant.plus(P2, P2);
    var P8 = diofant.plus(P4, P4);
    var P16 = diofant.plus(P8, P8);
    var P32 = diofant.plus(P16, P16);
    var P64 = diofant.plus(P32, P32);

    System.out.println("d7xmR5Pl45 :: P  1 = " + diofant.P);
    System.out.println("d7xmR5Pl45 :: P  2 = " + P2);
    System.out.println("d7xmR5Pl45 :: P  4 = " + P4);
    System.out.println("d7xmR5Pl45 :: P  8 = " + P8);
    System.out.println("d7xmR5Pl45 :: P 16 = " + P16);
    System.out.println("d7xmR5Pl45 :: P 32 = " + P32);
    System.out.println("d7xmR5Pl45 :: P 64 = " + P64.toBase64());

  }

}
