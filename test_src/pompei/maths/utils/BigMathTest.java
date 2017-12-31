package pompei.maths.utils;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static org.fest.assertions.Assertions.assertThat;

public class BigMathTest {

  public static BigDecimal E = new BigDecimal(
    "2.71828182845904523536028747135266249775724709369995957496696762772407663035354" +
      "759457138217852516642742746639193200305992181741359662904357290033429526059563" +
      "073813232862794349076323382988075319525101901157383418793070215408914993488416" +
      "750924476146066808226480016847741185374234544243710753907774499206955170276183" +
      "860626133138458300075204493382656029760673711320070932870912744374704723069697" +
      "720931014169283681902551510865746377211125238978442505695369677078544996996794" +
      "686445490598793163688923009879312773617821542499922957635148220826989519366803" +
      "318252886939849646510582093923982948879332036250944311730123819706841614039701" +
      "983767932068328237646480429531180232878250981945581530175671736133206981125099" +
      "618188159304169035159888851934580727386673858942287922849989208680582574927961" +
      "048419844436346324496848756023362482704197862320900216099023530436994184914631" +
      "409343173814364054625315209618369088870701676839642437814059271456354906130310" +
      "720851038375051011574770417189861068739696552126715468895703503540212340784981" +
      "933432106817012100562788023519303322474501585390473041995777709350366041699732" +
      "972508868769664035557071622684471625607988265178713419512466520103059212366771" +
      "943252786753985589448969709640975459185695638023637016211204774272283648961342" +
      "251644507818244235294863637214174023889344124796357437026375529444833799801612" +
      "549227850925778256209262264832627793338656648162772516401910590049164499828931");

  static BigDecimal PI = new BigDecimal(
    "3.14159265358979323846264338327950288419716939937510582097494459230781640628620" +
      "899862803482534211706798214808651328230664709384460955058223172535940812848111" +
      "745028410270193852110555964462294895493038196442881097566593344612847564823378" +
      "678316527120190914564856692346034861045432664821339360726024914127372458700660" +
      "631558817488152092096282925409171536436789259036001133053054882046652138414695" +
      "194151160943305727036575959195309218611738193261179310511854807446237996274956" +
      "735188575272489122793818301194912983367336244065664308602139494639522473719070" +
      "217986094370277053921717629317675238467481846766940513200056812714526356082778" +
      "577134275778960917363717872146844090122495343014654958537105079227968925892354" +
      "201995611212902196086403441815981362977477130996051870721134999999837297804995" +
      "105973173281609631859502445945534690830264252230825334468503526193118817101000" +
      "313783875288658753320838142061717766914730359825349042875546873115956286388235" +
      "378759375195778185778053217122680661300192787661119590921642019893809525720106" +
      "548586327886593615338182796823030195203530185296899577362259941389124972177528" +
      "347913151557485724245415069595082953311686172785588907509838175463746493931925" +
      "506040092770167113900984882401285836160356370766010471018194295559619894676783" +
      "744944825537977472684710404753464620804668425906949129331367702898915210475216" +
      "205696602405803815019351125338243003558764024749647326391419927260426992279678" +
      "235478163600934172164121992458631503028618297455570674983850549458858692699569" +
      "092721079750930295532116534498720275596023648066549911988183479775356636980742" +
      "654252786255181841757467289097777279380008164706001614524919217321721477235014");


  @DataProvider
  public Object[][] precisionsDataProvider() {
    return new Object[][]{
      {10, 1}, {100, 1}, {300, 1}, {1000, 2}, {1200, 2},
    };
  }

  @Test(dataProvider = "precisionsDataProvider")
  public void e(int precisions, int minusForCmp) throws Exception {
    BigMath bigMath = new BigMath(precisions).copy();

    //
    //
    BigDecimal e = bigMath.e();
    //
    //

    MathContext mcCmp = new MathContext(precisions - minusForCmp);

    assertThat(e.round(mcCmp)).isEqualByComparingTo(E.round(mcCmp));
  }

  @Test(dataProvider = "precisionsDataProvider")
  public void pi(int precisions, int minusForCmp) throws Exception {
    BigMath bigMath = new BigMath(precisions).copy();

    //
    //
    BigDecimal e = bigMath.pi();
    //
    //

    MathContext mcCmp = new MathContext(precisions - minusForCmp);

    assertThat(e.round(mcCmp)).isEqualByComparingTo(PI.round(mcCmp));
  }

  @DataProvider
  public Object[][] expVariantsDP() {
    return new Object[][]{
      {12.34D}, {-1.234D}
    };
  }

  @Test(dataProvider = "expVariantsDP")
  public void exp(double a) throws Exception {
    BigMath bigMath = new BigMath(13);
    MathContext mcCmp = new MathContext(12);

    BigDecimal expA_expected = BigDecimal.valueOf(Math.exp(a)).round(mcCmp);

    //
    //
    BigDecimal expA_actual = bigMath.exp(BigDecimal.valueOf(a)).round(mcCmp);
    //
    //

    assertThat(expA_expected).isEqualByComparingTo(expA_actual);
  }

  @DataProvider
  public Object[][] sqrtDataDP() {
    return new Object[][]{
      {"4"}, {"40000"}, {"4345.657"}, {"0.003555467"}, {"11"}, {"10005"}
    };
  }

  @Test(dataProvider = "sqrtDataDP")
  public void sqrt(String strValue) throws Exception {
    BigMath bigMath = new BigMath(1000);
    MathContext mcCmp = new MathContext(999);

    BigDecimal value = new BigDecimal(strValue);

    //
    //
    BigDecimal sqrt = bigMath.sqrt(value);
    //
    //

    assertThat(sqrt.multiply(sqrt, bigMath.mc).round(mcCmp)).isEqualByComparingTo(value.round(mcCmp));

  }

  enum BorderPlaceTest {
    Right, Left, Inner;
  }

  @DataProvider
  public Object[][] borderDP() {
    return new Object[][]{
      {BorderPlaceTest.Right},
      {BorderPlaceTest.Left},
      {BorderPlaceTest.Inner},
    };
  }

  @Test(dataProvider = "borderDP")
  public void border(BorderPlaceTest borderPlace) throws Exception {
    BigMath bigMath = new BigMath(100);
    MathContext mcCmp = new MathContext(99);

    BigDecimal expected = new BigDecimal("3213.455467");
    BigDecimal from = new BigDecimal("32.5646768");
    BigDecimal to = new BigDecimal("321334.65785435");

    BigDecimal delta = to.subtract(from).multiply(BigDecimal.valueOf(10_000L));

    BigDecimal value = expected;

    switch (borderPlace) {
      case Left:
        value = value.subtract(delta);
        break;
      case Right:
        value = value.add(delta);
        break;
    }

    //
    //
    BigDecimal actual = bigMath.border(value, from, to);
    //
    //

    assertThat(actual.round(mcCmp)).isEqualByComparingTo(expected.round(mcCmp));
  }

  @Test
  public void border2() throws Exception {
    BigMath bigMath = new BigMath(100);

    BigDecimal from = new BigDecimal("-3.1");
    BigDecimal to = new BigDecimal("3.1");
    BigDecimal value = new BigDecimal("-20");

    //
    //
    BigDecimal result = bigMath.border(value, from, to);
    //
    //

    assertThat(result).isGreaterThan(from);
    assertThat(result).isLessThan(to);

  }

  @Test
  public void cut() throws Exception {
    BigMath bigMath = new BigMath(100);
    MathContext mcCmp = new MathContext(99);

    BigDecimal expected = new BigDecimal("3213.455467");

    BigDecimal delta = new BigDecimal("321334.65785435");

    BigDecimal distance = delta.multiply(new BigDecimal("5423667548765345346765433567")).add(expected);

    //
    //
    BigDecimal actual = bigMath.cut(distance, delta);
    //
    //

    assertThat(actual.round(mcCmp)).isEqualByComparingTo(expected.round(mcCmp));
  }

  private static void fillList(List<Double> list, double x, double delta, double end) {
    while (x < end) {
      list.add(x);
      x += delta;
    }
  }

  @DataProvider
  public Object[][] values50() {
    List<Double> list = new ArrayList<>(100);

    fillList(list, -20D, 1D, 20.1D);
    fillList(list, -10D, 0.2D, 10.1D);

    list.sort(Comparator.comparingDouble(x -> x));

    {
      Object[][] ret = new Object[list.size()][];
      for (int i = 0, s = list.size(); i < s; i++) {
        ret[i] = new Object[]{list.get(i)};
      }
      return ret;
    }
  }

  @Test(dataProvider = "values50")
  public void sin(double x) throws Exception {
    BigMath bigMath = new BigMath(15);
    MathContext mcCmp = new MathContext(9);

    BigDecimal expected = BigDecimal.valueOf(Math.sin(x));

    //
    //
    BigDecimal actual = bigMath.sin(BigDecimal.valueOf(x));
    //
    //

    assertThat(actual.round(mcCmp)).isEqualByComparingTo(expected.round(mcCmp));
  }

  @Test(dataProvider = "values50")
  public void cos(double x) throws Exception {
    BigMath bigMath = new BigMath(15);
    MathContext mcCmp = new MathContext(9);

    BigDecimal expected = BigDecimal.valueOf(Math.cos(x));

    //
    //
    BigDecimal actual = bigMath.cos(BigDecimal.valueOf(x));
    //
    //

    assertThat(actual.round(mcCmp)).isEqualByComparingTo(expected.round(mcCmp));
  }

  @DataProvider
  public Object[][] valuesForLogs() {

    List<Double> values = new ArrayList<>();
    values.add(1.54326d);
    values.add(7.54326d);
    values.add(98.654673d);
    values.add(1.54326e2);
    values.add(7.50987e3);
    values.add(98.600673e7);
    values.add(1.81764e20);
    values.add(7.509078712e30);
    values.add(91.700613e70);
    values.add(713.230087609e120);

    Object[][] ret = new Object[values.size() * 2][];

    for (int i = 0, s = values.size(); i < s; i++) {
      ret[i] = new Object[]{values.get(i)};
      ret[s + i] = new Object[]{1d / values.get(i)};
    }

    return ret;
  }

  @Test(dataProvider = "valuesForLogs")
  public void ln(double x) throws Exception {
    BigMath bigMath = new BigMath(15);
    MathContext mcCmp = new MathContext(9);

    BigDecimal expected = BigDecimal.valueOf(Math.log(x));

    //
    //
    BigDecimal actual = bigMath.ln(BigDecimal.valueOf(x));
    //
    //

    assertThat(actual.round(mcCmp)).isEqualByComparingTo(expected.round(mcCmp));
  }

  @Test
  public void ln1() throws Exception {
    ln(1.1e213d);
  }

  @DataProvider
  public Object[][] e2nData() {
    return new Object[][]{{0}, {1}, {2}, {3}, {4}, {5}, {6}};
  }

  @Test(dataProvider = "e2nData")
  public void e2n(int n) throws Exception {
    BigMath bigMath = new BigMath(15);
    MathContext mcCmp = new MathContext(9);

    BigDecimal expected = BigDecimal.valueOf(Math.exp(1L << n));

    //
    //
    BigDecimal actual = bigMath.e2n(n);
    //
    //

    assertThat(actual.round(mcCmp)).isEqualByComparingTo(expected.round(mcCmp));
  }

  @Test
  public void cut2() throws Exception {
    BigMath bigMath = new BigMath(100);

    BigDecimal distance = new BigDecimal("23.1");
    BigDecimal delta = new BigDecimal("6.2");

    //
    //
    BigDecimal result = bigMath.cut(distance, delta);
    //
    //

    assertThat(result).isGreaterThanOrEqualTo(BigDecimal.ZERO);
    assertThat(result).isLessThan(delta);
  }

  @Test
  public void cut3() throws Exception {
    BigMath bigMath = new BigMath(100);

    BigDecimal distance = new BigDecimal("9.94159265357");
    BigDecimal delta = new BigDecimal("6.28318530714");

    //
    //
    BigDecimal result = bigMath.cut(distance, delta);
    //
    //

    assertThat(result).isGreaterThanOrEqualTo(BigDecimal.ZERO);
    assertThat(result).isLessThan(delta);
  }

  @Test
  public void pow() throws Exception {
    BigMath bigMath = new BigMath(1000);
    BigDecimal a = new BigDecimal("2.3");
    BigDecimal b = new BigDecimal("3.1");
    BigDecimal c = bigMath.pow(a, b);
    System.out.println("a   = " + a);
    System.out.println("b   = " + b);
    System.out.println("a^b = " + c);
  }

  @DataProvider
  public Object[][] root_DataProvider() {
    return new Object[][]{
      {123.456, 7},
      {1.23456, 5},
      {8.1672e12, 17},
      {8.16712e12, 3},
      {8.2e12, -13},
    };
  }

  @Test(dataProvider = "root_DataProvider")
  public void root(double a, long n) throws Exception {
    BigMath bigMath = new BigMath(15);
    MathContext mcCmp = new MathContext(9);

    BigDecimal expected = BigDecimal.valueOf(Math.pow(a, 1d / n));

    //
    //
    BigDecimal actual = bigMath.root(new BigDecimal(a), n);
    //
    //

    assertThat(actual.round(mcCmp)).isEqualByComparingTo(expected.round(mcCmp));
  }

  @DataProvider
  public Object[][] powInt_DataProvider() {
    return new Object[][]{
      {2.3457, 156},
      {1.35701, 56},
      {13.701, 7},
      {17.3401, -7},
    };
  }

  @Test(dataProvider = "powInt_DataProvider")
  public void powInt(double a, long n) throws Exception {

    BigMath bigMath = new BigMath(15);
    MathContext mcCmp = new MathContext(9);

    BigDecimal expected = BigDecimal.valueOf(Math.pow(a, n));

    //
    //
    BigDecimal actual = bigMath.powInt(new BigDecimal(a), n);
    //
    //

    assertThat(actual.round(mcCmp)).isEqualByComparingTo(expected.round(mcCmp));
  }


  @Test(dataProvider = "powInt_DataProvider")
  public void powBigInt(double a, long n) throws Exception {

    BigMath bigMath = new BigMath(15);
    MathContext mcCmp = new MathContext(9);

    BigDecimal expected = BigDecimal.valueOf(Math.pow(a, n));

    //
    //
    BigDecimal actual = bigMath.powBigInt(new BigDecimal(a), BigInteger.valueOf(n));
    //
    //

    assertThat(actual.round(mcCmp)).isEqualByComparingTo(expected.round(mcCmp));
  }

}