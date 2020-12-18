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
import java.util.List;

public class ChangeVarOperation implements MouseOperation {

  private final InitOperation io;
  private final List<VarInterface> varInterfaceList;
  private int i = 0;

  public ChangeVarOperation(InitOperation io, List<VarInterface> varInterfaceList) {
    this.io = io;
    this.varInterfaceList = varInterfaceList;
    cursor = io.cursor;
  }

  Vec2 cursor;

  @Override
  public MouseOperationCommand keyPressed(KeyEvent e) {
    if (io.keyDefinition.keyForBreakOperation(e)) {
      return MouseOperationCommand.REMOVE;
    }
    if (e.getKeyCode() == KeyEvent.VK_BACK_QUOTE) {
      i++;
      if (i >= varInterfaceList.size()) {
        i = 0;
      }
      return MouseOperationCommand.NONE;
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
    var v = varInterfaceList.get(i);

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
    pen.print(cursor, varInterfaceList.get(i).strState());
  }
}
