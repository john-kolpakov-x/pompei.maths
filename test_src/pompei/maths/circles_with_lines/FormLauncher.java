package pompei.maths.circles_with_lines;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class FormLauncher {
  public static void main(String[] args) {
    SwingUtilities.invokeLater(() -> {

      JFrame frame = new JFrame();
      frame.setTitle("Circles with Lines");
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
}
