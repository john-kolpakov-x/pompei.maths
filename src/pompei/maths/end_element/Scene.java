package pompei.maths.end_element;

import java.util.ArrayList;
import java.util.List;

public class Scene {
  public final List<Point> pointList = new ArrayList<>();
  public final List<Side> sideList = new ArrayList<>();
  public final List<Cell> cellList = new ArrayList<>();

  public void drawSides(DrawDestination destination) {

    sideList.stream()
        .peek(s -> destination.setSideType(s.type))
        .map(s -> Pair.with(pointList.get(s.p1), pointList.get(s.p2)))
        .forEach(p -> destination.vector(p.a.x, p.a.y, p.b.x, p.b.y));

  }

  public void drawCells(DrawDestination destination, DrawCellsFilter filter) {
    for (int cellIndex = 0; cellIndex < cellList.size(); cellIndex++) {
      Cell cell = cellList.get(cellIndex);
      if (!filter.drawCell(cell, cellIndex)) continue;
      destination.setSideType(SideType.WORKING);
      destination.line(cell.x1, cell.y1, cell.x2, cell.y2);
      destination.line(cell.x2, cell.y1, cell.x1, cell.y2);
      if (filter.drawPointLabels(cell)) {
        destination.labelPoint(cell.x1, cell.y1, "" + cell.p1, LabelPlace.TOP_RIGHT);
        destination.labelPoint(cell.x2, cell.y1, "" + cell.p2, LabelPlace.TOP_LEFT);
        destination.labelPoint(cell.x2, cell.y2, "" + cell.p3, LabelPlace.BOTTOM_LEFT);
        destination.labelPoint(cell.x1, cell.y2, "" + cell.p4, LabelPlace.BOTTOM_RIGHT);
      }

      if (filter.drawCellLabel(cell, cellIndex)) {
        destination.setSideType(SideType.LEFT_WALL);
        double xc = (cell.x1 + cell.x2) / 2, yc = (cell.y1 + cell.y2) / 2;
        destination.labelPoint(xc, yc, "" + cellIndex, LabelPlace.INSIDE);
      }

      for (int i = 0; i < cell.sides.size(); i++) {
        if (filter.drawSideWithIndex(cell, i)) {
          SideRef side = cell.sides.get(i);
          Point p1 = pointList.get(side.side.p1);
          Point p2 = pointList.get(side.side.p2);
          double xc = (p1.x + p2.x) / 2, yc = (p1.y + p2.y) / 2;
          destination.setSideType(side.side.type);
          destination.labelPoint(xc, yc, "" + i + (side.right ? "R" : "L"), placeFromCellIndex(i));
          destination.vector(p1.x, p1.y, p2.x, p2.y);
        }
      }
    }
  }

  private LabelPlace placeFromCellIndex(int i) {
    switch (i) {
      case 0:
        return LabelPlace.TOP;
      case 1:
        return LabelPlace.LEFT;
      case 2:
        return LabelPlace.BOTTOM;
      case 3:
        return LabelPlace.RIGHT;
    }
    return LabelPlace.INSIDE;
  }
}
