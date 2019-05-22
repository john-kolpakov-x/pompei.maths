package pompei.maths.lines_2d.core;

import pompei.maths.lines_2d.file_saver.AxesLook;
import pompei.maths.lines_2d.model.ViewRect2d;
import pompei.maths.lines_2d.model.ViewVec2d;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseWheelEvent;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

public class MainContentPane extends JPanel {

  private ViewPort viewPort;

  public MainContentPane(ViewPort viewPort, AxesLook axesLook) {
    this.viewPort = viewPort;
    axesLook.load(viewPort.axes);

    activateTiming();

    addMouseWheelListener(new MouseAdapter() {
      @Override
      public void mouseWheelMoved(MouseWheelEvent e) {
        double scaleChangeFactor = 1.23;
        if (e.getWheelRotation() > 0) {
          scaleChangeFactor = 1 / scaleChangeFactor;
        }
        viewPort.axes.changeScale(scaleChangeFactor, ViewVec2d.of(e.getPoint()));
        axesLook.save(viewPort.axes);
      }
    });

    final AtomicReference<ViewVec2d> mouseDownedAt = new AtomicReference<>(null);
    final AtomicReference<ViewVec2d> offsetAtDowned = new AtomicReference<>(null);

    Consumer<ViewVec2d> moveTo = mousePoint -> {
      ViewVec2d mda = mouseDownedAt.get();
      ViewVec2d startedOffset = offsetAtDowned.get();
      if (mda == null || startedOffset == null) {
        return;
      }
      var delta = mousePoint.minus(mda);
      viewPort.axes.viewCenterOffset = startedOffset.plus(delta);
      axesLook.save(viewPort.axes);
    };

    addMouseMotionListener(new MouseMotionAdapter() {
      @Override
      public void mouseDragged(MouseEvent e) {
        moveTo.accept(ViewVec2d.of(e.getPoint()));
      }
    });

    addMouseListener(new MouseAdapter() {
      @Override
      public void mousePressed(MouseEvent e) {
        if (e.getClickCount() == 1) {
          if (e.getButton() == MouseEvent.BUTTON1) {
            mouseDownedAt.set(ViewVec2d.of(e.getPoint()));
            offsetAtDowned.set(viewPort.axes.viewCenterOffset);
            return;
          }
          if (e.getButton() == MouseEvent.BUTTON3) {
            mouseDownedAt.set(null);
            var offset = offsetAtDowned.getAndSet(null);
            if (offset != null) {
              viewPort.axes.viewCenterOffset = offset;
            }
            return;
          }
        }
      }

      @Override
      public void mouseReleased(MouseEvent e) {
        if (e.getClickCount() == 0) {
          if (e.getButton() == MouseEvent.BUTTON1) {
            ViewVec2d mda = mouseDownedAt.getAndSet(null);
            offsetAtDowned.set(null);
            if (mda != null) {
              moveTo.accept(ViewVec2d.of(e.getPoint()));
            }
            return;
          }
        }
      }
    });

  }

  private void activateTiming() {
    new Thread(() -> {

      boolean flag = true;

      while (isDisplayable() || flag) {

        if (isDisplayable()) {
          flag = false;
        }

        try {
          Thread.sleep(1000 / 25);
        } catch (InterruptedException e) {
          break;
        }

        EventQueue.invokeLater(this::repaint);

      }

      System.out.println("Timing is finished");

    }).start();
  }

  final int padding = 5;

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);

    int width = getWidth(), height = getHeight();
    g.setColor(Color.BLUE);
    g.drawRect(padding, padding, width - 2 * padding, height - 2 * padding);

    doPaint(new DrawerGraphics(g));

  }

  private void doPaint(Drawer g) {

    var viewPortRect = new ViewRect2d();
    viewPortRect.x = padding;
    viewPortRect.y = padding;
    viewPortRect.width = getWidth() - 2 * padding;
    viewPortRect.height = getHeight() - 2 * padding;

    viewPort.paint(g, viewPortRect);

  }
}
