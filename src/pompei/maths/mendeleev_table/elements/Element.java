package pompei.maths.mendeleev_table.elements;

public class Element {
  public final int number;
  public String sign;
  public String name;
  public String weight;
  public String lastLayer;
  public String layers;
  public ElementType type;

  public Element(int number) {
    this.number = number;
  }
}
