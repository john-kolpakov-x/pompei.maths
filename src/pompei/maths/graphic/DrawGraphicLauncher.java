package pompei.maths.graphic;

import pompei.maths.graphic.graph.GraphPainter;
import pompei.maths.graphic.graph.GraphParams;
import pompei.maths.graphic.styles.KeyDefinition;
import pompei.maths.graphic.styles.KeyDefinitionDefault;
import pompei.maths.graphic.styles.Styles;
import pompei.maths.graphic.styles.StylesDefault;
import pompei.maths.lines_2d.file_saver.FormPositionLook;
import pompei.maths.lines_2d.file_saver.ModelFileSaver;
import pompei.maths.lines_2d.file_saver.SavableThread;

import javax.swing.JFrame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.concurrent.atomic.AtomicBoolean;

public class DrawGraphicLauncher {
  public static void main(String[] args) {
    new DrawGraphicLauncher().exec();
  }

  private void exec() {

    var paramDir = "build/" + getClass().getSimpleName();

    var mainFormDir = new File(paramDir + "/store");
    var mainFormLook = new FormPositionLook(mainFormDir);

    var savableThread = new SavableThread();

    JFrame frame = new JFrame();
    frame.setTitle("Graphics");
    mainFormLook.register(frame, "main-form");

    frame.setSize(800, 600);
    frame.setLocation(10, 10);

    frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);


    GraphParams graphParams = new GraphParams();

    {
      var modelFileSaver = new ModelFileSaver(graphParams, new File(paramDir + "/graphParams"));
      modelFileSaver.init();
      savableThread.register(modelFileSaver);
    }

    var realScreenConverter = new RealScreenConverter();
    {
      var modelFileSaver = new ModelFileSaver(realScreenConverter, new File(paramDir + "/realScreenConverter"));
      modelFileSaver.init();
      savableThread.register(modelFileSaver);
    }
    KeyDefinition keyDefinition = new KeyDefinitionDefault();
    Styles styles = new StylesDefault();
    var graphPainter = new GraphPainter(styles, graphParams);

    var drawPanel = new DrawPanel(graphPainter, realScreenConverter, styles, keyDefinition, graphParams);
    frame.addKeyListener(drawPanel.eventAdapter);
    frame.setContentPane(drawPanel);

    AtomicBoolean working = new AtomicBoolean(true);

    var repaintThread = new Thread(() -> {
      while (working.get()) {

        try {
          //noinspection BusyWait
          Thread.sleep(1000 / 24);
        } catch (InterruptedException e) {
          throw new RuntimeException("ERD4SaIMXd", e);
        }

        drawPanel.repaint();

      }

      System.out.println("a3zZL4pKc5 :: Repaint Thread Closed");
    });

    repaintThread.start();

    frame.addWindowListener(new WindowAdapter() {
      @Override
      public void windowClosed(WindowEvent e) {
        working.set(false);
        savableThread.stop();
        System.out.println("9CepT5mU8X :: Finish");
      }

      @Override
      public void windowClosing(WindowEvent e) {
        frame.dispose();
      }

    });

    frame.setVisible(true);

  }
}
