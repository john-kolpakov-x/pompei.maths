package pompei.maths.graphic.graph.fourier1;

import pompei.maths.graphic.graph.VarInterface;

public class VarInterfaceA implements VarInterface {

  private final GraphFourier1 params;

  public VarInterfaceA(GraphFourier1 params) {
    this.params = params;
  }

  @Override
  public String strState() {
    return "A = " + params.A;
  }

  @Override
  public void up() {
    params.A += 0.1;
  }

  @Override
  public void upCtrl() {
    params.A += 1;
  }

  @Override
  public void down() {
    params.A -= 0.1;
  }

  @Override
  public void downCtrl() {
    params.A -= 1;
  }

}
