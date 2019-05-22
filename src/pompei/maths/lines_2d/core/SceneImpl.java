package pompei.maths.lines_2d.core;

import pompei.maths.lines_2d.model.Rect2d;
import pompei.maths.lines_2d.model.WorldRect2d;
import pompei.maths.lines_2d.model.WorldVec2d;
import pompei.maths.lines_2d.model.figures.Figure;
import pompei.maths.lines_2d.model.figures.FigureRect;

import java.util.stream.Stream;

public class SceneImpl implements Scene {


  @Override
  public Stream<Figure> selectContains(Rect2d worldRect) {

    FigureRect rect1 = new FigureRect();
    rect1.source = WorldRect2d.diagonal(WorldVec2d.of(10, 10), WorldVec2d.of(150, 100));

    FigureRect rect2 = new FigureRect();
    rect2.source = WorldRect2d.diagonal(WorldVec2d.of(-50, -70), WorldVec2d.of(-10, -10));

    FigureRect rect3 = new FigureRect();
    rect3.source = WorldRect2d.diagonal(WorldVec2d.of(-250, 150), WorldVec2d.of(-10, 10));

    return Stream.of(rect1, rect2, rect3);
  }

}
