package pompei.maths.graphic.graph;

public class VarInterfaceN implements VarInterface {

  private final GraphParams params;

  public VarInterfaceN(GraphParams params) {
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
