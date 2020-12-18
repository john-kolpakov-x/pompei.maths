package pompei.maths.graphic.graph.fourier1;

import pompei.maths.graphic.graph.VarInterface;

public class VarInterfaceK implements VarInterface {

  private final GraphFourier1 params;

  public VarInterfaceK(GraphFourier1 params) {
    this.params = params;
  }

  @Override
  public String strState() {
    return "k = " + params.k;
  }

  @Override
  public void up() {
    params.k += 0.01;
    normalize();
  }

  @Override
  public void upCtrl() {
    params.k += 0.1;
    normalize();
  }

  @Override
  public void down() {
    params.k -= 0.01;
    normalize();
  }

  @Override
  public void downCtrl() {
    params.k -= 0.1;
    normalize();
  }

  private void normalize() {
    if (params.k <= 0) {
      params.k = 0;
      return;
    }
    if (params.k > 1) {
      params.k = 1;
      return;
    }
  }

}
