package pompei.maths.lines_2d.core;

import pompei.maths.lines_2d.model.ViewRect2d;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Graphics;

public class MainContentPane extends JPanel {

  private ViewPort viewPort;

  public MainContentPane(ViewPort viewPort) {
    this.viewPort = viewPort;

    activateTiming();

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
