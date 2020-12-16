package pompei.maths.graphic.graph;

public class VarInterfaceA implements VarInterface {

  private final GraphParams params;

  public VarInterfaceA(GraphParams params) {
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
