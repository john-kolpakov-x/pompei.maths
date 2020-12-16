package pompei.maths.graphic.styles;

import java.awt.Color;

public class StylesDefault implements Styles {

  @Override
  public Color coordinatesColor() {
    return Color.LIGHT_GRAY;
  }

  @Override
  public Color startOperationCursorColor() {
    return Color.GREEN.darker();
  }

  @Override
  public Color operationCursorColor() {
    return startOperationCursorColor().darker();
  }
}
