package pompei.maths.tailor_calc;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.List;

public class Planets {
  static final MathContext MC = new MathContext(10);
  
  public static class Planet {
    BigDecimal T, a;
    int no;
    String name;
    
    @Override
    public String toString() {
      return no + ":" + name;
    }
    
    public void showDefiWith(Planet x) {
      BigDecimal T1_2 = T.multiply(T, MC);
      BigDecimal T2_2 = x.T.multiply(x.T, MC);
      
      BigDecimal a1_3 = a.multiply(a, MC).multiply(a, MC);
      BigDecimal a2_3 = x.a.multiply(x.a, MC).multiply(x.a, MC);
      
      BigDecimal difTa1 = T1_2.divide(a1_3, MC);
      BigDecimal difTa2 = T2_2.divide(a2_3, MC);
      
      System.out.println(this + "/" + x + "  ->  difTa1 = " + difTa1 + " : difTa2 = " + difTa2);
    }
    
    public BigDecimal calcDivTA() {
      BigDecimal T_2 = T.multiply(T, MC);
      BigDecimal a_3 = a.multiply(a, MC).multiply(a, MC);
      return T_2.divide(a_3, MC);
    }
    
    public String len(int len) {
      String NO = "" + no;
      while (NO.length() < 3) {
        NO = " " + NO;
      }
      String ret = no + ":" + name;
      while (ret.length() < len) {
        ret = " " + ret;
      }
      return ret;
    }
  }
  
  public static void pl(List<Planet> pls, String info) {
    String[] s = info.split("\\|");
    
    Planet p = new Planet();
    
    p.no = Integer.parseInt(s[0].replace(" ", ""));
    p.name = s[1].trim();
    p.T = new BigDecimal(s[2].replace(" ", ""));
    p.a = new BigDecimal(s[3].replace(" ", ""));
    
    pls.add(p);
  }
  
  public static void main(String[] args) {
    
    List<Planet> l = new ArrayList<>();
    
    pl(l, " 2 | Венера   |     224.698         |  0.723 332     ");
    pl(l, " 3 | Земля    |     365.256 366 004 |  1.000 002 610 ");
    pl(l, " 4 | Марс     |     686.98          |  1.523 662     ");
    pl(l, " 5 | Юпитер   |   4 332.589         |  5.204 267     ");
    pl(l, " 6 | Сатурн   |  10 759.22          |  9.582 017 199 ");
    pl(l, " 7 | Уран     |  30 685.4           | 19.229 411 95  ");
    pl(l, " 8 | Нептун   |  60 190.03          | 30.103 661 51  ");
    pl(l, " 9 | Плутон   |  90 553.02          | 39.482 117     ");
    pl(l, "10 | Хаумеа   | 102 937             | 42.984 92      ");
    pl(l, "11 | Макемаке | 111 867             | 45.436 301     ");
    
    Planet[] a = new Planet[100];
    
    for (Planet p : l) {
      a[p.no] = p;
    }
    for (Planet p : l) {
      System.out.println(p.len(20) + " - " + p.calcDivTA().divide(a[3].calcDivTA(), MC));
    }
    
    a[2].showDefiWith(a[11]);
    
  }
}
