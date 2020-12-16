package pompei.maths.graphic;

import pompei.maths.graphic.graph.GraphPainter;
import pompei.maths.graphic.graph.GraphParams;
import pompei.maths.graphic.graph.VarInterfaceA;
import pompei.maths.graphic.graph.VarInterfaceK;
import pompei.maths.graphic.graph.VarInterfaceN;
import pompei.maths.graphic.operation.EventAdapter;
import pompei.maths.graphic.operation.InitOperation;
import pompei.maths.graphic.operation.MouseOperation;
import pompei.maths.graphic.operation.MouseOperationCommand;
import pompei.maths.graphic.operation.list.ChangeVarOperation;
import pompei.maths.graphic.operation.list.MoveCenterOperation;
import pompei.maths.graphic.operation.list.ScaleOperation;
import pompei.maths.graphic.pen.GraphicsPen;
import pompei.maths.graphic.pen.Pen;
import pompei.maths.graphic.styles.KeyDefinition;
import pompei.maths.graphic.styles.Styles;
import pompei.maths.utils.Vec2;

import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

public class DrawPanel extends JPanel {

  private final GraphPainter graphPainter;
  private final RealScreenConverter realScreenConverter;
  private final Styles styles;
  private final KeyDefinition keyDefinition;
  private final GraphParams graphParams;

  private MouseOperation mouseOperation = null;
  private Vec2 cursor = Vec2.xy(0, 0);

  public final EventAdapter eventAdapter = new EventAdapter() {

    @Override
    public void mousePressed(MouseEvent e) {
      if (mouseOperation != null) {
        doCommand(mouseOperation.mousePressed(e));
        return;
      }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
      if (mouseOperation != null) {
        doCommand(mouseOperation.mouseReleased(e));
        return;
      }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
      cursor = Vec2.from(e.getPoint());
      if (mouseOperation != null) {
        doCommand(mouseOperation.mouseMoved(e));
        return;
      }
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
      if (mouseOperation != null) {
        if (doCommand(mouseOperation.mouseWheelMoved(e))) {
          return;
        }
      }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
      cursor = Vec2.from(e.getPoint());
      if (mouseOperation != null) {
        doCommand(mouseOperation.mouseMoved(e));
        return;
      }
    }

    private InitOperation initOperation() {
      return new InitOperation(realScreenConverter, styles, cursor, keyDefinition);
    }

    @Override
    public void keyPressed(KeyEvent e) {
      if (mouseOperation != null) {
        if (doCommand(mouseOperation.keyPressed(e))) {
          return;
        }
      }
      if (keyDefinition.keyForMoveCenterOperation(e)) {
        mouseOperation = new MoveCenterOperation(initOperation());
        return;
      }
      if (keyDefinition.keyForScaleOperation(e)) {
        mouseOperation = new ScaleOperation(initOperation());
        return;
      }
      if (e.getKeyCode() == KeyEvent.VK_1) {
        mouseOperation = new ChangeVarOperation(initOperation(), new VarInterfaceA(graphParams));
        return;
      }
      if (e.getKeyCode() == KeyEvent.VK_2) {
        mouseOperation = new ChangeVarOperation(initOperation(), new VarInterfaceN(graphParams));
        return;
      }
      if (e.getKeyCode() == KeyEvent.VK_3) {
        mouseOperation = new ChangeVarOperation(initOperation(), new VarInterfaceK(graphParams));
        return;
      }
      System.out.println("MA93HOyw0n :: keyPressed " + e);
    }

    private boolean doCommand(MouseOperationCommand mouseOperationCommand) {
      switch (mouseOperationCommand) {

        case NONE:
          return true;

        case REMOVE:
          mouseOperation = null;
          return true;

        case SKIP:
          return false;

        case REMOVE_AND_SKIP:
          mouseOperation = null;
          return false;

        default:
          throw new RuntimeException("04eulAz160 :: Unknown command " + mouseOperationCommand);
      }
    }

  };

  public DrawPanel(GraphPainter graphPainter, RealScreenConverter realScreenConverter,
                   Styles styles, KeyDefinition keyDefinition, GraphParams graphParams) {
    this.graphPainter = graphPainter;
    this.realScreenConverter = realScreenConverter;
    this.styles = styles;
    this.keyDefinition = keyDefinition;
    this.graphParams = graphParams;
    addMouseListener(eventAdapter);
    addMouseMotionListener(eventAdapter);
    addMouseWheelListener(eventAdapter);
  }

  private Pen createPen(Graphics g) {
    realScreenConverter.screenWidth = getWidth();
    realScreenConverter.screenHeight = getHeight();
    return new GraphicsPen((Graphics2D) g, realScreenConverter);
  }

  @Override
  public void paint(Graphics g) {
    super.paint(g);
    graphPainter.paint(createPen(g));
    if (mouseOperation != null) {
      mouseOperation.paint(createPen(g));
    }
  }

}
