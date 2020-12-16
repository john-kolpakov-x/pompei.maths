package pompei.maths.graphic.operation;

import pompei.maths.graphic.RealScreenConverter;
import pompei.maths.graphic.styles.KeyDefinition;
import pompei.maths.graphic.styles.Styles;
import pompei.maths.utils.Vec2;

public class InitOperation {
  public final RealScreenConverter converter;
  public final Styles styles;
  public final Vec2 cursor;
  public final KeyDefinition keyDefinition;

  public InitOperation(RealScreenConverter converter, Styles styles,
                       Vec2 cursor, KeyDefinition keyDefinition) {
    this.converter = converter;
    this.styles = styles;
    this.cursor = cursor;
    this.keyDefinition = keyDefinition;
  }

}
