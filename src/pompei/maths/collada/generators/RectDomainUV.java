package pompei.maths.collada.generators;

public interface RectDomainUV {
  double u1();

  double u2();

  double v1();

  double v2();

  int Nu();

  int Nv();

  boolean connectedByU();

  boolean connectedByV();
}
