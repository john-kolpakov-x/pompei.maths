package pompei.maths.lines_2d.core;

import pompei.maths.lines_2d.model.Rect2d;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

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

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    doPaint((Graphics2D) g);
  }

  private void doPaint(Graphics2D g) {

    int padding = 5;
    int width = getWidth(), height = getHeight();
    g.setColor(Color.BLUE);
    g.drawRect(padding, padding, width - 2 * padding, height - 2 * padding);

    var viewPortRect = new Rect2d();
    viewPortRect.x = padding;
    viewPortRect.y = padding;
    viewPortRect.width = width - 2 * padding;
    viewPortRect.height = height - 2 * padding;

    viewPort.paint(g, viewPortRect);

  }
}
