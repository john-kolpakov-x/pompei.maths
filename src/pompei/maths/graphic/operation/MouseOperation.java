package pompei.maths.graphic.operation;

import pompei.maths.graphic.pen.Pen;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

public interface MouseOperation {

  MouseOperationCommand keyPressed(KeyEvent e);

  MouseOperationCommand mousePressed(MouseEvent e);

  MouseOperationCommand mouseReleased(MouseEvent e);

  MouseOperationCommand mouseMoved(MouseEvent e);

  MouseOperationCommand mouseWheelMoved(MouseWheelEvent e);

  void paint(Pen pen);

}
