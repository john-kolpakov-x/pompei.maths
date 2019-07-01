package pompei.maths.fourier;


import hageldave.ezfftw.dp.FFT;
import hageldave.ezfftw.dp.samplers.ComplexValuedSampler;
import hageldave.ezfftw.dp.writers.ComplexValuedWriter;
import org.testng.annotations.Test;

import java.security.SecureRandom;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Random;

public class FastFourierTransformTest {

  public static double rndDouble(Random rnd, int size) {
    return rnd.nextDouble() * rnd.nextInt(size) * (rnd.nextBoolean() ? 1 : -1);
  }

  @SuppressWarnings("SameParameterValue")
  public static String toStr(int leftLen, int rightLen, double value) {

    DecimalFormatSymbols dfs = new DecimalFormatSymbols();
    dfs.setDecimalSeparator('.');
    dfs.setGroupingSeparator(' ');
    DecimalFormat fmt = new DecimalFormat("0.####################", dfs);

    StringBuilder str = new StringBuilder(leftLen + rightLen + 10);
    str.append(fmt.format(value));
    int idx = str.toString().indexOf('.');
    if (idx < 0) {
      str.append(".0");
      idx = str.toString().indexOf('.');
    }

    for (int i = 0, L = leftLen - idx; i < L; i++) {
      str.insert(0, ' ');
    }

    int L = leftLen + rightLen + 1;
    while (str.length() < L) {
      str.append(' ');
    }

    return str.toString();
  }

  @Test
  public void rnd() {
    Random rnd = new SecureRandom();
//    System.out.println(toStr(5, 20, rndDouble(rnd, 200)));
//    System.out.println(rndDouble(rnd, 200));
//    System.out.println(rndDouble(rnd, 200));
//    System.out.println(rndDouble(rnd, 200));
    int l = 5, r = 20;
    System.out.println(toStr(l, r, 11.399763424443671));
    System.out.println(toStr(l, r, -4.090141313544932));
    System.out.println(toStr(l, r, -156.7205128972805));
    System.out.println(toStr(l, r, -110.79819059079891));
    System.out.println(toStr(l, r, -0.00000000079819059079891));
    System.out.println(toStr(l, r, rndDouble(rnd, 200)));
    System.out.println(toStr(l, r, rndDouble(rnd, 200)));
    System.out.println(toStr(l, r, rndDouble(rnd, 200)));
    System.out.println(toStr(l, r, rndDouble(rnd, 200)));
    System.out.println(toStr(l, r, rndDouble(rnd, 200)));
  }

  @Test
  public void FFT_iFFT() {
    final int length = 512;

    double[] re1 = new double[length];
    double[] im1 = new double[length];

    for (int i = 0; i < length; i++) {
      re1[i] = Math.sin((double) i / length * 7) + Math.sin((double) i / length * 70);
      im1[i] = 0;
    }

    ComplexValuedSampler reader1 = (imaginary, coo) -> {
      int i = (int) coo[0];
      return (imaginary ? im1 : re1)[i];
    };

    double[] re2 = new double[length];
    double[] im2 = new double[length];

    ComplexValuedWriter writer1 = (value, imaginary, coo) -> {
      int i = (int) coo[0];
      (imaginary ? im2 : re2)[i] = value;
    };

    //
    //
    FFT.fft(reader1, writer1, length);
    //
    //

    ComplexValuedSampler reader2 = (imaginary, coo) -> {
      int i = (int) coo[0];
      return (imaginary ? im2 : re2)[i];
    };

    double[] re3 = new double[length];
    double[] im3 = new double[length];

    ComplexValuedWriter writer2 = (value, imaginary, coo) -> {
      int i = (int) coo[0];
      (imaginary ? im3 : re3)[i] = value / length;
    };

    //
    //
    FFT.ifft(reader2, writer2, length);
    //
    //

    int l = 5, r = 20;

    System.out.println();

    for (int i = 0; i < length; i++) {

      double r2 = Math.sqrt(im2[i] * im2[i] + re2[i] * re2[i]);
      double fi2 = Math.atan2(im2[i], re2[i]) / (Math.PI / 180.0);

      System.out.println(""

        + toStr(l, r, re1[i]) + " +i* " + toStr(l, r, im1[i])
        + " | "
        + toStr(l, r, re3[i]) + " +i* " + toStr(l, r, im3[i])
        + " | "
        + toStr(l, r, r2) + "  @  " + toStr(l, r, fi2)

      );
    }

    System.out.println();
  }

  private static double f1(double x) {
    x += 0.4;
    return Math.sin(x * 7) + Math.sin(x * 70);
  }

  @Test
  public void FFT_twice() {
    final int length = 128;

    double[] re1 = new double[length * 2];

    for (int i = 0; i < length; i++) {
      re1[i] = f1((double) i / length);
      re1[i + length] = f1((double) (i + length) / length);
    }

    ComplexValuedSampler reader1 = (imaginary, coo) -> {
      int i = (int) coo[0];
      return imaginary ? 0 : re1[i];
    };
    ComplexValuedSampler reader2 = (imaginary, coo) -> {
      int i = (int) coo[0];
      return imaginary ? 0 : re1[i + length];
    };

    double[] re2 = new double[length * 2];
    double[] im2 = new double[length * 2];

    ComplexValuedWriter writer1 = (value, imaginary, coo) -> {
      int i = (int) coo[0];
      (imaginary ? im2 : re2)[i] = value;
    };
    ComplexValuedWriter writer2 = (value, imaginary, coo) -> {
      int i = (int) coo[0];
      (imaginary ? im2 : re2)[i + length] = value;
    };

    //
    //
    FFT.fft(reader1, writer1, length);
    FFT.fft(reader2, writer2, length);
    //
    //

    int l = 5, r = 20;

    System.out.println();

    for (int i = 0; i < length; i++) {
      double re_1 = re1[i];
      double re_2 = re1[i + length];

      double r_1 = Math.sqrt(im2[i] * im2[i] + re2[i] * re2[i]);
      double fi_1 = Math.atan2(im2[i], re2[i]) / (Math.PI / 180.0);
      double r_2 = Math.sqrt(im2[i + length] * im2[i + length] + re2[i + length] * re2[i + length]);
      double fi_2 = Math.atan2(im2[i + length], re2[i + length]) / (Math.PI / 180.0);

      System.out.println(""

        + toStr(l, r, re_1) + " & " + toStr(l, r, re_2)
        + " | "
        + toStr(l, r, r_1) + "  @  " + toStr(l, r, fi_1)
        + " & "
        + toStr(l, r, r_2) + "  @  " + toStr(l, r, fi_2)

      );
    }

    System.out.println();
  }

}
