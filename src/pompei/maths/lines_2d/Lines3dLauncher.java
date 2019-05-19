package pompei.maths.lines_2d;

import pompei.maths.lines_2d.core.Lines2dMainFrame;
import pompei.maths.lines_2d.util.FormPositionLook;

import javax.swing.JFrame;
import java.awt.EventQueue;
import java.io.File;

public class Lines3dLauncher {

  public static void main(String[] args) {

    var positionLook = new FormPositionLook(new File("build/form_positions"));

    EventQueue.invokeLater(() -> {
      JFrame frame = new Lines2dMainFrame();
      positionLook.register(frame, "MainForm");
      frame.setVisible(true);
    });

  }

}
