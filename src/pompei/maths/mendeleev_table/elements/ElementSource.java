package pompei.maths.mendeleev_table.elements;

import java.util.HashMap;
import java.util.Map;

public class ElementSource {

  public final Map<Integer, Element> elementMap = new HashMap<>();

  private void addElement(Element element) {
    elementMap.put(element.number, element);
  }

  //   ⁰¹²³⁴⁵⁶⁷⁸⁹
  public void fill() {
    {
      var e = new Element(1);
      e.type = ElementType.NOT_IRON;
      e.sign = "H";
      e.name = "Водород";
      e.weight = "1,0079";
      e.lastLayer = "1s¹";
      e.layers = "1";
      addElement(e);
    }
    {
      var e = new Element(2);
      e.type = ElementType.NOT_IRON;
      e.sign = "He";
      e.name = "Гелий";
      e.weight = "4,0026";
      e.lastLayer = "1s²";
      e.layers = "2";
      addElement(e);
    }
    {
      var e = new Element(3);
      e.type = ElementType.IRON;
      e.sign = "Li";
      e.name = "Литий";
      e.weight = "6,94";
      e.lastLayer = "2s¹";
      e.layers = "2 1";
      addElement(e);
    }
    {
      var e = new Element(4);
      e.type = ElementType.IRON;
      e.sign = "Be";
      e.name = "Бериллий";
      e.weight = "9,012";
      e.lastLayer = "2s²";
      e.layers = "2 2";
      addElement(e);
    }
    {
      var e = new Element(5);
      e.type = ElementType.NOT_IRON;
      e.sign = "B";
      e.name = "Бор";
      e.weight = "10,811";
      e.lastLayer = "2s²2p¹";
      e.layers = "2 3";
      addElement(e);
    }
    {
      var e = new Element(6);
      e.type = ElementType.NOT_IRON;
      e.sign = "C";
      e.name = "Углерод";
      e.weight = "12,011";
      e.lastLayer = "2s²2p²";
      e.layers = "2 4";
      addElement(e);
    }
    {
      var e = new Element(7);
      e.type = ElementType.NOT_IRON;
      e.sign = "N";
      e.name = "Азот";
      e.weight = "14,0067";
      e.lastLayer = "2s²2p³";
      e.layers = "2 5";
      addElement(e);
    }
    {
      var e = new Element(10);
      e.type = ElementType.NOT_IRON;
      e.sign = "Ne";
      e.name = "Неон";
      e.weight = "20,179";
      e.lastLayer = "2s²2p⁶";
      e.layers = "2 8";
      addElement(e);
    }
  }
}
