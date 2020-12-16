package pompei.maths.graphic.styles;

import java.awt.event.KeyEvent;

public interface KeyDefinition {
  boolean keyForMoveCenterOperation(KeyEvent e);

  boolean keyForScaleOperation(KeyEvent e);

  boolean keyForBreakOperation(KeyEvent e);
}
