package pompei.maths.end_element;

import java.util.HashMap;
import java.util.Map;

public class Task1 {
  private Scene scene;

  public Task1(Scene scene) {
    this.scene = scene;
  }

  @SuppressWarnings("PointlessArithmeticExpression")
  public void createField(double dx, double dy, int Nx, int Ny) {

    PointIndex pointIndex = new PointIndex(dx * 0.99, dy * 0.99);

    for (int j = 0; j < Ny + 1; j++) {
      for (int i = 0; i < Nx + 1; i++) {

        double x = dx * i;
        double y = dy * j;

        pointIndex.addIndex(x, y, scene.pointList.size());
        scene.pointList.add(new Point(x, y));

      }
    }

    final Map<IntPair, Side> sideIndex = new HashMap<>();

    for (int j = 0; j <= Ny; j++) {
      for (int i = 0; i <= Nx; i++) {
        if (i < Nx) {
          SideType sideType = SideType.WORKING;
          if (j == 0) sideType = SideType.RIGHT_WALL;
          else if (j == Ny) sideType = SideType.LEFT_WALL;
          Side side = new Side(j * (Nx + 1) + i, j * (Nx + 1) + i + 1, sideType);
          scene.sideList.add(side);
          sideIndex.put(new IntPair(side.p1, side.p2), side);
        }
        if (j < Ny) {
          SideType sideType = SideType.WORKING;
          if (i == 0) sideType = SideType.LEFT_INPUT;
          else if (i == Nx) sideType = SideType.RIGHT_OUTPUT;
          Side side = new Side(j * (Nx + 1) + i, (j + 1) * (Nx + 1) + i, sideType);
          scene.sideList.add(side);
          sideIndex.put(new IntPair(side.p1, side.p2), side);
        }
      }
    }

    for (int j = 0; j < Ny; j++) {
      for (int i = 0; i < Nx; i++) {
        Cell cell = new Cell(dx * i, dy * j, dx * (i + 1), dy * (j + 1));
        scene.cellList.add(cell);
        cell.p1 = pointIndex.getNearestIndex(cell.x1, cell.y1);
        cell.p2 = pointIndex.getNearestIndex(cell.x2, cell.y1);
        cell.p3 = pointIndex.getNearestIndex(cell.x2, cell.y2);
        cell.p4 = pointIndex.getNearestIndex(cell.x1, cell.y2);

        cell.sides.add(findSide(cell.p1, cell.p2, sideIndex));
        cell.sides.add(findSide(cell.p2, cell.p3, sideIndex));
        cell.sides.add(findSide(cell.p3, cell.p4, sideIndex));
        cell.sides.add(findSide(cell.p4, cell.p1, sideIndex));

      }
    }


  }

  private static SideRef findSide(int p1, int p2, Map<IntPair, Side> sideIndex) {

    {
      Side side = sideIndex.get(new IntPair(p1, p2));
      if (side != null) return new SideRef(side, false);
    }

    {
      Side side = sideIndex.get(new IntPair(p2, p1));
      if (side != null) return new SideRef(side, true);
    }

    throw new RuntimeException("Cannot find side for " + p1 + " -> " + p2);
  }
}
