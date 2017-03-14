package pompei.maths.utils;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

public class Conv {

  public static final double GIG = 1_000_000_000.0;

  public static String nanoToSec(long time1, long time2) {
    double v = (double) (time2 - time1) / GIG;

    DecimalFormat decimalFormat = new DecimalFormat("#0.000000");
    decimalFormat.setGroupingSize(3);
    decimalFormat.setGroupingUsed(true);
    DecimalFormatSymbols dfs=new DecimalFormatSymbols();
    dfs.setDecimalSeparator('.');
    dfs.setGroupingSeparator(' ');
    decimalFormat.setDecimalFormatSymbols(dfs);
    return decimalFormat.format(v) + " s";
  }

}
