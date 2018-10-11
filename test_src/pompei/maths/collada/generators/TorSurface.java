package pompei.maths.collada.generators;

import pompei.maths.collada.core.VecXYZ;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

public class TorSurface implements Surface {

  public double R = 2;
  public double r = 1;

  @SuppressWarnings("UnnecessaryLocalVariable")
  @Override
  public VecXYZ position(double u, double v) {

    double rAx = cos(u);
    double rAy = sin(u);
    double rAz = 0;

    double rBx = 0;
    double rBy = 0;
    double rBz = 1;

    double rx = rAx * cos(v) + rBx * sin(v);
    double ry = rAy * cos(v) + rBy * sin(v);
    double rz = rAz * cos(v) + rBz * sin(v);

    VecXYZ ret = new VecXYZ();

    ret.x = R * rAx + r * rx;
    ret.y = R * rAy + r * ry;
    ret.z = R * rAz + r * rz;

    return ret;
  }

  @Override
  public VecXYZ normal(double u, double v) {

    double rAx = cos(u);
    double rAy = sin(u);
    double rAz = 0;

    double rBx = 0;
    double rBy = 0;
    double rBz = 1;

    double rx = rAx * cos(v) + rBx * sin(v);
    double ry = rAy * cos(v) + rBy * sin(v);
    double rz = rAz * cos(v) + rBz * sin(v);

    VecXYZ ret = new VecXYZ();

    ret.x = rx;
    ret.y = ry;
    ret.z = rz;

    return ret;
  }
}
