package pompei.maths.mendeleev_table.style;

import pompei.maths.mendeleev_table.elements.ElementType;

import java.awt.Color;

public interface ElementStyle {

  Color backColor(ElementType type);

  ElementNumberStyle elementNumber();

}
