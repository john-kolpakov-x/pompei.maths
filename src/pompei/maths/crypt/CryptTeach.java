package pompei.maths.crypt;

import pompei.maths.syms.visitors.math.UtilMath;

import java.math.BigInteger;

import static pompei.maths.crypt.U.___;

public class CryptTeach {
  public static void main(String[] args) {

    //SecureRandom rnd = new SecureRandom();

    //    BigInteger p1 = BigInteger.probablePrime(1024 * 4, rnd);
    //    BigInteger p2 = BigInteger.probablePrime(1024 * 4, rnd);

    BigInteger p1 = new BigInteger("89602727487546192758782416414288740612489625465538273429678308"
                                       + "379687666143525494212736675814928905229498980447642314967151385405258590038909949519650"
                                       + "771551721531099845765377798587191803501484058472955702756611478283911656982929913039593"
                                       + "809735273959771035680544752381900246691902371355410153266600225369000508475489116583414"
                                       + "593288680768494004143842235217964442651732869422980437466168421504050261291061164026236"
                                       + "246672028686820660767943582046292658767623748370996087524070554734481562054568380076078"
                                       + "105990306418944428880317616925624217896254021279481705558131533214874036922509465996121"
                                       + "351925124764658669121430478727567426245726258108936447289854305266056004843714986185326"
                                       + "143823033059721058125638761969740876253855017164707689691848803658589552810959257229854"
                                       + "390015528661320903660017478161644740579527615882310998238584928398184053842095247268375"
                                       + "646040016860773364015275402149444422542382365208393793576701356593081720760076759221592"
                                       + "838254456872189437491214491154348570279705587576787930347464593682517234809363345472933"
                                       + "839736897629800071415729817497306936273238993523649758326929505212057141854070305584675"
                                       + "518301265734392913249728197481785078766446899598443348533563521946953775059488969375181"
                                       + "9101371628853638054353936281531457633183");

    BigInteger p2 = new BigInteger("81574071632470721788783488436488586592891743525028833937071991"
                                       + "466455261914220743810810009935090546810703342549922134270139797992940527490688695427788"
                                       + "049856483320333170517710895225269888676834234693575922176800208039815810557641119256474"
                                       + "479228273063930142637316983043428052501179292426012264566265415883137962663029647193785"
                                       + "717034225376899735100158497352221783664934255252110102125333923816049295704127890322710"
                                       + "836282218576724042248836758996017827552901935418526572645494299276703808887088116564571"
                                       + "488332334336961856837935138666303587019427675012430458053507382873713520871124320886358"
                                       + "452222079876413482799471650667589894041668451609872358815641902603167724319982710515291"
                                       + "911705172613989419418950937125142124542160442623204019106279253038649987350639893391852"
                                       + "310367788359289989376282023728975409861206337432807513829809652274367342944318416855995"
                                       + "807600896419312467308130987904627083041267898634111925632182464164392592647877439507566"
                                       + "499183922886773028787961989120402657684246955221464666072708406286347505330100235090463"
                                       + "828395417986522943330713052866240446300152135256383381513515798589791126110579073016312"
                                       + "325156035667952240747731848391742451882586801570184750335072191304106344975460473931216"
                                       + "2554662435976139193055633808534393396713");

    int len = 130;

    ___(len);
    System.out.println(U.split("p1 = " + p1, len));
    ___(len);
    System.out.println(U.split("p2 = " + p2, len));

    BigInteger n = p1.multiply(p2);

    ___(len);
    System.out.println(U.split("power = " + n, len));

    BigInteger p1m = p1.subtract(BigInteger.ONE);
    BigInteger p2m = p2.subtract(BigInteger.ONE);
    BigInteger fi = p1m.multiply(p2m);
    ___(len);
    System.out.println(U.split("fi = " + fi, len));

    BigInteger e = new BigInteger("6700417");
    BigInteger d = UtilMath.gcdExt(fi, e)[2];
    ___(len);
    System.out.println(U.split("e = " + e, len));
    System.out.println(U.split("d = " + d, len));
    ___(len);

    System.out.println("e*d(mod fi) = " + e.multiply(d).remainder(fi));

//    BigInteger m1 = new BigInteger("5465443254354576658768563643254325467537542365426435326543654");
    BigInteger m1 = new BigInteger("" +
                                       "5465267831285430000654706507080760540320605705675644325435457665876856364325432546753754236426435326543654" +
                                       "5146938724623408903483947287313000040324035046054654365054930493029054930569543095205490000000000542304302" +
                                       "5465267831285430000654706507080760540320605705675644325435457665876856364325432546753754236426435326543654" +
                                       "5146938724623408903483947287313000040324035046054654365054930493029054930569543095205490000100000542304302" +
                                       "5465267831285430000654706507080760540320605705675644325435457665876856364325432546753754236546435326543654" +
                                       "5146938724623408903483947287313000040324035046054654365054930493029054930569543095205490000020000542304302" +
                                       "5465267831285430000654706507080760540320605705675644325435457665876856364325432546753754236426435326543654" +
                                       "5146938724623408903483947287313000040324035046054654365054930493029054930569543095205490000000000542304302" +
                                       "5465267831285430000654706507080957540320605705675644325435457665876856364325432546753754236426435326543654" +
                                       "5146938724623408903483947287310957040324035046054654365054930493029054930569543095205490000100000542304302" +
                                       "5465267831285430000654706507080957540320605705675644325435457665876856364325432546753754236546435326543654" +
                                       "5146938724623408903483947287313000040324035046054654365054930493029054930569543095205490000020000542304302" +
                                       "5465267831285430000654706507080760540320605705675644325435457665876856364325432546753754236426435326543654" +
                                       "5146938724623408903483947287313000040324035046054654365054930493029054930569543095205490000000000542304302" +
                                       "5465267831285430000654706507080957540320605705675644325435457665876856364325432546753754236426435326543654" +
                                       "5146938724623408903483947287310957040324035046054654365054930493029054930569543095205490000100000542304302" +
                                       "5465267831285430000654706507080957540320605705675644325435457665876856364325432546753754236546435326543654" +
                                       "5146938724623408903483947287310957040324035046054654365054930493029054930569543095205490000020000542304302" +
                                       "5465267831285430000654706507080957540320605705675644325435457665876856364325432546753754236426435326543654" +
                                       "5146938724623408903483947287310957040324035046054654365054930493029054930569543095205490000000000542304302" +
                                       "5465267831285430000654706507080957540320605705675644325435457665876856364325432546753754236426435326543654" +
                                       "5146938724623408903483947287310957040324035046054654365054930493029054930569543095205490000100000542304302" +
                                       "5465267831285430000654706507080957540320605705675644325435457665876856364325432546753754236546435326543654" +
                                       "543654768111223334455567613"
    );

    BigInteger cr = m1.modPow(e, n);
    BigInteger m2 = cr.modPow(d, n);

    ___(len);
    System.out.println(U.split("m1 = ", "" + m1, len));
    System.out.println();
    System.out.println(U.split("cr = ", "" + cr, len));
    System.out.println();
    System.out.println(U.split("m2 = ", "" + m2, len));
    System.out.println();
    System.out.println(" e.bitCount = " + e.bitCount());
    System.out.println(" d.bitCount = " + d.bitCount());
    System.out.println(" n.bitCount = " + n.bitCount());
    System.out.println();
    System.out.println("m1.bitCount = " + m1.bitCount() + " = " + (m1.bitCount() / 8) + " * 8");
    System.out.println("cr.bitCount = " + cr.bitCount() + " = " + (cr.bitCount() / 8) + " * 8");
    System.out.println("m2.bitCount = " + m2.bitCount() + " = " + (m2.bitCount() / 8) + " * 8");
    System.out.println("m1 <=> m2 = " + m1.compareTo(m2));

    String greetgo = "greetgo!";
    System.out.println(greetgo);

  }

}
