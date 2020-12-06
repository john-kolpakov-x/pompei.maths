package pompei.maths.lines_2d.core;

import pompei.maths.lines_2d.model.Rect2d;
import pompei.maths.lines_2d.model.figures.Figure;

import java.util.stream.Stream;

public interface Scene {

  Stream<Figure> selectContains(Rect2d worldRect);

}
