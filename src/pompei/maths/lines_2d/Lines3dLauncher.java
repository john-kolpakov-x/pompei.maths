package pompei.maths.lines_2d;

import pompei.maths.lines_2d.core.Lines2dMainFrame;
import pompei.maths.lines_2d.file_saver.AxesLook;
import pompei.maths.lines_2d.file_saver.FormPositionLook;

import javax.swing.JFrame;
import java.awt.EventQueue;
import java.io.File;

public class Lines3dLauncher {

  public static void main(String[] args) {

    var positionLook = new FormPositionLook(new File("build/saved_data/form_positions"));
    var axesLook = new AxesLook("build/saved_data/axes/");

    EventQueue.invokeLater(() -> {
      JFrame frame = new Lines2dMainFrame(axesLook);
      positionLook.register(frame, "MainForm");
      frame.setVisible(true);
    });

  }

}
