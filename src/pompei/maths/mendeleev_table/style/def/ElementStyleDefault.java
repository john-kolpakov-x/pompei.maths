package pompei.maths.mendeleev_table.style.def;

import pompei.maths.mendeleev_table.elements.ElementType;
import pompei.maths.mendeleev_table.style.ElementNumberStyle;
import pompei.maths.mendeleev_table.style.ElementStyle;
import pompei.maths.mendeleev_table.style.TextStyle;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;
import java.io.InputStream;

public class ElementStyleDefault implements ElementStyle {

  @Override
  public Color backColor(ElementType type) {
    switch (type) {

      case NOT_IRON:
        return new Color(254, 255, 67);

      case HALF_IRON:
        return new Color(254, 136, 214);

      case IRON:
        return new Color(106, 218, 250);

      case ACTINOID:
      case LANTANOID:
        return new Color(85, 177, 66);

      default:
        throw new RuntimeException("Unknown type = " + type);

    }
  }

  private final Font Roboto_Regular;

  {
    try {
      String resourceName = "roboto/Roboto-Regular.ttf";
      InputStream fontStream = getClass().getResourceAsStream(resourceName);
      if (fontStream == null) {
        throw new RuntimeException("No resource " + resourceName);
      }
      Roboto_Regular = Font.createFont(Font.TRUETYPE_FONT, fontStream);
    } catch (FontFormatException | IOException e) {
      throw new RuntimeException(e);
    }
  }

  public static void main(String[] args) throws Exception {
    ElementStyleDefault e = new ElementStyleDefault();
    System.out.println(e.Roboto_Regular);
  }

  @Override
  public ElementNumberStyle elementNumber() {
    return new ElementNumberStyle() {
      @Override
      public TextStyle text() {
        return new TextStyle() {
          @Override
          public Font font() {
            return Roboto_Regular.deriveFont(17f);
          }

          @Override
          public Color color() {
            return Color.BLACK;
          }
        };
      }

      @Override
      public int horPadding() {
        return 5;
      }

      @Override
      public int vertPadding() {
        return 0;
      }
    };
  }

}
