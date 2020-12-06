package pompei.maths.difur.many_masses.djanibekov;

import pompei.maths.difur.DiffUr;
import pompei.maths.difur.DiffUrDefault;
import pompei.maths.difur.F;
import pompei.maths.difur.Stepper_H4_Hoine;

import java.io.PrintStream;

import static java.lang.Math.sqrt;
import static java.nio.charset.StandardCharsets.UTF_8;

public class Джанибеков {

  private static final double A = 1;
  private static final double B = 3;
  private static final double C = 2;

  private static final double m1 = 1;
  private static final double m2 = 1;
  private static final double m3 = 1;
  private static final double m4 = 1;
  private static final double m5 = 1;
  private static final double m6 = 1;
  private static final double m7 = 1;
  private static final double m8 = 1;

  private static final double L12 = 2 * B;
  private static final double L23 = 2 * A;
  private static final double L34 = 2 * B;
  private static final double L41 = 2 * A;

  private static final double L56 = 2 * B;
  private static final double L67 = 2 * A;
  private static final double L78 = 2 * B;
  private static final double L85 = 2 * A;

  private static final double L15 = 2 * C;
  private static final double L26 = 2 * C;
  private static final double L37 = 2 * C;
  private static final double L48 = 2 * C;

  private static final double DU = 0.01;

  private static final double K_TR = 0.1;
  private static final double K_U = 10;

  private static class MF implements F {

    @Override
    public void f(double[] r, double t, double[] x) {
      int i = 0;
      double x1 = x[i++], y1 = x[i++], z1 = x[i++], vx1 = x[i++], vy1 = x[i++], vz1 = x[i++];
      double x2 = x[i++], y2 = x[i++], z2 = x[i++], vx2 = x[i++], vy2 = x[i++], vz2 = x[i++];
      double x3 = x[i++], y3 = x[i++], z3 = x[i++], vx3 = x[i++], vy3 = x[i++], vz3 = x[i++];
      double x4 = x[i++], y4 = x[i++], z4 = x[i++], vx4 = x[i++], vy4 = x[i++], vz4 = x[i++];
      double x5 = x[i++], y5 = x[i++], z5 = x[i++], vx5 = x[i++], vy5 = x[i++], vz5 = x[i++];
      double x6 = x[i++], y6 = x[i++], z6 = x[i++], vx6 = x[i++], vy6 = x[i++], vz6 = x[i++];
      double x7 = x[i++], y7 = x[i++], z7 = x[i++], vx7 = x[i++], vy7 = x[i++], vz7 = x[i++];
      double x8 = x[i++], y8 = x[i++], z8 = x[i++], vx8 = x[i++], vy8 = x[i++], vz8 = x[i];

      double F12 = force(x1, y1, z1, x2, y2, z2, vx1, vy1, vz1, vx2, vy2, vz2, m1, m2, L12);
      double F23 = force(x2, y2, z2, x3, y3, z3, vx2, vy2, vz2, vx3, vy3, vz3, m2, m3, L23);
      double F34 = force(x3, y3, z3, x4, y4, z4, vx3, vy3, vz3, vx4, vy4, vz4, m3, m4, L34);
      double F41 = force(x4, y4, z4, x1, y1, z1, vx4, vy4, vz4, vx1, vy1, vz1, m4, m1, L41);

      double F56 = force(x5, y5, z5, x6, y6, z6, vx5, vy5, vz5, vx6, vy6, vz6, m5, m6, L56);
      double F67 = force(x6, y6, z6, x7, y7, z7, vx6, vy6, vz6, vx7, vy7, vz7, m6, m7, L67);
      double F78 = force(x7, y7, z7, x8, y8, z8, vx7, vy7, vz7, vx8, vy8, vz8, m7, m8, L78);
      double F85 = force(x8, y8, z8, x5, y5, z5, vx8, vy8, vz8, vx5, vy5, vz5, m8, m5, L85);

      double F15 = force(x1, y1, z1, x5, y5, z5, vx1, vy1, vz1, vx5, vy5, vz5, m1, m5, L15);
      double F26 = force(x2, y2, z2, x6, y6, z6, vx2, vy2, vz2, vx6, vy6, vz6, m2, m6, L26);
      double F37 = force(x3, y3, z3, x7, y7, z7, vx3, vy3, vz3, vx7, vy7, vz7, m3, m7, L37);
      double F48 = force(x4, y4, z4, x8, y8, z8, vx4, vy4, vz4, vx8, vy8, vz8, m4, m8, L48);

      double N12x = x2 - x1, N12y = y2 - y1, N12z = z2 - z1;
      double N23x = x3 - x2, N23y = y3 - y2, N23z = z3 - z2;
      double N34x = x4 - x3, N34y = y4 - y3, N34z = z4 - z3;
      double N41x = x1 - x4, N41y = y1 - y4, N41z = z1 - z4;

      double N56x = x6 - x5, N56y = y6 - y5, N56z = z6 - z5;
      double N67x = x7 - x6, N67y = y7 - y6, N67z = z7 - z6;
      double N78x = x8 - x7, N78y = y8 - y7, N78z = z8 - z7;
      double N85x = x5 - x8, N85y = y5 - y8, N85z = z5 - z8;

      double N15x = x5 - x1, N15y = y5 - y1, N15z = z5 - z1;
      double N26x = x6 - x2, N26y = y6 - y2, N26z = z6 - z2;
      double N37x = x7 - x3, N37y = y7 - y3, N37z = z7 - z3;
      double N48x = x8 - x4, N48y = y8 - y4, N48z = z8 - z4;

      double N12 = sqrt(N12x * N12x + N12y * N12y + N12z * N12z);
      double N23 = sqrt(N23x * N23x + N23y * N23y + N23z * N23z);
      double N34 = sqrt(N34x * N34x + N34y * N34y + N34z * N34z);
      double N41 = sqrt(N41x * N41x + N41y * N41y + N41z * N41z);

      double N56 = sqrt(N56x * N56x + N56y * N56y + N56z * N56z);
      double N67 = sqrt(N67x * N67x + N67y * N67y + N67z * N67z);
      double N78 = sqrt(N78x * N78x + N78y * N78y + N78z * N78z);
      double N85 = sqrt(N85x * N85x + N85y * N85y + N85z * N85z);

      double N15 = sqrt(N15x * N15x + N15y * N15y + N15z * N15z);
      double N26 = sqrt(N26x * N26x + N26y * N26y + N26z * N26z);
      double N37 = sqrt(N37x * N37x + N37y * N37y + N37z * N37z);
      double N48 = sqrt(N48x * N48x + N48y * N48y + N48z * N48z);

      double n12x = N12x / N12, n12y = N12y / N12, n12z = N12z / N12;
      double n23x = N23x / N23, n23y = N23y / N23, n23z = N23z / N23;
      double n34x = N34x / N34, n34y = N34y / N34, n34z = N34z / N34;
      double n41x = N41x / N41, n41y = N41y / N41, n41z = N41z / N41;

      double n56x = N56x / N56, n56y = N56y / N56, n56z = N56z / N56;
      double n67x = N67x / N67, n67y = N67y / N67, n67z = N67z / N67;
      double n78x = N78x / N78, n78y = N78y / N78, n78z = N78z / N78;
      double n85x = N85x / N85, n85y = N85y / N85, n85z = N85z / N85;

      double n15x = N15x / N15, n15y = N15y / N15, n15z = N15z / N15;
      double n26x = N26x / N26, n26y = N26y / N26, n26z = N26z / N26;
      double n37x = N37x / N37, n37y = N37y / N37, n37z = N37z / N37;
      double n48x = N48x / N48, n48y = N48y / N48, n48z = N48z / N48;

      double F1x = F12 * n12x - F41 * n41x + F15 * n15x;
      double F1y = F12 * n12y - F41 * n41y + F15 * n15y;
      double F1z = F12 * n12z - F41 * n41z + F15 * n15z;

      double F2x = -F12 * n12x + F23 * n23x + F26 * n26x;
      double F2y = -F12 * n12y + F23 * n23y + F26 * n26y;
      double F2z = -F12 * n12z + F23 * n23z + F26 * n26z;

      double F3x = -F23 * n23x + F34 * n34x + F37 * n37x;
      double F3y = -F23 * n23y + F34 * n34y + F37 * n37y;
      double F3z = -F23 * n23z + F34 * n34z + F37 * n37z;

      double F4x = -F34 * n34x + F41 * n41x + F48 * n48x;
      double F4y = -F34 * n34y + F41 * n41y + F48 * n48y;
      double F4z = -F34 * n34z + F41 * n41z + F48 * n48z;

      double F5x = F56 * n56x - F85 * n85x - F15 * n15x;
      double F5y = F56 * n56y - F85 * n85y - F15 * n15y;
      double F5z = F56 * n56z - F85 * n85z - F15 * n15z;

      double F6x = -F56 * n56x + F67 * n67x - F26 * n26x;
      double F6y = -F56 * n56y + F67 * n67y - F26 * n26y;
      double F6z = -F56 * n56z + F67 * n67z - F26 * n26z;

      double F7x = -F67 * n67x + F78 * n78x - F37 * n37x;
      double F7y = -F67 * n67y + F78 * n78y - F37 * n37y;
      double F7z = -F67 * n67z + F78 * n78z - F37 * n37z;

      double F8x = -F78 * n78x + F85 * n85x - F48 * n48x;
      double F8y = -F78 * n78y + F85 * n85y - F48 * n48y;
      double F8z = -F78 * n78z + F85 * n85z - F48 * n48z;

      //F1
      r[0] = x[3];
      r[1] = x[4];
      r[2] = x[5];

      r[3] = F1x / m1;
      r[4] = F1y / m1;
      r[5] = F1z / m1;

      //F2
      r[6] = x[9];
      r[7] = x[10];
      r[8] = x[11];

      r[9] = F2x / m2;
      r[10] = F2y / m2;
      r[11] = F2z / m2;

      //F3
      r[12] = x[15];
      r[13] = x[16];
      r[14] = x[17];

      r[15] = F3x / m3;
      r[16] = F3y / m3;
      r[17] = F3z / m3;

      //F4
      r[18] = x[21];
      r[19] = x[22];
      r[20] = x[23];

      r[21] = F4x / m4;
      r[22] = F4y / m4;
      r[23] = F4z / m4;

      //F5
      r[24] = x[27];
      r[25] = x[28];
      r[26] = x[29];

      r[27] = F5x / m5;
      r[28] = F5y / m5;
      r[29] = F5z / m5;

      //F6
      r[30] = x[33];
      r[31] = x[34];
      r[32] = x[35];

      r[33] = F6x / m6;
      r[34] = F6y / m6;
      r[35] = F6z / m6;

      //F7
      r[36] = x[39];
      r[37] = x[40];
      r[38] = x[41];

      r[39] = F7x / m7;
      r[40] = F7y / m7;
      r[41] = F7z / m7;

      //F8
      r[42] = x[45];
      r[43] = x[46];
      r[44] = x[47];

      r[45] = F8x / m8;
      r[46] = F8y / m8;
      r[47] = F8z / m8;
    }


    private double force(double x1, double y1, double z1, double x2, double y2, double z2,
                         double vx1, double vy1, double vz1, double vx2, double vy2, double vz2,
                         @SuppressWarnings("UnusedParameters") double m1,
                         @SuppressWarnings("UnusedParameters") double m2, double L0) {

      double L = Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2) + (z1 - z2) * (z1 - z2));

      double dL = L0 - L;

      if (dL > DU) {
        return K_TR * dL;
      }

      double nx = (x2 - x1) / L, ny = (y2 - y1) / L, nz = (z2 - z1) / L;
      double vx = vx2 - vx1, vy = vy2 - vy1, vz = vz2 - vz1;

      double v = nx * vx + ny * vy + nz * vz;

      return K_U * v;
    }
  }

  private static final MF f = new MF();

  public static void main(String[] args) throws Exception {
    DiffUr ur = new DiffUrDefault(new Stepper_H4_Hoine());
    ur.prepare(48, f);

    double v1x = 0, v1y = 0, v1z = 0;
    double v2x = 0, v2y = 0, v2z = 0;
    double v3x = 0, v3y = 0, v3z = 0;
    double v4x = 0, v4y = 0, v4z = 0;
    double v5x = 0, v5y = 0, v5z = 0;
    double v6x = 0, v6y = 0, v6z = 0;
    double v7x = 0, v7y = 0, v7z = 0;
    double v8x = 0, v8y = 0, v8z = 0;

    double AB = sqrt(A * A + B * B);
    double BC = sqrt(B * B + C * C);
    double CA = sqrt(C * C + A * A);

    double a_c = A / AB;
    double b_c = B / AB;

    double b_a = B / BC;
    double c_a = C / BC;

    double c_b = C / CA;
    double a_b = A / CA;

    double omegaX = 0.0001;
    double omegaY = 1;
    double omegaZ = -0.0002;

    //OMEGA Y

    v5x += c_b * omegaY;
    v6x += c_b * omegaY;
    v5z += -a_b * omegaY;
    v6z += -a_b * omegaY;

    v7x += c_b * omegaY;
    v8x += c_b * omegaY;
    v7z += a_b * omegaY;
    v8z += a_b * omegaY;

    v1x += -c_b * omegaY;
    v2x += -c_b * omegaY;
    v1z += -a_b * omegaY;
    v2z += -a_b * omegaY;

    v3x += -c_b * omegaY;
    v4x += -c_b * omegaY;
    v3z += a_b * omegaY;
    v4z += a_b * omegaY;

    //OMEGA X

    v5y += -c_a * omegaX;
    v8y += -c_a * omegaX;
    v5z += -b_a * omegaX;
    v8z += -b_a * omegaX;

    v6y += -c_a * omegaX;
    v7y += -c_a * omegaX;
    v6z += b_a * omegaX;
    v7z += b_a * omegaX;

    v2y += c_a * omegaX;
    v3y += c_a * omegaX;
    v2z += b_a * omegaX;
    v3z += b_a * omegaX;

    v1y += c_a * omegaX;
    v4y += c_a * omegaX;
    v1z += -b_a * omegaX;
    v4z += -b_a * omegaX;

    //OMEGA Z

    v1x += b_c * omegaZ;
    v5x += b_c * omegaZ;
    v1y += a_c * omegaZ;
    v5y += a_c * omegaZ;

    v2x += -b_c * omegaZ;
    v6x += -b_c * omegaZ;
    v2y += a_c * omegaZ;
    v6y += a_c * omegaZ;

    v3x += -b_c * omegaZ;
    v7x += -b_c * omegaZ;
    v3y += -a_c * omegaZ;
    v7y += -a_c * omegaZ;

    v4x += b_c * omegaZ;
    v8x += b_c * omegaZ;
    v4y += -a_c * omegaZ;
    v8y += -a_c * omegaZ;

    double[] x = ur.getX();
    //@formatter:off
    // Координаты
    ss(x, 0, 1, 2, A, -B, -C); // 1
    ss(x, 6, 7, 8, A, B, -C); // 2
    ss(x, 12, 13, 14, -A, B, -C); // 3
    ss(x, 18, 19, 20, -A, -B, -C); // 4
    ss(x, 24, 25, 26, A, -B, C); // 5
    ss(x, 30, 31, 32, A, B, C); // 6
    ss(x, 36, 37, 38, -A, B, C); // 7
    ss(x, 42, 43, 44, -A, -B, C); // 8
    // Скорости
    ss(x, 3, 4, 5, v1x, v1y, v1z); // 1
    ss(x, 9, 10, 11, v2x, v2y, v2z); // 2
    ss(x, 15, 16, 17, v3x, v3y, v3z); // 3
    ss(x, 21, 22, 23, v4x, v4y, v4z); // 4
    ss(x, 27, 28, 29, v5x, v5y, v5z); // 5
    ss(x, 33, 34, 35, v6x, v6y, v6z); // 6
    ss(x, 39, 40, 41, v7x, v7y, v7z); // 7
    ss(x, 45, 46, 47, v8x, v8y, v8z); // 8
    //@formatter:on

    double saveStep = 1.0 / 24.0;
    double nextSave = saveStep;
    ur.setT(0);
    ur.setH(0.00001);

    PrintStream out = new PrintStream("build/out.txt", UTF_8);
    PrintStream out2 = new PrintStream("build/out2.txt", UTF_8);

    save(out, out2, ur);
    while (ur.getT() < 20) {
      ur.step();
      if (ur.getT() > nextSave) {
        save(out, out2, ur);
        nextSave += saveStep;
        System.out.println("t = " + nextSave);
      }
    }

    out.close();
    out2.close();
    System.out.println("Finish");
  }

  private static void save(PrintStream out, PrintStream out2, DiffUr ur) {
    double[] x = ur.getX();
    {
      out.print(ur.getT());
      out.print(' ');
      for (double aX : x) {
        out.print(aX);
        out.print(' ');
      }
      out.println();
    }

    {
      out2.println("t=" + ur.getT() + "; l12=" + l(x, 1, 2));
    }
  }

  @SuppressWarnings({"PointlessArithmeticExpression", "SameParameterValue"})
  private static double l(double[] x, int i, int j) {

    double dx = x[(i - 1) * 6 + 0] - x[(j - 1) * 6 + 0];
    double dy = x[(i - 1) * 6 + 1] - x[(j - 1) * 6 + 1];
    double dz = x[(i - 1) * 6 + 2] - x[(j - 1) * 6 + 2];

    return sqrt(dx * dx + dy * dy + dz * dz);
  }

  private static void ss(double[] x, int xi, int yi, int zi, double X, double Y, double Z) {
    x[xi] = X;
    x[yi] = Y;
    x[zi] = Z;
  }
}
