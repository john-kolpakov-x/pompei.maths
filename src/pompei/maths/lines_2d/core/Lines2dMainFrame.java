package pompei.maths.lines_2d.core;

import pompei.maths.lines_2d.file_saver.AxesLook;

import javax.swing.JFrame;

public class Lines2dMainFrame extends JFrame {

  public Lines2dMainFrame(AxesLook axesLook) {
    setTitle("Lines 2D");

    var scene = new SceneImpl();

    var viewPort = new ViewPort(scene);

    MainContentPane contentPane = new MainContentPane(viewPort, axesLook);

    setContentPane(contentPane);

    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

    setLocation(100, 300);
    setSize(800, 400);

  }
}
