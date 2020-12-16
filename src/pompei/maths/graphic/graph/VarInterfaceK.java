package pompei.maths.graphic.graph;

public class VarInterfaceK implements VarInterface {

  private final GraphParams params;

  public VarInterfaceK(GraphParams params) {
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
