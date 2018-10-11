package pompei.maths.collada.generators;

import pompei.maths.collada.core.VecXYZ;

public interface Surface {
  VecXYZ position(double u, double v);

  VecXYZ normal(double u, double v);
}
