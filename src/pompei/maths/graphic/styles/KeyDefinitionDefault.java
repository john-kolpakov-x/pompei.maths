package pompei.maths.graphic.styles;

import java.awt.event.KeyEvent;

public class KeyDefinitionDefault implements KeyDefinition {

  @Override
  public boolean keyForBreakOperation(KeyEvent e) {
    return e.getKeyCode() == KeyEvent.VK_ESCAPE;
  }

  @Override
  public boolean keyForMoveCenterOperation(KeyEvent e) {
    return e.getKeyCode() == KeyEvent.VK_G;
  }

  @Override
  public boolean keyForScaleOperation(KeyEvent e) {
    return e.getKeyCode() == KeyEvent.VK_S;
  }

}
