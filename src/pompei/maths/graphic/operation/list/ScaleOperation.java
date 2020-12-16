package pompei.maths.graphic.operation.list;

import pompei.maths.graphic.operation.InitOperation;
import pompei.maths.graphic.operation.MouseOperation;
import pompei.maths.graphic.operation.MouseOperationCommand;
import pompei.maths.graphic.pen.Pen;
import pompei.maths.utils.Vec2;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

public class ScaleOperation implements MouseOperation {

  private final InitOperation io;

  public ScaleOperation(InitOperation io) {
    this.io = io;
    movedAt = io.cursor;
  }

  private Vec2 movedAt;

  @Override
  public MouseOperationCommand keyPressed(KeyEvent e) {

    if (io.keyDefinition.keyForBreakOperation(e)) {
      return MouseOperationCommand.REMOVE_OPERATION;
    }

    if (io.keyDefinition.keyForScaleOperation(e)) {
      return MouseOperationCommand.REMOVE_OPERATION;
    }

    return MouseOperationCommand.NONE;
  }

  @Override
  public MouseOperationCommand mousePressed(MouseEvent e) {

    if (e.getButton() != MouseEvent.BUTTON1) {
      return MouseOperationCommand.REMOVE_OPERATION;
    }

    return MouseOperationCommand.NONE;
  }

  @Override
  public MouseOperationCommand mouseReleased(MouseEvent e) {
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
    if (movedAt != null) {
      pen.setColor(io.styles.startOperationCursorColor());
      var line = pen.line(movedAt);
      line.moveDelta(0, -25);
      var p1 = line.current();
      line.delta(0, 50);
      var p2 = line.current();

      pen.line(p1).moveDelta(-7, 7).delta(7, -7).delta(7, 7);
      pen.line(p2).moveDelta(-7, -7).delta(7, 7).delta(7, -7);

      pen.line(movedAt).moveDelta(-3, -3).delta(6, 6).moveDelta(0, -6).delta(-6, 6);
    }

  }

  @SuppressWarnings("FieldCanBeLocal")
  private final double factor = 1.3;

  @Override
  public MouseOperationCommand mouseWheelMoved(MouseWheelEvent e) {

    if (e.getWheelRotation() >= 1) {
      io.converter.kx *= factor;
      io.converter.ky *= factor;
      return MouseOperationCommand.NONE;
    }

    if (e.getWheelRotation() <= -1) {
      io.converter.kx /= factor;
      io.converter.ky /= factor;
      return MouseOperationCommand.NONE;
    }

    System.out.println("6VUBXo03yn :: mouseWheelMoved " + e);
    return MouseOperationCommand.NONE;
  }

}
