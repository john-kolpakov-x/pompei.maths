package pompei.maths.lines_2d.file_saver;

import pompei.maths.lines_2d.core.Axes;
import pompei.maths.lines_2d.model.ViewVec2d;

import java.nio.file.Path;

public class AxesLook {

  private final FileAcceptor<ViewVec2d> viewCenterOffset;
  private final FileAcceptor<Double> scale;

  public AxesLook(String filePathPrefix) {
    viewCenterOffset = new FileAcceptor<>(Path.of(filePathPrefix + "viewCenterOffset"), Vec2dSerializer.newView());
    scale = new FileAcceptor<>(Path.of(filePathPrefix + "scale"), new DoubleSerializer());
  }

  public void save(Axes axes) {
    viewCenterOffset.setValue(axes.viewCenterOffset);
    scale.setValue(axes.scale);
  }

  public void load(Axes axes) {
    viewCenterOffset.get().ifPresent(value -> axes.viewCenterOffset = value);
    scale.get().ifPresent(value -> axes.scale = value);
  }

}
