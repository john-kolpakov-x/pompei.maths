package pompei.maths.lines_2d.util;

import javax.swing.JFrame;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Point;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.File;

public class FormPositionLook {
  private final File dir;

  public FormPositionLook(File dir) {
    this.dir = dir;
  }

  private Acceptor<Point> formPosition(String name) {
    return new FileAcceptor<>(dir.toPath().resolve(name + ".form-position.txt"), new PointSerializer());
  }

  private Acceptor<Dimension> formSize(String name) {
    return new FileAcceptor<>(dir.toPath().resolve(name + ".form-size.txt"), new DimensionSerializer());
  }

  public void register(JFrame frame, String name) {
    frame.addComponentListener(new ComponentAdapter() {
      @Override
      public void componentMoved(ComponentEvent e) {
        formPosition(name).setValue(frame.getLocation());
      }

      @Override
      public void componentResized(ComponentEvent e) {
        formSize(name).setValue(frame.getSize());
      }

      @Override
      public void componentShown(ComponentEvent e) {
        EventQueue.invokeLater(() -> {
          formPosition(name).get().ifPresent(frame::setLocation);
          formSize(name).get().ifPresent(frame::setSize);
        });
      }
    });

  }
}
