package pompei.maths.collada.generators;

public class TorRectDomainUV implements RectDomainUV {
  @Override
  public double u1() {
    return -Math.PI;
  }

  @Override
  public double u2() {
    return +Math.PI;
  }

  @Override
  public double v1() {
    return -Math.PI;
  }

  @Override
  public double v2() {
    return +Math.PI;
  }

  public int Nu = 32, Nv = 16;

  @Override
  public int Nu() {
    return Nu;
  }

  @Override
  public int Nv() {
    return Nv;
  }

  @Override
  public boolean connectedByU() {
    return true;
  }

  @Override
  public boolean connectedByV() {
    return true;
  }
}
