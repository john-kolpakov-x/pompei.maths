package pompei.maths.collada.generators;

import pompei.maths.collada.core.Geometry;

public class GeneratorClosedUV {

  @SuppressWarnings("UnnecessaryLocalVariable")
  public static void append(Geometry geometry, DomainUV domainUV, Surface surface, boolean rightRound) {

    int Ni = domainUV.Nu(), Nj = domainUV.Nv();
    double u1 = domainUV.u1(), u2 = domainUV.u2();
    double v1 = domainUV.v1(), v2 = domainUV.v2();

    double du = (u2 - u1) / (double) Ni;
    double dv = (v2 - v1) / (double) Nj;

    for (int j = 0; j < Nj; j++) {
      for (int i = 0; i < Ni; i++) {

        double u = u1 + du * i, v = v1 + dv * j;

        geometry.addPosition(surface.position(u, v));
        geometry.addNormal(surface.normal(u, v));

        int I1 = i;
        int I2 = i < Ni - 1 ? i + 1 : 0;
        int J1 = j;
        int J2 = j < Nj - 1 ? j + 1 : 0;

        int a = I1 + J1 * Ni;
        int b = I2 + J1 * Ni;
        int c = I1 + J2 * Ni;
        int d = I2 + J2 * Ni;

        if (rightRound) {
          geometry
            .addTriangle(a, d, b, a, d, b)
            .addTriangle(a, c, d, a, c, d)
          ;
        } else {
          geometry
            .addTriangle(a, b, d, a, b, d)
            .addTriangle(a, d, c, a, d, c)
          ;
        }
      }
    }
  }

}
