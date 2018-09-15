package pompei.maths.circles_with_lines;

import pompei.maths.circles_with_lines.model.Circle;
import pompei.maths.circles_with_lines.model.Line;

import java.util.List;

public class MovingCalculus {
  private List<Line> lineList;
  private List<Circle> circleList;

  public void register(List<Line> lineList, List<Circle> circleList) {
    this.lineList = lineList;
    this.circleList = circleList;
  }


}
