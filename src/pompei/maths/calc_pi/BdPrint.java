package pompei.maths.calc_pi;

import java.io.PrintStream;
import java.math.BigDecimal;

@SuppressWarnings({"FieldCanBeLocal", "FieldMayBeFinal"})
public class BdPrint {
  private final PrintStream out;
  private int tab = 2;
  private int part = 8;
  private int partCount = 16;

  BdPrint(PrintStream out) {
    this.out = out;
  }

  int realTab;

  public void print(BigDecimal bd) {
    String s = "" + bd;
    int i = s.indexOf('.');
    realTab = tab;
    tab();
    if (i < 0) {
      printDigitStr(s);
    } else {
      out.append(s, 0, i + 1);
      realTab += i + 1;
      printDigitStr(s.substring(i + 1));
    }
  }

  private void tab() {
    for (int i = 0; i < realTab; i++) {
      out.append(' ');
    }
  }

  private void printDigitStr(String s) {
    int i = 0;
    final int length = s.length();
    int colCount = 0;
    while (true) {
      int j = i + part;
      if (j > length) {
        j = length;
        out.append(s, i, j);
        return;
      }

      out.append(s, i, j);
      colCount++;
      if (colCount > partCount) {
        out.append('\n');
        tab();
        colCount = 0;
      } else {
        out.append(' ');
      }

      i = j;
    }
  }
}
