package pompei.maths.difur.falling_in_cylinder;

import java.io.BufferedWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import pompei.maths.difur.DiffUr;
import pompei.maths.difur.DiffUrDefault;
import pompei.maths.difur.Stepper_H5_RungeKutta;


import static java.lang.Math.*;
import static java.nio.charset.StandardCharsets.UTF_8;
import static java.nio.file.StandardOpenOption.APPEND;
import static java.nio.file.StandardOpenOption.CREATE;

public class FallingInCylinder {
  private static final double DEG = Math.PI / 180.0;
  private static final double ROUND = Math.PI * 2.0;

  public static void main(String[] args) throws Exception {
    new FallingInCylinder().run();
  }

  private void run() throws Exception {
    FallInCylinderF f = new FallInCylinderF();

    f.g = 9.81;
    f.A = 10 * DEG;
    f.H0 = 3.1;
    f.Omega0 = 0.4 * ROUND;

    DiffUr diffUr = new DiffUrDefault(new Stepper_H5_RungeKutta());

    diffUr.prepare(4, f);

    diffUr.setT(0);
    diffUr.setH(0.0001);

    double[] a = diffUr.getX();

    a[0] = a[1] = a[2] = a[3] = 0;
    a[0] = f.H0;
    a[3] = f.Omega0;

    printXYZ(0, a, f);
    for (int i = 1; i <= 1000000; i++) {
      diffUr.step();
      if (i % 100 == 0) printXYZ(diffUr.getT(), a, f);
    }
  }

  private void printXYZ(double t, double[] a, FallInCylinderF f) throws Exception {
    double x = a[0] * tan(f.A) * cos(a[1]);
    double y = a[0] * tan(f.A) * sin(a[1]);
    double z = a[0];

    try (BufferedWriter wr = Files.newBufferedWriter(Paths.get(
      System.getProperty("user.home") + "/tmp/asd.txt"), UTF_8, APPEND, CREATE)) {
      //noinspection StringBufferReplaceableByString
      var sb = new StringBuilder();
      sb.append(t).append('\t');
      sb.append(x).append('\t');
      sb.append(y).append('\t');
      sb.append(z).append("\r\n");
      wr.write(sb.toString());
    }
  }

  private void print(double t, double[] a, FallInCylinderF f) {
    int w = 30;

    double x = a[0] * tan(f.A) * cos(a[1]);
    double y = a[0] * tan(f.A) * sin(a[1]);
    double z = a[0];

    StringBuilder sb = new StringBuilder();
    sb.append(t);
    toLen(sb, w);
    sb.append('|').append(x);
    toLen(sb, 2 * w);
    sb.append('|').append(y);
    toLen(sb, 3 * w);
    sb.append('|').append(z);

    System.out.println(sb);
  }

  private void toLen(StringBuilder sb, int len) {
    while (sb.length() < len) sb.append(' ');
  }
}
