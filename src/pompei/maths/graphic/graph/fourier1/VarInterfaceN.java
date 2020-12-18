package pompei.maths.graphic.graph.fourier1;

import pompei.maths.graphic.graph.VarInterface;

public class VarInterfaceN implements VarInterface {

  private final GraphFourier1 params;

  public VarInterfaceN(GraphFourier1 params) {
    this.params = params;
  }

  @Override
  public String strState() {
    return "N = " + params.N;
  }

  @Override
  public void up() {
    params.N++;
    normalize();
  }

  @Override
  public void upCtrl() {
    params.N++;
    normalize();
  }

  @Override
  public void down() {
    params.N--;
    normalize();
  }

  @Override
  public void downCtrl() {
    params.N--;
    normalize();
  }

  private void normalize() {
    if (params.N < 0) {
      params.N = 0;
    }
  }

}
