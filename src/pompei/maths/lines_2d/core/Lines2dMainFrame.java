package pompei.maths.lines_2d.core;

import javax.swing.JFrame;

public class Lines2dMainFrame extends JFrame {

  public Lines2dMainFrame() {
    setTitle("Lines 2D");

    var viewPort = new ViewPort();

    MainContentPane contentPane = new MainContentPane(viewPort);

    setContentPane(contentPane);

    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

    setLocation(100, 300);
    setSize(800, 400);

  }
}
