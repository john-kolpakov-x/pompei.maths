package pompei.maths.circles_with_lines;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

public class FormLauncher extends JPanel {
  public static void main(String[] args) {
    SwingUtilities.invokeLater(() -> {

      JFrame frame = new JFrame();
      frame.setTitle("asd");
      frame.setSize(800, 600);
      frame.setLocation(1000, 50);
      frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

      ViewPanel viewPanel = new ViewPanel();

      frame.addKeyListener(new KeyAdapter() {
        @Override
        public void keyReleased(KeyEvent e) {
          viewPanel.keyReleased(e);
        }

        @Override
        public void keyPressed(KeyEvent e) {
          viewPanel.keyPressed(e);
        }
      });

      frame.setContentPane(viewPanel);
      frame.setVisible(true);
    });
  }

  @Override
  public void paint(Graphics g) {
    paint2D((Graphics2D) g);
  }

  private void paint2D(Graphics2D g) {
    g.setColor(Color.WHITE);
    g.fillRect(0, 0, getWidth(), getHeight());
    g.setColor(Color.BLACK);
    g.drawLine(0, 0, 10, 0);
    g.drawLine(0, 0, 0, 10);
    g.drawLine(0, 0, getWidth(), getHeight());
    g.drawLine(getWidth() - 1, getHeight() - 1, getWidth() - 10, getHeight() - 1);
    g.drawLine(getWidth() - 1, getHeight() - 1, getWidth() - 1, getHeight() - 10);

    drawCool(g, getWidth() / 2, getHeight() / 2);
  }

  private final int W = 400, H = 400;
  private final int net[] = new int[W * H];

  private final BufferedImage image = new BufferedImage(W, H, BufferedImage.TYPE_INT_ARGB);

  private void drawCool(Graphics2D g, int X, int Y) {

    for (int y = 0; y < H; y++) {
      for (int x = 0; x < W; x++) {
        net[x + y * W] = calcColor(x, y, W, H);
      }
    }

    image.setRGB(0, 0, W, H, net, 0, W);

    g.setColor(Color.BLACK);
    g.drawImage(image, X - W / 2, Y - H / 2, null);
  }

  private static int rgb(int red, int green, int blue) {
    return (blue & 0xFF) | ((green & 0xFF) << 8) | ((red & 0xFF) << 16) | (0xFF << 24);
  }

  private static int calcColor(int x, int y, int w, int h) {

    float u = (float) x / (float) w, v = (float) y / (float) h;

    u = u * 2 - 1;
    v = 1 - v * 2;

    float r = (float) Math.sqrt(u * u + v * v);

    return Color.HSBtoRGB(r * 7 / 8, 1, 1);
  }
}
