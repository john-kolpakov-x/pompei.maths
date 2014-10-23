package pompei.maths.syms.visitors;

import java.awt.Graphics2D;

public interface GraphicsSource {
  Graphics2D getGraphics(int level);
  
  ConfDiv div();
  
  ConfPower power();
  
  float ascendingMiddleProportion(int level);
  
  void closeAll();
}
