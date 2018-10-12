package pompei.maths.collada.generators;

import pompei.maths.collada.core.Geometry;
import pompei.maths.collada.core.VecXYZ;

public class GeometryGenerator {

  public static void append(Geometry geometry,
                            RectDomainUV domainUV,
                            Surface surface,
                            boolean rightRound,
                            boolean smooth) {

    if (domainUV.connectedByU() && domainUV.connectedByV()) {
      appendBothConnected(geometry, domainUV, surface, rightRound, smooth);
      return;
    }

    throw new IllegalArgumentException("Variant while do not realized:"
      + " domainUV.connectedByU() = " + domainUV.connectedByU()
      + ", domainUV.connectedByV() = " + domainUV.connectedByV());
  }

  @SuppressWarnings({"UnnecessaryLocalVariable", "SingleStatementInBlock"})
  private static void appendBothConnected(Geometry geometry,
                                          RectDomainUV domainUV,
                                          Surface surface,
                                          boolean rightRound,
                                          boolean smooth) {

    int Ni = domainUV.Nu(), Nj = domainUV.Nv();
    double u1 = domainUV.u1(), u2 = domainUV.u2();
    double v1 = domainUV.v1(), v2 = domainUV.v2();

    double du = (u2 - u1) / (double) Ni;
    double dv = (v2 - v1) / (double) Nj;

    for (int j = 0; j < Nj; j++) {
      for (int i = 0; i < Ni; i++) {

        double u = u1 + du * i, v = v1 + dv * j;

        geometry.addPosition(surface.position(u, v));

        int I1 = i;
        int I2 = i < Ni - 1 ? i + 1 : 0;
        int J1 = j;
        int J2 = j < Nj - 1 ? j + 1 : 0;

        int a = I1 + J1 * Ni;
        int b = I2 + J1 * Ni;
        int c = I1 + J2 * Ni;
        int d = I2 + J2 * Ni;

        int a1 = a;
        int b1 = b;
        int c1 = c;
        int d1 = d;

        if (smooth) {
          geometry.addNormal(surface.normal(u, v));
        } else {
          b1 = c1 = d1 = a;

          VecXYZ n1 = surface.normal(u, v);
          VecXYZ n2 = surface.normal(u + du, v);
          VecXYZ n3 = surface.normal(u, v + dv);
          VecXYZ n4 = surface.normal(u + du, v + dv);

          VecXYZ n = n1.plus(n2).plus(n3).plus(n4);

          geometry.addNormal(n.div(n.length()));
        }

        if (rightRound) {
          geometry
            .addTriangle(a, d, b, a1, d1, b1)
            .addTriangle(a, c, d, a1, c1, d1)
          ;
        } else {
          geometry
            .addTriangle(a, b, d, a1, b1, d1)
            .addTriangle(a, d, c, a1, d1, c1)
          ;
        }
      }
    }
  }

}
