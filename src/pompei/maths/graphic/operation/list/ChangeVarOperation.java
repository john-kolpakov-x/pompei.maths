package pompei.maths.graphic.operation.list;

import pompei.maths.graphic.graph.VarInterface;
import pompei.maths.graphic.operation.InitOperation;
import pompei.maths.graphic.operation.MouseOperation;
import pompei.maths.graphic.operation.MouseOperationCommand;
import pompei.maths.graphic.pen.Pen;
import pompei.maths.utils.Vec2;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

public class ChangeVarOperation implements MouseOperation {

  private final InitOperation io;
  private final VarInterface v;

  public ChangeVarOperation(InitOperation io, VarInterface v) {
    this.io = io;
    this.v = v;
    cursor = io.cursor;
  }

  Vec2 cursor;

  @Override
  public MouseOperationCommand keyPressed(KeyEvent e) {
    if (io.keyDefinition.keyForBreakOperation(e)) {
      return MouseOperationCommand.REMOVE;
    }
    return MouseOperationCommand.SKIP;
  }

  @Override
  public MouseOperationCommand mousePressed(MouseEvent e) {
    return MouseOperationCommand.REMOVE;
  }

  @Override
  public MouseOperationCommand mouseReleased(MouseEvent e) {
    return MouseOperationCommand.REMOVE;
  }

  @Override
  public MouseOperationCommand mouseMoved(MouseEvent e) {
    cursor = Vec2.from(e.getPoint());
    return MouseOperationCommand.NONE;
  }

  @Override
  public MouseOperationCommand mouseWheelMoved(MouseWheelEvent e) {

    if (e.getWheelRotation() <= -1) {

      if (e.isControlDown()) {
        v.upCtrl();
      } else {
        v.up();
      }

      return MouseOperationCommand.NONE;
    }

    if (e.getWheelRotation() >= +1) {

      if (e.isControlDown()) {
        v.downCtrl();
      } else {
        v.down();
      }

      return MouseOperationCommand.NONE;
    }

    return MouseOperationCommand.NONE;
  }

  @Override
  public void paint(Pen pen) {
    pen.setConverting(false);
    pen.setColor(Color.BLACK.brighter());
    pen.print(cursor, v.strState());
  }
}
