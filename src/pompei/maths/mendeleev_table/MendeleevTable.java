package pompei.maths.mendeleev_table;

import pompei.maths.mendeleev_table.elements.Align;
import pompei.maths.mendeleev_table.elements.Element;
import pompei.maths.mendeleev_table.elements.ElementSource;
import pompei.maths.mendeleev_table.geo.ElementGeo;
import pompei.maths.mendeleev_table.geo.ElementGeoDefault;
import pompei.maths.mendeleev_table.style.ElementNumberStyle;
import pompei.maths.mendeleev_table.style.ElementStyle;
import pompei.maths.mendeleev_table.style.def.ElementStyleDefault;

import java.awt.Graphics2D;
import java.util.HashMap;
import java.util.Map;

public class MendeleevTable {

  private final Map<Integer, Element> elementMap = new HashMap<>();

  private void addElement(Element element) {
    elementMap.put(element.number, element);
  }

  public MendeleevTable() {
    ElementSource elementSource = new ElementSource();
    elementSource.fill();
    elementMap.putAll(elementSource.elementMap);
  }

  public void paintTo(Graphics2D g) {

    ElementGeo geo = new ElementGeoDefault();
    ElementStyle style = new ElementStyleDefault();

    printElement(g, elementMap.get(7), geo, style, 100, 100);
    printElement(g, elementMap.get(10), geo, style, 150, 100 + geo.height() + 10);

  }

  private void printElement(Graphics2D g, Element e, ElementGeo geo, ElementStyle topStyle, int x0, int y0) {

    int cellWidth = geo.width();
    int cellHeight = geo.height();

    {
      g.setColor(topStyle.backColor(e.type));
      g.fillRect(x0, y0, cellWidth, cellHeight);
    }

    {
      ElementNumberStyle style = topStyle.elementNumber();


      String N = "" + e.number;

      int width = g.getFontMetrics().stringWidth(N);
      int height = g.getFontMetrics().getHeight();
      Align align = e.type.align;

      int x, y;
      switch (align) {
        case LEFT:
          x = x0 + style.horPadding();
          y = y0 + height + style.vertPadding();
          break;
        case RIGHT:
          x = x0 + cellWidth - style.horPadding() - width;
          y = y0 + height + style.vertPadding();
          break;
        default:
          throw new RuntimeException("Unknown align = " + align);
      }

      g.setColor(style.text().color());
      g.setFont(style.text().font());
      g.drawString(N, x, y);

    }


  }
}
