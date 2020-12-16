package pompei.maths.graphic.operation.list;

import pompei.maths.graphic.operation.InitOperation;
import pompei.maths.graphic.operation.MouseOperation;
import pompei.maths.graphic.operation.MouseOperationCommand;
import pompei.maths.graphic.pen.Pen;
import pompei.maths.utils.Vec2;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

public class MoveCenterOperation implements MouseOperation {
  private final InitOperation io;

  public MoveCenterOperation(InitOperation io) {
    this.io = io;
    movedAt = io.cursor;
  }

  private Vec2 movedAt;
  private Vec2 startFrom = null;

  @Override
  public MouseOperationCommand keyPressed(KeyEvent e) {
    if (io.keyDefinition.keyForBreakOperation(e)) {
      return MouseOperationCommand.REMOVE_OPERATION;
    }
    if (io.keyDefinition.keyForMoveCenterOperation(e)) {
      return MouseOperationCommand.REMOVE_OPERATION;
    }
    System.out.println("hIH2dJi8mB :: keyPressed " + e);
    return MouseOperationCommand.NONE;
  }

  @Override
  public MouseOperationCommand mousePressed(MouseEvent e) {
    if (e.getButton() != MouseEvent.BUTTON1) {
      return MouseOperationCommand.REMOVE_OPERATION;
    }

    var point = Vec2.from(e.getPoint());

    if (startFrom == null) {
      startFrom = point;
      return MouseOperationCommand.NONE;
    }

    return MouseOperationCommand.NONE;
  }

  @Override
  public MouseOperationCommand mouseWheelMoved(MouseWheelEvent e) {
    return MouseOperationCommand.NONE;
  }

  @Override
  public MouseOperationCommand mouseReleased(MouseEvent e) {
    if (e.getButton() != MouseEvent.BUTTON1) {
      return MouseOperationCommand.REMOVE_OPERATION;
    }

    if (startFrom == null) {
      return MouseOperationCommand.REMOVE_OPERATION;
    }

    var point = Vec2.from(e.getPoint());

    var delta = startFrom.minus(point);

    io.converter.setDelta(io.converter.getDelta().minus(delta));

    startFrom = null;

    return MouseOperationCommand.NONE;
  }

  @Override
  public MouseOperationCommand mouseMoved(MouseEvent e) {
    movedAt = Vec2.from(e.getPoint());
    return MouseOperationCommand.NONE;
  }

  @Override
  public void paint(Pen pen) {
    pen.setConverting(false);
    var movedAt = this.movedAt;
    if (movedAt == null) {
      return;
    }

    var center = io.converter.toScreen(Vec2.xy(0, 0));

    var startFrom = this.startFrom;
    if (startFrom == null) {
      pen.setColor(io.styles.startOperationCursorColor());
      pen.line(center).to(movedAt);
      return;
    }

    pen.setColor(io.styles.startOperationCursorColor());
    pen.line(center).to(startFrom).to(movedAt);

    var delta = movedAt.minus(startFrom);

    pen.setColor(io.styles.operationCursorColor());
    pen.line(center).delta(delta);
  }

}
